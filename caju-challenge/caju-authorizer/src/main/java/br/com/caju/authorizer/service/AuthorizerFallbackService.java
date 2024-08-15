package br.com.caju.authorizer.service;

import br.com.caju.authorizer.domain.enums.AuthorizerStatusCodeEnum;
import br.com.caju.authorizer.domain.enums.MccEnum;
import br.com.caju.authorizer.entity.Account;
import br.com.caju.authorizer.entity.Transaction;
import br.com.caju.authorizer.exception.TransactionException;
import br.com.caju.authorizer.repository.AccountRepository;
import br.com.caju.authorizer.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public record AuthorizerFallbackService(AccountRepository accountRepository,
                                        TransactionRepository transactionRepository, AccountService accountService) {

    public String approveFallbackTransaction(Transaction transaction) {
        try {
            var account = getAccount(transaction);
            var mcc = checkMccByMerchant(transaction);
            var newBalance = getCorrectBalanceValue(account, mcc) - transaction.getTotalAmount();

            if (newBalance < 0) {

                if (mcc.equals(MccEnum.CASH)) {
                    transaction.setTransactionCode(AuthorizerStatusCodeEnum.INSUFFICIENT_FUNDS);
                    return AuthorizerStatusCodeEnum.toResponseJson(transaction.getTransactionCode().toString());
                }

                var fallback = account.getCash() - transaction.getTotalAmount();

                if (fallback < 0) {
                    transaction.setTransactionCode(AuthorizerStatusCodeEnum.INSUFFICIENT_FUNDS);
                    return AuthorizerStatusCodeEnum.toResponseJson(transaction.getTransactionCode().toString());
                }

                accountRepository.updateCashBalanceById(account.getId(), fallback);
                transaction.setTransactionCode(AuthorizerStatusCodeEnum.APPROVED);
                transactionRepository.save(transaction);
                return AuthorizerStatusCodeEnum.toResponseApproveJson(transaction.getTransactionCode().toString(), transaction.getId());
            }

            updateCorrectBalance(account, mcc, newBalance);
            transaction.setTransactionCode(AuthorizerStatusCodeEnum.APPROVED);
            transactionRepository.save(transaction);
            return AuthorizerStatusCodeEnum.toResponseApproveJson(transaction.getTransactionCode().toString(), transaction.getId());

        } catch (Exception e) {

            transaction.setTransactionCode(AuthorizerStatusCodeEnum.PROCESSING_TRANSACTION_ERROR);
            return AuthorizerStatusCodeEnum.toResponseJson(transaction.getTransactionCode().toString());

        }
    }

    private Account getAccount(Transaction transaction) {
        return accountService.findAccountById(transaction.getAccount());
    }

    private MccEnum checkMccByMerchant(Transaction transaction) {

        final Map<Pattern, MccEnum> merchantNameHashMap = new HashMap<>();

        // Adicione regras específicas para comerciantes conhecidos
        merchantNameHashMap.put(Pattern.compile("UBER TRIP", Pattern.CASE_INSENSITIVE), MccEnum.CASH);
        merchantNameHashMap.put(Pattern.compile("UBER EATS", Pattern.CASE_INSENSITIVE), MccEnum.MEAL);
        merchantNameHashMap.put(Pattern.compile("PAG\\*JoseDaSilva", Pattern.CASE_INSENSITIVE), MccEnum.CASH);
        merchantNameHashMap.put(Pattern.compile("PICPAY\\*BILHETEUNICO", Pattern.CASE_INSENSITIVE), MccEnum.CASH);

        String merchantName = transaction.getMerchant();

        // Verifique se o nome do comerciante corresponde a algum padrão conhecido
        for (Map.Entry<Pattern, MccEnum> entry : merchantNameHashMap.entrySet()) {
            if (entry.getKey().matcher(merchantName).find()) {
                return entry.getValue();
            }
        }

        // Se não houver correspondência, use o MCC da transação
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
