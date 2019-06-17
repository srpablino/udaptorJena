package io.udaptor.core.mappers;

import io.udaptor.core.models.Job;
import io.udaptor.core.util.Dictionary;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.riot.Lang;
import org.apache.jena.update.UpdateAction;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Mapper {

    private Job myJob = null;

    public Mapper(Job job){
        this.myJob = job;
    }

    private final String PATH_ONTOLOGIES = "src/main/resources/sparqlLoader/";
    private final String PATH_QUERY = "src/main/resources/queries/mappingQuery.txt";

    public List<HashMap<String,String>> mapNow() throws Exception {

        return this.performQuery(this.buildModel(myJob.getInputFormat(),myJob.getOutputFormat()),PATH_QUERY);
    }

    private OntModel buildModel(String inputServiceName, String outputServiceName){

        OntModel model = ModelFactory.createOntologyModel();

        //reads and integrate general model
        UpdateAction.readExecute(PATH_ONTOLOGIES+"UDAPTOR.txt",model);

        //reads and integrate input service model and links
        UpdateAction.readExecute(PATH_ONTOLOGIES+inputServiceName+".txt",model);
        UpdateAction.readExecute(PATH_ONTOLOGIES+inputServiceName+"_To_UDAPTOR.txt",model);

        //reads and integrate input service metadata model and links
        UpdateAction.readExecute(PATH_ONTOLOGIES+inputServiceName+"_metadata.txt",model);
        UpdateAction.readExecute(PATH_ONTOLOGIES+inputServiceName+"_metadata_To_"+inputServiceName+".txt",model);

        //reads and integrate output service model and links
        UpdateAction.readExecute(PATH_ONTOLOGIES+outputServiceName+".txt",model);
        UpdateAction.readExecute(PATH_ONTOLOGIES+outputServiceName+"_To_UDAPTOR.txt",model);

        //reads and integrate output service metadata and links
        UpdateAction.readExecute(PATH_ONTOLOGIES+outputServiceName+"_metadata.txt",model);
        UpdateAction.readExecute(PATH_ONTOLOGIES+outputServiceName+"_metadata_To_"+outputServiceName+".txt",model);

        //writes to file the resulting integrated schema (not necessary, just to see the resulting ontology)
        this.writeFinalOntologyToFile(model);

        return model;
    }

    private List<HashMap<String,String>> performQuery(Model model, String queryName) throws Exception {
        //Get the skeleton of the sparql query
        String sparqlQueryString = this.getQueryFromFile(queryName);

        //Parameterized Queries with Jena
        //see: https://jena.apache.org/documentation/query/parameterized-sparql-strings.html
        ParameterizedSparqlString pss = new ParameterizedSparqlString();
        pss.setCommandText(sparqlQueryString);

        //build the prefix for source and target before run the query

        String inputURIMetadata = Dictionary.AVAILABLE_ONTOLOGIES_HASH.get(myJob.getInputFormat());
        String outputURIMetadata = Dictionary.AVAILABLE_ONTOLOGIES_HASH.get(myJob.getOutputFormat());

        //Node file = NodeFactory.createURI(inputURIMetadata+ myJob.getSourceFile());
        //Node object = NodeFactory.createURI(inputURIMetadata+ myJob.getSourceOjbect());

        //set parameters for the query
        //pss.setParam("fileName",file);
        //pss.setParam("object",object);
        pss.setNsPrefix("metadataSource", inputURIMetadata);
        pss.setNsPrefix("metadataTarget", outputURIMetadata);

        //Prints query after parameter set up
        System.out.println("\n"+pss.toString());

        Query query = pss.asQuery();

        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        ResultSetRewindable results = ResultSetFactory.makeRewindable(qexec.execSelect());

        if(results.hasNext()){
            List<HashMap<String,String>> resultsObject = new ArrayList<>();
            while (results.hasNext()){
                QuerySolution q = results.next();
                HashMap<String,String> rowMap = new HashMap<>();
                for(String attribute : results.getResultVars()){
                    RDFNode obj = q.get(attribute);
                    String value = obj.toString();
                    rowMap.put(attribute,value);
                }
                System.out.println(rowMap);
                resultsObject.add(rowMap);
            }
            qexec.close() ;
            return resultsObject;
        }else{
            throw new Exception("No results");
        }
    }

    private void writeFinalOntologyToFile(Model model){
        try {
            OutputStream outputStream = null;
            outputStream = new FileOutputStream("integratedSchema.owl");
            model.write(outputStream, "N-TRIPLE");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String getQueryFromFile(String path){
        String sparqlQueryString = "";
        try
        {
            sparqlQueryString = new String ( Files.readAllBytes( Paths.get(path) ) );
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return sparqlQueryString;
    }
}
