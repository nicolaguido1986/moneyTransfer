package com.example.bank.account

import com.example.bank.exception.TransferException
import spock.lang.Specification
import spock.lang.Unroll

class AccountSpec extends Specification {

    @Unroll
    def "unload should throw an exception"() {
        given:
        Account account = new Account(0, "username", amount)

        when:
        account.unload(transferAmount)

        then:
        TransferException ex = thrown()
        ex.getMessage() == "Invalid unload operation in account " + account.userName

        where:
        amount << [-1, -1, 0, 0, 1, 1]
        transferAmount << [-1, 2, -1, 2, -1, 2]
    }

    def "unload should be performed successfully"() {
        given:
        Account account = new Account(0, "username", 100)

        when:
        account.unload(10)

        then:
        account.getAmount() == 90
    }

    def "load should thrown an exception"(){
        given:
        Account account = new Account(0, "username", 100)

        when:
        account.load(-1 )

        then:
        TransferException ex = thrown()
        ex.getMessage() == "Invalid load operation in account " + account.userName
    }

    def "load should be performed successfully"() {
        given:
        Account account = new Account(0, "username", 100)

        when:
        account.load(10)

        then:
        account.getAmount() == 110
    }
}
