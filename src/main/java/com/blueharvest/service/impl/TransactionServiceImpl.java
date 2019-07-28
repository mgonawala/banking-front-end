package com.blueharvest.service.impl;

import com.blueharvest.model.ErrorModle;
import com.blueharvest.model.Transaction;
import com.blueharvest.service.TransactionsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class TransactionServiceImpl implements TransactionsService{

  @Value("${system.api.baserUrl}")
  private String apiBaseUrl;

  @Value(("${transactions.contextPath}"))
  private String apiPath ;

  @Value("${get.customers.byId.uri}")
  private String getCustomers;

  private RestTemplate restTemplate = new RestTemplate();

  @Override
  public List<Transaction> getAllTransactionByAccount(Long id) {
    ResponseEntity<List<Transaction>> rateResponse =
        restTemplate.exchange(apiBaseUrl + "/accounts/"+id+"/transactions",
            HttpMethod.GET, null, new ParameterizedTypeReference<List<Transaction>>() {
            }
        );
    List<Transaction> transactions = rateResponse.getBody();
    return transactions;
  }

  @Override
  public Transaction newTransaction(Transaction transaction, Long accountId) {
    transaction.setId(null);
    try {
      ResponseEntity<Transaction> savedCustomer = restTemplate
          .postForEntity(apiBaseUrl + apiPath,
              transaction, Transaction.class, accountId);
      return savedCustomer.getBody();
    } catch (HttpClientErrorException ex) {
      Transaction transaction1 = new Transaction();
      ObjectMapper objectMapper = new ObjectMapper();
      try {
        ErrorModle errorModle = objectMapper
            .readValue(ex.getResponseBodyAsString(), ErrorModle.class);
        transaction1.setErrors(errorModle.getErrors().get(0));
      } catch (IOException e) {
        e.printStackTrace();
      }
      return transaction1;
    }

  }

  @Override
  public Transaction deleteTransaction(Long accountId, Long transactionId) {
    try {

      ResponseEntity<Transaction> result = restTemplate
          .exchange(apiBaseUrl + "/accounts/" + accountId + "/transactions/" + transactionId,
              HttpMethod.PUT, null, new ParameterizedTypeReference<Transaction>() {
              }
          );

      return result.getBody();
    } catch (HttpClientErrorException ex) {
      Transaction transaction1 = new Transaction();
      ObjectMapper objectMapper = new ObjectMapper();
      try {
        ErrorModle errorModle = objectMapper
            .readValue(ex.getResponseBodyAsString(), ErrorModle.class);
        transaction1.setErrors(errorModle.getErrors().get(0));
      } catch (IOException e) {
        e.printStackTrace();
      }
      return transaction1;
    }

  }

}
