package com.revolut.bank.account

import com.revolut.bank.exception.ResourceNotFoundException
import com.revolut.bank.exception.TransferException
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class AccountServiceSpec extends Specification {

    AccountDao accountDao = AccountDao.instance()

    @Subject
    AccountService accountService

    def setup(){
        accountService = new AccountService(accountDao)
    }

    def "getAccountByName should return an account"(){
        when:
        accountService.getAccountByUserName("user_x")

        then:
        noExceptionThrown()
    }

    def "getAccountByName should thrown an acception"(){
        when:
        accountService.getAccountByUserName("name")

        then:
        ResourceNotFoundException resourceNotFoundException = thrown()
        resourceNotFoundException.message == "Account not found"
    }

    def "addAccount should thrown an exception"() {
        when:
        accountService.addAccount(account)

        then:
        TransferException transferException = thrown()
        transferException.message == "Account details not valid"

        where:
        account<< [
                Account.builder().build(),
                Account.builder().userName(null).build(),
                Account.builder().userName("").build()
        ]
    }

    @Unroll
    def "addAccount should return #isPresent when #username"() {
        def isPresent = accountDao.getAccountByUserName(username).isPresent()

        expect:
        accountService.addAccount(new Account(0, username, 100)) == !isPresent

        where:
        username << ["user_x", "user_xy"]
    }


}
