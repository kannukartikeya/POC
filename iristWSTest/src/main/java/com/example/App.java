package com.example;
    import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
     
    public class App {
        public static void main(String[] args) throws UnirestException {
/*            HttpResponse<JsonNode> response = Unirest.post("https://app.dominodatalab.com/v1/kannu/iristest/endpoint")
                .header("X-Domino-Api-Key", "dnfc1TeQnffWAZHajlJNvmmJr8DMIXZVxIhWlUEMm9ZnTCe6d3qbhyhkBmMxRNET")
                .header("Content-Type", "application/json")
                .body(new JsonNode("{\"parameters\": [ 5.6, 3.2, 1.7,0.8]}"))
                .asJson();*/
        	
            HttpResponse<JsonNode> response = Unirest.post("https://app.dominodatalab.com/v1/kannu/defaulter/endpoint")
                    .header("X-Domino-Api-Key", "dnfc1TeQnffWAZHajlJNvmmJr8DMIXZVxIhWlUEMm9ZnTCe6d3qbhyhkBmMxRNET")
                    .header("Content-Type", "application/json")
                    .body(new JsonNode("{\"parameters\": []}"))
                    .asJson();
            System.out.println(response.getStatus());
            System.out.println(response.getHeaders());
            System.out.println(response.getBody().getObject().toString(2));

            
        }
    }

