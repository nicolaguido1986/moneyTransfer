import static com.revolut.bank.util.JsonUtil.json;
import static com.revolut.bank.util.JsonUtil.toJson;
import static spark.Spark.after;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.revolut.bank.account.Account;
import com.revolut.bank.account.AccountDao;
import com.revolut.bank.account.AccountService;
import com.revolut.bank.exception.ResourceNotFoundException;
import com.revolut.bank.exception.ResponseError;
import com.revolut.bank.exception.TransferException;
import com.revolut.bank.transfer.Transfer;
import com.revolut.bank.transfer.TransferService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MoneyTransferApplication {

  private static AccountService accountService = new AccountService(AccountDao.instance());

  private static TransferService transferService = new TransferService(accountService);

  // It is thread-safe
  private static Gson gson = new Gson();

  public static void main(String[] args) {

    post(
        "/create_transfer",
        (request, response) -> {
          log.info("Request_arrived");
          response.type("application/json");
          Transfer transfer = gson.fromJson(request.body(), Transfer.class);
          return transferService.createTransfer(transfer);
        },
        json());

    get(
        "/account/:id",
        (request, response) -> {
          String id = request.params(":id");
          log.info("Get account by id={}", id);
          return accountService.getAccountByUserName(id);
        },
        json());

    post(
        "/account",
        (request, response) -> {
          log.info("Request_arrived");
          response.type("application/json");
          Account account = gson.fromJson(request.body(), Account.class);
          return accountService.addAccount(account);
        },
        json());

    put(
        "/account/:id",
        (request, response) -> {
          String id = request.params(":id");
          log.info("Update account by id={}", id);
          return accountService.getAccountByUserName(id);
        },
        json());

    exception(
        TransferException.class,
        (e, req, res) -> {
          res.status(500);
          res.body(toJson(new ResponseError(e)));
        });

    exception(
        JsonSyntaxException.class,
        (e, req, res) -> {
          res.status(404);
          res.body(toJson(new ResponseError(e)));
        });
    exception(
        ResourceNotFoundException.class,
        (e, req, res) -> {
          res.status(404);
          res.body(toJson(new ResponseError(e)));
        });
  }
}
