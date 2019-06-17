package io.udaptor.core.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Job  {

    private final Long idJob;
    private final String email;
    private final String inputFormat;
    private final String outputFormat;
    private final String urlS3;
    private final String sourceFile;
    private final String sourceOjbect;

    @JsonCreator
    public Job(
            @JsonProperty(value = "idJob") final Long idJob,
            @JsonProperty(value = "email") final String email,
            @JsonProperty("inputFormat") final String inputFormat,
            @JsonProperty("outputFormat") final String outputFormat,
            @JsonProperty("urlS3") final String urlS3,
            @JsonProperty("sourceFile") final String sourceFile,
            @JsonProperty("sourceObject") final String sourceOjbect
    )
    {
        this.idJob = idJob;
        this.email = email;
        this.inputFormat = inputFormat;
        this.outputFormat = outputFormat;
        this.urlS3 = urlS3;
        this.sourceFile = sourceFile;
        this.sourceOjbect = sourceOjbect;
    }

    public Long getIdJob() {
        return idJob;
    }

    public String getEmail() {
        return email;
    }

    public String getInputFormat() {
        return inputFormat;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public String getUrlS3() {
        return urlS3;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public String getSourceOjbect() {
        return sourceOjbect;
    }

    @Override
    public String toString() {
        return "Job{" +
                "idJob=" + idJob +
                ", email='" + email + '\'' +
                ", inputFormat='" + inputFormat + '\'' +
                ", outputFormat='" + outputFormat + '\'' +
                ", urlS3='" + urlS3 + '\'' +
                ", sourceFile='" + sourceFile + '\'' +
                ", sourceObject='" + sourceOjbect + '\'' +
                '}';
    }
}
