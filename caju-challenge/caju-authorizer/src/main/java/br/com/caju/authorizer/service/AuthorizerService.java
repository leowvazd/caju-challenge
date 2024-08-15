package br.com.caju.authorizer.service;

import static br.com.caju.authorizer.domain.ApplicationConstants.*;
import static java.util.Objects.isNull;
import static net.logstash.logback.marker.Markers.append;

import br.com.caju.authorizer.domain.enums.AuthorizerStatusCodeEnum;
import br.com.caju.authorizer.domain.enums.MccEnum;
import br.com.caju.authorizer.entity.Account;
import br.com.caju.authorizer.entity.Transaction;
import br.com.caju.authorizer.exception.TransactionException;
import br.com.caju.authorizer.repository.AccountRepository;
import br.com.caju.authorizer.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
@Slf4j
public record AuthorizerService(AccountRepository accountRepository, TransactionRepository transactionRepository,
                                AccountService accountService) {

    public String approveSimpleTransaction(Transaction transaction) {
        try {
            log.info(append(TRANSACTION, transaction), "Starting to process approve simple transaction");
            var account = getAccount(transaction);

            var mcc = checkMccByMerchant(transaction);
            log.info(append(MCC_CODE, mcc), "Merchant name preceding Mcc successfully checked");

            var newBalance = getCorrectBalanceValue(account, mcc) - transaction.getTotalAmount();
            log.info(append(BALANCE_VALUE, newBalance), "Matching balance value found successfully");

            if (newBalance < 0) {
                transaction.setTransactionCode(AuthorizerStatusCodeEnum.INSUFFICIENT_FUNDS);
                log.info(append(TRANSACTION_CODE, transaction.getTransactionCode()), "Simple transaction was processed unsuccessfully: insufficient funds");
                return AuthorizerStatusCodeEnum.toResponseJson(transaction.getTransactionCode().toString());
            }

            updateCorrectBalance(account, mcc, newBalance);
            transaction.setTransactionCode(AuthorizerStatusCodeEnum.APPROVED);
            transactionRepository.save(transaction);
            log.info(append(TRANSACTION_CODE, transaction.getTransactionCode()), "Simple transaction was processed successfully");
            return AuthorizerStatusCodeEnum.toResponseApproveJson(transaction.getTransactionCode().toString(), transaction.getId());

        } catch (Exception e) {

            transaction.setTransactionCode(AuthorizerStatusCodeEnum.PROCESSING_TRANSACTION_ERROR);
            log.info(append(TRANSACTION_CODE, transaction.getTransactionCode()), "Simple transaction was processed unsuccessfully: processing transaction error");
            return AuthorizerStatusCodeEnum.toResponseJson(transaction.getTransactionCode().toString());

        }
    }

    public Transaction findTransactionById(Long id) {
        log.info(append(TRANSACTION_ID, id), "Starting to find transaction by id");
        var response = transactionRepository.findById(id).orElse(null);

        if (isNull(response)) {
            throw new TransactionException("The transaction with the given id does not exist");
        }

        log.info(append(TRANSACTION_ID, id), "Transaction was found successfully");
        return response;
    }

    private Account getAccount(Transaction transaction) {
        return accountService.findAccountById(transaction.getAccount());
    }

    private MccEnum checkMccByMerchant(Transaction transaction) {

        final Map<Pattern, MccEnum> merchantNameHashMap = new HashMap<>();

        // Armazena os comerciantes conhecidos em uma espécie "biblioteca" HashMap para consulta
        merchantNameHashMap.put(Pattern.compile("UBER TRIP", Pattern.CASE_INSENSITIVE), MccEnum.CASH);
        merchantNameHashMap.put(Pattern.compile("UBER EATS", Pattern.CASE_INSENSITIVE), MccEnum.MEAL);
        merchantNameHashMap.put(Pattern.compile("PAG\\*JoseDaSilva", Pattern.CASE_INSENSITIVE), MccEnum.FOOD);
        merchantNameHashMap.put(Pattern.compile("PICPAY\\*BILHETEUNICO", Pattern.CASE_INSENSITIVE), MccEnum.CASH);

        String merchantName = transaction.getMerchant();

        // Verifica se o nome do comerciante corresponde a algum padrão conhecido
        for (Map.Entry<Pattern, MccEnum> entry : merchantNameHashMap.entrySet()) {
            if (entry.getKey().matcher(merchantName).find()) {
                log.info(append(MERCHANT_NAME, entry.getValue()), "Known merchant name successfully found");
                return entry.getValue();
            }
        }

        // Se não houver correspondência, use o Mcc proveniente da transação
        log.info(append(MCC_CODE, transaction.getMcc()), "Known merchant name successfully found");
        return transaction.getMcc();
    }

    private Double getCorrectBalanceValue(Account account, MccEnum mcc) {
        return switch (mcc) {
            case FOOD -> account.getFood();
            case MEAL -> account.getMeal();
            default -> account.getCash();
        };
    }

    private void updateCorrectBalance(Account account, MccEnum mcc, Double newBalance) {
        switch (mcc) {
            case FOOD -> accountRepository.updateFoodBalanceById(account.getId(), newBalance);
            case MEAL -> accountRepository.updateMealBalanceById(account.getId(), newBalance);
            default -> accountRepository.updateCashBalanceById(account.getId(), newBalance);
        }
    }
}
