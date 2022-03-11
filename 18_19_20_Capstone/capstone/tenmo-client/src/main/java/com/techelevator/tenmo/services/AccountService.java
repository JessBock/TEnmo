package com.techelevator.tenmo.services;


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

public class AccountService {
    private final String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();
    private UserService userService;
    private String authToken = null;

    public AccountService(String baseUrl){
        this.baseUrl = baseUrl;
        this.userService = new UserService(baseUrl);
    }

    public List<BigDecimal> getBalance(){
        List<BigDecimal> balances =new ArrayList();
        try {
            userService.setAuthToken(authToken);
            List<Long> accounts = userService.getAccounts();
            for (Long account : accounts) {
                String url = baseUrl + "account/" + account + "/balance";
                ResponseEntity <BigDecimal> response = restTemplate.exchange(url, HttpMethod.GET,makeAuthEntity(),BigDecimal.class);
                        balances.add(response.getBody());
            }
        } catch (RestClientException e) {
            System.out.println("Unable to Print Balance");
        }
        return balances;
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
