package br.com.caju.authorizer.service;

import static br.com.caju.authorizer.support.AccountSupport.defaultAccount;
import static br.com.caju.authorizer.support.ConstantsSupport.*;
import static br.com.caju.authorizer.support.TransactionSupport.defaultTransaction;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.caju.authorizer.domain.enums.MccEnum;
import br.com.caju.authorizer.exception.AccountException;
import br.com.caju.authorizer.exception.TransactionException;
import br.com.caju.authorizer.repository.AccountRepository;
import br.com.caju.authorizer.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Optional;

public class AuthorizerServiceTest {

    private AuthorizerService authorizerService;
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private AccountService accountService;

    @BeforeEach
    public void setup() {
        accountRepository = mock(AccountRepository.class);
        transactionRepository = mock(TransactionRepository.class);
        accountService = mock(AccountService.class);
        authorizerService = new AuthorizerService(accountRepository, transactionRepository, accountService);
    }

    @Test
    @DisplayName("Given Transaction When Approve Simple Transaction Food Then Return Transaction Code Approve")
    public void givenTransactionWhenApproveSimpleTransactionFoodThenReturnTransactionCodeApprove() {
        var transaction = defaultTransaction().build();

        when(accountService.findAccountById(ID)).thenReturn(defaultAccount().build());
        when(transactionRepository.save(any())).thenReturn(defaultTransaction().build());

        var response = authorizerService.approveSimpleTransaction(transaction);
        assertThat(response).isNotEmpty();
    }

    @Test
    @DisplayName("Given Transaction When Approve Simple Transaction Meal Then Return Transaction Code Approve")
    public void givenTransactionWhenApproveSimpleTransactionMealThenReturnTransactionCodeApprove() {
        var transaction = defaultTransaction().withMcc(MccEnum.MEAL).build();

        when(accountService.findAccountById(ID)).thenReturn(defaultAccount().build());
        when(transactionRepository.save(any())).thenReturn(defaultTransaction().withMcc(MccEnum.MEAL).build());

        var response = authorizerService.approveSimpleTransaction(transaction);
        assertThat(response).isNotEmpty();
    }

    @Test
    @DisplayName("Given Transaction When Approve Simple Transaction Cash Then Return Transaction Code Approve")
    public void givenTransactionWhenApproveSimpleTransactionCashThenReturnTransactionCodeApprove() {
        var transaction = defaultTransaction().withMerchant(MERCHANT_UBER_TRIP).build();

        when(accountService.findAccountById(ID)).thenReturn(defaultAccount().build());
        when(transactionRepository.save(any())).thenReturn(defaultTransaction().withMcc(MccEnum.MEAL).build());

        var response = authorizerService.approveSimpleTransaction(transaction);
        assertThat(response).isNotEmpty();
    }

    @Test
    @DisplayName("Given Transaction When Approve Simple Transaction Food Then Return Transaction Code Insufficient Funds")
    public void givenTransactionWhenApproveSimpleTransactionFoodThenReturnTransactionCodeInsufficientFunds() {
        var transaction = defaultTransaction().build();

        when(accountService.findAccountById(ID)).thenReturn(defaultAccount().withFood(BALANCE_ZERO).build());
        when(transactionRepository.save(any())).thenReturn(defaultTransaction().build());

        var response = authorizerService.approveSimpleTransaction(transaction);
        assertThat(response).isNotEmpty();
    }

    @Test
    @DisplayName("Given Transaction When Approve Simple Transaction Food Then Return Transaction Code Processing Transaction Error")
    public void givenTransactionWhenApproveSimpleTransactionFoodThenReturnTransactionCodeProcessingTransactionError() {
        var transaction = defaultTransaction().build();

        when(accountService.findAccountById(anyLong())).thenThrow(new AccountException(MESSAGE_ERROR));

        var response = authorizerService.approveSimpleTransaction(transaction);
        assertThat(response).isNotEmpty();
    }

    @Test
    @DisplayName("Given Transaction Id When Find Transaction Then Return Transaction")
    public void givenTransactionIdWhenFindTransactionThenReturnTransaction() {
        var transaction = defaultTransaction().build();

        when(transactionRepository.findById(anyLong())).thenReturn(Optional.ofNullable(transaction));

        var response = authorizerService.findTransactionById(ID);
        assertThat(response).isNotNull();
    }

    @Test
    @DisplayName("Given Null Transaction Id When Find Transaction Then Throw Exception")
    public void givenNullTransactionIdWhenFindTransactionThenThrowException() {
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authorizerService.findTransactionById(ID)).isInstanceOf(TransactionException.class);
    }
}
