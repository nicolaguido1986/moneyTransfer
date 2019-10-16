package com.revolut.bank.transfer;

import com.revolut.bank.account.Account;
import com.revolut.bank.account.AccountService;
import com.revolut.bank.exception.TransferException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransferService {

  private final AccountService accountService;

  public TransferService(final AccountService accountService) {
    this.accountService = accountService;
  }

  public Transfer createTransfer(Transfer transfer) {
    if (transfer == null || !transfer.isValid()) {
      log.error("Transfer is not valid {}", transfer);
      throw new TransferException("Transfer details are not valid");
    }
    Account sender = accountService.getAccountByUserName(transfer.getUserFrom());

    log.info("Found sender {}", sender);
    Account receiver = accountService.getAccountByUserName(transfer.getUserTo());
    log.info("Found receiver {}", receiver);
    sender.unload(transfer.getAmount());
    receiver.load(transfer.getAmount());
    return transfer;
  }
}
