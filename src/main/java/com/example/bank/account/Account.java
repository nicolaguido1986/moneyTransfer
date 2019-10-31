package com.example.bank.account;

import com.example.bank.exception.TransferException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@Builder
@Slf4j
@Accessors(chain = true)
public class Account {

  private int id;
  private String userName;
  private int amount;

  public synchronized void unload(int amountTransfer) {
    if (this.amount < 0  || amountTransfer < 0 || this.amount < amountTransfer) {
      throw new TransferException("Invalid unload operation in account " +  this.userName);
    }
    this.amount -= amountTransfer;
    log.info("Money unload successfully from account {}", this);
  }

  public synchronized void load(int amountTransfer) {
    if (amountTransfer < 0) {
      throw new TransferException("Invalid load operation in account " +  this.userName);
    }
    this.amount += amountTransfer;
    log.info("Money load successfully to account {}", this);
  }
}
