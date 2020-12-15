package com.h2app.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class AmazonS3Factory {

    AmazonS3 s3Client;

    public  void setAmazonS3Client(String accessKey, String secretAccessKey){
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretAccessKey);
        AmazonS3 client = AmazonS3ClientBuilder
        .standard()
        .withCredentials(new AWSStaticCredentialsProvider(credentials))
        .withRegion(Regions.US_EAST_2)
        .build();
       this.s3Client = client;
    }

    public AmazonS3 getClient(){
        return s3Client;
    }
    
}
