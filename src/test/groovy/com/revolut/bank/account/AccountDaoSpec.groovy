package com.revolut.bank.account

import spock.lang.Specification

class AccountDaoSpec extends Specification {

    def "instance should create a new instance"(){
        when:
        AccountDao accountDao = AccountDao.instance()

        then:
        accountDao != null
    }

    def "instance should create only one instance"(){
        when:
        AccountDao accountDao1 = AccountDao.instance()
        accountDao1.addAccount(new Account(4, "user_xy", 100))
        AccountDao accountDao2 = AccountDao.instance()

        then:
        accountDao1 == accountDao2
    }

    def "getAccountByUsername should return an account"(){
        given:
        AccountDao accountDao = AccountDao.instance()

        expect:
        accountDao.getAccountByUserName("user_x").isPresent()
    }

    def "getAccountByUsername should return an ampty optional"(){
        given:
        AccountDao accountDao = AccountDao.instance()

        expect:
        accountDao.getAccountByUserName("user_yy").isEmpty()
    }

    def "addAccount should insert a new account"(){
        given:
        AccountDao accountDao = AccountDao.instance()

        expect:
        accountDao.addAccount(new Account(0, "user_y y", 100))
    }

    def "addAccount should not insert an existing account"(){
        given:
        AccountDao accountDao = AccountDao.instance()

        expect:
        !accountDao.addAccount(new Account(0, "user_x", 100))
    }
}
