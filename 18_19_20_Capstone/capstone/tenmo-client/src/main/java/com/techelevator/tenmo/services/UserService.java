package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private final String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();
    private String authToken = null;


    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }


    public UserService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public List<Long> getAccounts() {
        String url = baseUrl + "account/";
        List<Long> accounts = new ArrayList<>();
        try {
            ResponseEntity <List<Long>> response = restTemplate.exchange(url, HttpMethod.GET, makeAuthEntity(),new ParameterizedTypeReference<List<Long>>() {});
            accounts = response.getBody();
        } catch (RestClientException e) {
            System.out.println("Account Not Found!");
        }
        return accounts;
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

    public List<Transfer> getTransferHistory(){
    String url = baseUrl + "account/transfers";
    List<Transfer> transfers = new ArrayList<>();
    try{
        ResponseEntity <List<Transfer>> response = restTemplate.exchange(url, HttpMethod.GET, makeAuthEntity(), new ParameterizedTypeReference<List<Transfer>>() {
        });
        transfers = response.getBody();
    } catch (RestClientException e) {
        System.out.println("Transfers History Not Found!");
    }
    return transfers;
    }


}
