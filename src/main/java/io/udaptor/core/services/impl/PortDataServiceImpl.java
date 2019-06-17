package io.udaptor.core.services.impl;

import io.DataFile;
import io.udaptor.core.mappers.Mapper;
import io.udaptor.core.models.Job;
import io.udaptor.core.services.FileService;
import io.udaptor.core.services.PortDataService;
import io.udaptor.core.util.Dictionary;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PortDataServiceImpl implements PortDataService {

    final String FILE_INTEGRATION_SCHEMA = "src/main/resources/integrationSchemas.owl";
    final String CLASS_PATH = "io.udaptor.core.mappers.musicMappers.";

    private final FileService fileService;
    private File file;

    public PortDataServiceImpl(final FileService fileService) {
        this.fileService = fileService;
    }



    @Override
    public String performConversionAndGetOutputFileUrl(Job job) throws Exception {
        //loads Integration Schema and checks if input  to output is possible
        List<HashMap<String,String>> outputMapList = null;

        if (!Dictionary.AVAILABLE_ONTOLOGIES_HASH.containsKey(job.getInputFormat()) ||
            !Dictionary.AVAILABLE_ONTOLOGIES_HASH.containsKey(job.getOutputFormat())){
            throw new Exception("Mapping from "+job.getInputFormat() + " to "+job.getOutputFormat()+" not possible.");
        }
        String outputFileUrl = "";

        try{
            outputMapList = new Mapper(job).mapNow();
            //Pass json as String
            String s3FileUrl = job.getUrlS3();
            File file = fileService.download(s3FileUrl);
            String json = String.join("", Files.readAllLines(file.toPath()));
            System.out.println(json);


            SparkSession sparkSession = SparkSession.builder().appName("mapper").master("local[1]").getOrCreate();
            DataFile dataFile = new DataFile(json, sparkSession);
//            outputMapList.forEach(map -> {
//                buildSourceAndTarget(map, dataFile);
//            });

            dataFile.registerMap("file_jsonöø_playlistπøartistName", "another_file_jsonöø_playlistπønameOfTheArtist");
            dataFile.registerMap("file_jsonöø_playlistπømsPlayed", "another_file_jsonöø_playlistπøtimePlayed");

            File file1 = dataFile.performMapsAndReturnFile();
             outputFileUrl = fileService.upload(file1);
        }
        catch (final Exception e){
            e.printStackTrace();
            throw new Exception("Job mapping failed");
        }

        System.out.println(outputFileUrl);
        return outputFileUrl;
    }

    @Override
    public List<String> getAllAvailableServicesForMapping() {
        return Dictionary.getAllAvailableServicesForMapping();
    }



    public Map<String, String> buildSourceAndTarget(HashMap<String, String> map, DataFile dataFile) {
        Map<String, String> result = new HashMap<>();
        String[] targetArray = map.get("targetObject").split("/");
        String[] sourceArray = map.get("sourceObject").split("/");

        String target = targetArray[targetArray.length - 1];
        String source = sourceArray[targetArray.length - 1];

        dataFile.registerMap(source, target);
        return result;
    }
}
