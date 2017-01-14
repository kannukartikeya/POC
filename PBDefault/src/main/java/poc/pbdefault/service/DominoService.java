package poc.pbdefault.service;
    import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import poc.pbdefault.domain.PDModel;
     
    public class DominoService {
    	public static void main(String[] args) throws UnirestException {
    	PDModel pdmodel = new PDModel(10,10,1.2,2,new Boolean(true));
    	getPBDetails(pdmodel);
    	}
        public static void getPBDetails(PDModel pdModel) throws UnirestException {
/*            HttpResponse<JsonNode> response = Unirest.post("https://app.dominodatalab.com/v1/kannu/iristest/endpoint")
                .header("X-Domino-Api-Key", "dnfc1TeQnffWAZHajlJNvmmJr8DMIXZVxIhWlUEMm9ZnTCe6d3qbhyhkBmMxRNET")
                .header("Content-Type", "application/json")
                .body(new JsonNode("{\"parameters\": [ 5.6, 3.2, 1.7,0.8]}"))
                .asJson();*/
        	String requestbody = "{\"parameters\": [" + pdModel.getLast_fico_range_low() +","+
					  pdModel.getLast_fico_range_high() +","+
					  pdModel.getRevol_util()+","+
					  pdModel.getInq_last_6mths() +","+
					  pdModel.isIs_rent()+"]}";
        	
        	System.out.println("Request body is "  + requestbody);
/*            HttpResponse<JsonNode> response = Unirest.post("https://app.dominodatalab.com/v1/kannu/defaulter/endpoint")
                    .header("X-Domino-Api-Key", "dnfc1TeQnffWAZHajlJNvmmJr8DMIXZVxIhWlUEMm9ZnTCe6d3qbhyhkBmMxRNET")
                    .header("Content-Type", "application/json")
                    .body(new JsonNode(requestbody))
                    .asJson();*/
            HttpResponse<JsonNode> response = Unirest.post("https://trial.dominodatalab.com/v1/kartik/defaulter/endpoint")
                    .header("X-Domino-Api-Key", "iiaPA42kplRz0lG8TRDOTmgHLDThnU3gJsOAK6SLB8U68VWDeCg5U5PcoF7zz9R9")
                    .header("Content-Type", "application/json")
                    .body(new JsonNode(requestbody))
                    .asJson();
            //.body(new JsonNode("{\"parameters\": [0,759,755,83.7,1,TRUE]}"))
            System.out.println(response.getStatus());
            System.out.println(response.getHeaders());
            System.out.println(response.getBody().getObject().toString(2));

            
        }
        
        
    }

