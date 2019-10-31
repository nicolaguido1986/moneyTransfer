package com.example.bank.transfer


import com.example.bank.exception.TransferException
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class TransferServiceSpec extends Specification {

    com.example.bank.account.AccountService accountService = Mock()

    @Subject
    TransferService transferService

    def setup() {
        transferService = new TransferService(accountService)
    }

    @Unroll
    def "createTransfer should throw an exception"() {
        when:
        transferService.createTransfer(transfer)

        then:
        TransferException transferException = thrown()
        transferException.message == "Transfer details are not valid"

        where:
        transfer << [
                null,
                new Transfer(null, null, -1, ""),
                new Transfer(null, null, 0, ""),
                new Transfer(null, "", -1, ""),
                new Transfer(null, "", 0, ""),
                new Transfer(null, "user_x", -1, ""),
                new Transfer(null, "user_x", 0, ""),
                new Transfer("", null, -1, ""),
                new Transfer("", null, 0, ""),
                new Transfer("", "", -1, ""),
                new Transfer("", "", 0, ""),
                new Transfer("", "user_x", -1, ""),
                new Transfer("", "user_x", 0, ""),
                new Transfer("user_y", null, -1, ""),
                new Transfer("user_y", null, 0, ""),
                new Transfer("user_y", "", -1, ""),
                new Transfer("user_y", "", 0, ""),
                new Transfer("user_y", "user_x", -1, "")
        ]
    }

    def "createTransfer should be executed correctly"(){
        given:
        Transfer transfer = new Transfer("user_y", "user_x", 10,"")
        com.example.bank.account.Account sender = com.example.bank.account.Account.builder().amount(10).build()
        com.example.bank.account.Account receiver = com.example.bank.account.Account.builder().amount(0).build()
        when:
        transferService.createTransfer(transfer)

        then:
        1 * accountService.getAccountByUserName("user_y") >> sender
        1 * accountService.getAccountByUserName("user_x") >> receiver
        old(sender.amount) == 10
        old(receiver.amount) == 0
        sender.amount == 0
        receiver.amount == 10
    }

}
