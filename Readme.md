# Jena mapping service
## Performs the next processes chain:   
* joinOntologies(inputMetadata,inputOntology,standardOntology,outputOntology, outputMetadata)  
* query data from a standard sparql query to retrieve correspondences InputMetadata <--> OutputMetadata  
* Return JSON file with the mapped concept from inputMetadata to outputMetadata.
* *OBS: If it is not perform the mapping due to the lack or bad parameters, and error with status 500 is thrown (could be improved)

### Petition parameters and outputExample
#### Succesful request
![](./readmeFiles/SuccesfulRequest.jpg?raw=true)
#### Failded request
![](./readmeFiles/FailedRequest.jpg?raw=true)

## Code structure
Spring Boot project structure.

## Resources description

![](./readmeFiles/resourcesStructure.png?raw=true)

* ./queries contain the queries to perform the mappings (one generic query with parametrizable variables)
* ./sparqlLoader/ folder contains the queries to generate the ontologies tboxes
    * *OBS: When adding new services, you need to specify 4 files:
        * The ontology of the new service
        * The ontology of the metadata of the new service
        * The link between the metadata and the ontology of the service
        * The link between the ontology of the service and the UDAPTOR ontology
    * **READ: Need to follow the name convention, see examples of current services available 

## Add new ontologies
To support new services you will need to add the corresponding ontologies files following the name convention and also add the corresponding entries into the Dictionary class, inside the util package.
