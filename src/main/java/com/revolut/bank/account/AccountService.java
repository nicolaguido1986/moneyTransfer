package com.revolut.bank.account;

import com.revolut.bank.exception.ResourceNotFoundException;
import com.revolut.bank.exception.TransferException;
import lombok.extern.slf4j.Slf4j;
import spark.utils.StringUtils;

@Slf4j
public class AccountService {

  private final AccountDao accountDao;

  public AccountService(final AccountDao accountDao) {
    this.accountDao = accountDao;
  }

  public Account getAccountByUserName(final String userName) {
    return accountDao
        .getAccountByUserName(userName)
        .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
  }

  public boolean addAccount(final Account account) {
    if (account == null || StringUtils.isBlank(account.getUserName())) {
      log.error("Account details not valid {}", account);
      throw new TransferException("Account details not valid");
    }
    return accountDao.addAccount(account);
  }

  //todo: not tested
  public Account updateAccount(final String id, final Account account) {
    if (StringUtils.isBlank(id) || account == null || id.equalsIgnoreCase(account.getUserName())) {
      throw new TransferException("Account id is not matching the account details");
    }
    Account persistedAccount = getAccountByUserName(id);
    persistedAccount.setAmount(account.getAmount());
    return persistedAccount;
  }
}
