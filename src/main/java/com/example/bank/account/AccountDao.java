package com.example.bank.account;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class AccountDao {

  private static AccountDao accountDao = null;

  private List<Account> accounts =
      new CopyOnWriteArrayList<Account>(
          Arrays.asList(
              new Account(0, "user_x", 100),
              new Account(0, "user_y", 200),
              new Account(0, "user_z", 0)));

  private AccountDao() {}

  public static AccountDao instance() {
    if (accountDao == null) {
      accountDao = new AccountDao();
    }
    return accountDao;
  }

  public Optional<Account> getAccountByUserName(final String userName) {
    return accounts.stream()
        .filter(account -> userName.equalsIgnoreCase(account.getUserName()))
        .findAny();
  }

  public boolean addAccount(final Account account) {
    return !getAccountByUserName(account.getUserName()).isPresent() &&  accounts.add(account);
  }

}
