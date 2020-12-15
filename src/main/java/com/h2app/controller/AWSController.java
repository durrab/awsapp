package com.h2app.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ListBucketsRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.h2app.dto.Key;
import com.h2app.dto.Payload;
import com.h2app.dto.RequestDTO;
import com.h2app.dto.ResponseDTO;
import com.h2app.model.Book;
import com.h2app.aws.AmazonS3Factory;
import com.h2app.aws.ApiGatewayResponse;
import com.h2app.aws.JsonApiGatewayCaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = "/aws")
public class AWSController {

    @Value("${aws.gatway.url}")
    private String apiUrl;

    @Autowired
    private JsonApiGatewayCaller caller;

    @Autowired
    private AmazonS3Factory s3Factory;

    Logger logger = LoggerFactory.getLogger(AWSController.class);

    @PostMapping(value = "/callApiGateway", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> callApiGateway(@RequestBody com.h2app.dto.RequestDTO req) {
        HttpHeaders headers = new HttpHeaders();
        ApiGatewayResponse response = null;
        String requestStr = "";

        try {
            requestStr = new ObjectMapper().writeValueAsString(req);
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        response = caller.execute(HttpMethodName.POST, "/number", new ByteArrayInputStream(requestStr.getBytes()));
        
        return new ResponseEntity<>(response.getBody(), headers, HttpStatus.OK);
    }

    @PostMapping(value = "/callTemplate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> callWithRestTemplate(@RequestBody RequestDTO req)
            throws JsonMappingException, JsonProcessingException {
        String request = "";
        try {
            request = new ObjectMapper().writeValueAsString(req);
        }

        catch (IOException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestEntity = new HttpEntity<>(request, headers);
        HttpEntity<String> responseEntity = restTemplate.exchange(apiUrl+"/number", HttpMethod.POST, requestEntity,
                String.class);
        String response = responseEntity.getBody();
        ResponseDTO responseObj = new ObjectMapper().readValue(response, ResponseDTO.class);
        return new ResponseEntity<>(responseObj, headers, HttpStatus.OK);
    }

    @GetMapping(value = "/s3")
    public ResponseEntity<List<Bucket>> getS3() {
        HttpHeaders headers = new HttpHeaders();
        AmazonS3 client = s3Factory.getClient();
        List<Bucket> buckets = client.listBuckets();
        headers.add("buckets-length", String.valueOf(buckets.size()));
        return new ResponseEntity<>(buckets, headers, HttpStatus.OK);
    }

    @GetMapping(value = "/s3/{bucketName}")
    public ResponseEntity<Bucket> getS3ByBucketName(@PathVariable String bucketName) {
        HttpHeaders headers = new HttpHeaders();
        AmazonS3 client = s3Factory.getClient();
        List<Bucket> buckets = client.listBuckets();
        Bucket existingBucket = null;
        for(Bucket bucket : buckets){
            if(bucket.getName().equalsIgnoreCase(bucketName)){
                existingBucket = bucket;
                break;
            }
        }
        return new ResponseEntity<>(existingBucket, headers, HttpStatus.OK);
    }


    @PutMapping(value = "/s3/{bucketName}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createS3(@PathVariable String bucketName){
       AmazonS3 client = s3Factory.getClient();
       if(client.doesBucketExistV2(bucketName)){
           return new ResponseEntity<>("bucket name already exists",HttpStatus.BAD_REQUEST);
       }
       client.createBucket(bucketName);
       return new ResponseEntity<>(String.format("bucket with name [%s] created successfully!",bucketName),HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/s3/{bucketName}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteS3(@PathVariable String bucketName){
        AmazonS3 client = s3Factory.getClient();
        if(client.doesBucketExistV2(bucketName)){
            client.deleteBucket(bucketName);
        }
        else {
            return new ResponseEntity<>(String.format("bucket name [%s] doesn't exists", bucketName),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(String.format("bucket with name [%s] deleted successfully!",bucketName),HttpStatus.ACCEPTED);
    }


}
