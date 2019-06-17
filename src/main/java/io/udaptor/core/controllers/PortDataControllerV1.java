package io.udaptor.core.controllers;

import io.udaptor.core.DataPortabilityApplication;
import io.udaptor.core.models.Job;
import io.udaptor.core.response.JobResponse;
import io.udaptor.core.services.PortDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/job")
public class PortDataControllerV1 {

    private final PortDataService portDataService;

    public PortDataControllerV1(final PortDataService portDataService) {
        this.portDataService = portDataService;
    }

    @PostMapping
    public ResponseEntity<?> newJob(@RequestBody final Job job) throws Exception {
        DataPortabilityApplication.logger.info("New job with the request: \n {}", job);
        final JobResponse jobResponse = new JobResponse(portDataService.performConversionAndGetOutputFileUrl(job));
        return new ResponseEntity<>(jobResponse, HttpStatus.OK);
    }


}
