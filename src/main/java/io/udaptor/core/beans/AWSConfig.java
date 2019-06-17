package io.udaptor.core.beans;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AWSConfig {

    @Value("${aws_access_key_id}")
    private String awsAccessKeyId;

    @Value("${aws_secret_access_key}")
    private String accessKey;

    @Value("${s3_bucket_url}")
    private String s3BaseUrl;

    @Bean
    public AWSCredentials aws() {
        return new BasicAWSCredentials(awsAccessKeyId, accessKey);
    }

    @Bean
    public AmazonS3 s3() {
        return AmazonS3ClientBuilder
                .standard()
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3BaseUrl, Regions.EU_WEST_1.getName()))
                    .withCredentials(new AWSStaticCredentialsProvider(aws()))
                .build();

    }

}
