package com.h2app.config;

import java.net.URI;
import java.net.URISyntaxException;

import com.amazonaws.services.s3.AmazonS3;
import com.h2app.aws.AmazonS3Factory;
import com.h2app.aws.JsonApiGatewayCaller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

    @Value("${aws.accesskey}")
    private String accessKey; //These keys are just for example - potential PII

    @Value("${aws.secretkey}")
    private String secretKey; //These keys are just for example - potential PII

    @Value("${aws.gatway.url}")
    private String url;

    @Value("${aws.region}")
    private String region;

    @Bean
    @Scope("prototype")
    public JsonApiGatewayCaller getApiGatewayCaller() {
        try {
            System.out.println("JsonApiGatewayCaller Bean called");
            return new JsonApiGatewayCaller(accessKey, secretKey, null, region, new URI(url));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Bean
    @Scope("prototype")
    public AmazonS3Factory getAmazonS3Client(){
        AmazonS3Factory client = new AmazonS3Factory();
        client.setAmazonS3Client(accessKey, secretKey);
        return client;
    }
    
}
