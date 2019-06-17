package io.udaptor.core.response;

import io.udaptor.core.util.Assert;

public class JobResponse {

    private final String outputFileUrl;

    public JobResponse(
            final String outputFileUrl
    )
    {
        Assert.nonEmptyString(outputFileUrl, "outputFileUrl cannot be null or empty");
        this.outputFileUrl = outputFileUrl;
    }

    public String getOutputFileUrl() {
        return outputFileUrl;
    }


    @Override
    public String toString() {
        return "JobResponse{" +
                "outputFileUrl='" + outputFileUrl + '\'' +
                '}';
    }
}
