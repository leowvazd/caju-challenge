package br.com.caju.authorizer.controller;

import static br.com.caju.authorizer.support.ConstantsSupport.ID;
import static br.com.caju.authorizer.support.TransactionSupport.defaultTransaction;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.caju.authorizer.domain.enums.AuthorizerStatusCodeEnum;
import br.com.caju.authorizer.service.AuthorizerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AuthorizerControllerTest {

    private AuthorizerController controller;
    private AuthorizerService service;

    @BeforeEach
    public void setup() {
        service = mock(AuthorizerService.class);
        controller = new AuthorizerController(service);
    }

    @Test
    @DisplayName("Given Transaction When Approve Simple Transaction Then Return Transaction Status")
    public void givenTransactionWhenApproveSimpleTransactionThenReturnTransactionStatus() {
        var transaction = defaultTransaction().build();

        when(service.approveSimpleTransaction(any())).thenReturn(String.valueOf(AuthorizerStatusCodeEnum.APPROVED));

        var response = controller.approveSimpleTransaction(transaction);
        assertThat(response).isNotEmpty();
    }

    @Test
    @DisplayName("Given Transaction Id When Find Transaction Then Return Transaction")
    public void givenTransactionIdWhenFindTransactionThenReturnTransaction() {

        when(service.findTransactionById(any())).thenReturn(defaultTransaction().build());

        var response = controller.findTransactionById(ID);
        assertThat(response).hasNoNullFieldsOrProperties();
    }
}
