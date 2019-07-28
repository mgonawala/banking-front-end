package com.blueharvest.model;

import java.time.Instant;

public class Transaction {

  private Long id;

  private String type;

  private Instant transactionDate;

  private double amount;

  private String status;

  private Account account;

  private String errors;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Instant getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(Instant transactionDate) {
    this.transactionDate = transactionDate;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public String getErrors() {
    return errors;
  }

  public void setErrors(String errors) {
    this.errors = errors;
  }
}
