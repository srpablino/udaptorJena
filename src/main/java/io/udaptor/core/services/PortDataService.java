package io.udaptor.core.services;

import io.udaptor.core.models.Job;

import java.util.List;

public interface PortDataService {
    //returns S3_URL where the new file with output format has been uploaded
    String performConversionAndGetOutputFileUrl(Job job) throws Exception;

    List<String> getAllAvailableServicesForMapping();


}
