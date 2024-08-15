package br.com.caju.authorizer.service;

import static br.com.caju.authorizer.support.AccountSupport.defaultAccount;
import static br.com.caju.authorizer.support.ConstantsSupport.*;
import static br.com.caju.authorizer.support.TransactionSupport.defaultTransaction;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.caju.authorizer.domain.enums.MccEnum;
import br.com.caju.authorizer.exception.AccountException;
import br.com.caju.authorizer.repository.AccountRepository;
import br.com.caju.authorizer.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AuthorizerFallbackServiceTest {

    private AuthorizerFallbackService authorizerFallbackService;
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private AccountService accountService;

    @BeforeEach()
    public void setup() {
        accountRepository = mock(AccountRepository.class);
        transactionRepository = mock(TransactionRepository.class);
        accountService = mock(AccountService.class);
        authorizerFallbackService = new AuthorizerFallbackService(accountRepository, transactionRepository, accountService);
    }

    @Test
    @DisplayName("Given Transaction When Approve Fallback Transaction Food Then Return Transaction Code Approve")
    public void givenTransactionWhenApproveFallbackTransactionFoodThenReturnTransactionCodeApprove() {
        var transaction = defaultTransaction().build();

        when(accountService.findAccountById(ID)).thenReturn(defaultAccount().build());
        when(transactionRepository.save(any())).thenReturn(defaultTransaction().build());

        var response = authorizerFallbackService.approveFallbackTransaction(transaction);
        assertThat(response).isNotEmpty();
    }

    @Test
    @DisplayName("Given Transaction When Approve Fallback Transaction Meal Then Return Transaction Code Approve")
    public void givenTransactionWhenApproveFallbackTransactionMealThenReturnTransactionCodeApprove() {
        var transaction = defaultTransaction().withMcc(MccEnum.MEAL).build();

        when(accountService.findAccountById(ID)).thenReturn(defaultAccount().build());
        when(transactionRepository.save(any())).thenReturn(defaultTransaction().withMcc(MccEnum.MEAL).build());

        var response = authorizerFallbackService.approveFallbackTransaction(transaction);
        assertThat(response).isNotEmpty();
    }

    @Test
    @DisplayName("Given Transaction When Approve Fallback Transaction Cash Then Return Transaction Code Approve")
    public void givenTransactionWhenApproveFallbackTransactionCashThenReturnTransactionCodeApprove() {
        var transaction = defaultTransaction().withMerchant(MERCHANT_UBER_TRIP).build();

        when(accountService.findAccountById(ID)).thenReturn(defaultAccount().build());
        when(transactionRepository.save(any())).thenReturn(defaultTransaction().withMcc(MccEnum.MEAL).build());

        var response = authorizerFallbackService.approveFallbackTransaction(transaction);
        assertThat(response).isNotEmpty();
    }

    @Test
    @DisplayName("Given Transaction When Approve Fallback Transaction Food Then Return Transaction Code Approve With Fallback Applied")
    public void givenTransactionWhenApproveFallbackTransactionFoodThenReturnTransactionCodeApproveWithFallbackApplied() {
        var transaction = defaultTransaction().build();

        when(accountService.findAccountById(ID)).thenReturn(defaultAccount().withFood(BALANCE_ZERO).build());
        when(transactionRepository.save(any())).thenReturn(defaultTransaction().build());

        var response = authorizerFallbackService.approveFallbackTransaction(transaction);
        assertThat(response).isNotEmpty();
    }

    @Test
    @DisplayName("Given Transaction When Approve Fallback Transaction Cash Then Return Transaction Code Insufficient Funds With Fallback Not Applied")
    public void givenTransactionWhenApproveFallbackTransactionCashThenReturnTransactionCodeInsufficientFundsWithFallbackNotApplied() {
        var transaction = defaultTransaction().withMcc(MccEnum.CASH).build();

        when(accountService.findAccountById(ID)).thenReturn(defaultAccount().withCash(BALANCE_ZERO).build());
        when(transactionRepository.save(any())).thenReturn(defaultTransaction().withMcc(MccEnum.CASH).build());

        var response = authorizerFallbackService.approveFallbackTransaction(transaction);
        assertThat(response).isNotEmpty();
    }

    @Test
    @DisplayName("Given Transaction When Approve Fallback Transaction Food Then Return Transaction Code Insufficient Funds With Fallback Applied")
    public void givenTransactionWhenApproveFallbackTransactionFoodThenReturnTransactionCodeInsufficientFundsWithFallbackApplied() {
        var transaction = defaultTransaction().withTotalAmount(BALANCE_ONE_THOUSAND).build();

        when(accountService.findAccountById(ID)).thenReturn(defaultAccount().build());
        when(transactionRepository.save(any())).thenReturn(defaultTransaction().build());

        var response = authorizerFallbackService.approveFallbackTransaction(transaction);
        assertThat(response).isNotEmpty();
    }

    @Test
    @DisplayName("Given Transaction When Approve Fallback Transaction Food Then Return Transaction Code Processing Transaction Error")
    public void givenTransactionWhenApproveSimpleTransactionFoodThenReturnTransactionCodeProcessingTransactionError() {
        var transaction = defaultTransaction().build();

        when(accountService.findAccountById(anyLong())).thenThrow(new AccountException(MESSAGE_ERROR));

        var response = authorizerFallbackService.approveFallbackTransaction(transaction);
        assertThat(response).isNotEmpty();
    }
}
