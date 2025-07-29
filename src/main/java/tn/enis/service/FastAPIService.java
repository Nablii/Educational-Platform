package tn.enis.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import tn.enis.query.QueryRequest;
import tn.enis.query.QueryResponse;

@Service
public class FastAPIService {

    private final RestTemplate restTemplate;

    public FastAPIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String queryFastApi(String userInput, String boilerplate) {
        String url = "http://localhost:9999/query";

        QueryRequest request = new QueryRequest();
        request.setInput_string(boilerplate + userInput);

        try {
            ResponseEntity<QueryResponse> response = restTemplate.postForEntity(
                    url,
                    request,
                    QueryResponse.class
            );
            return response.getBody().getResult();
        } catch (HttpClientErrorException e) {
            return "Error from FastAPI: " + e.getResponseBodyAsString();
        }
    }
}
