package com.h2app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payload {

    @JsonProperty("key")
    private Key key;
    @JsonProperty("operation")
    private String operation;
    @JsonProperty("tableName")
    private String tableName;
    @JsonProperty("name")
    private String name;
}
