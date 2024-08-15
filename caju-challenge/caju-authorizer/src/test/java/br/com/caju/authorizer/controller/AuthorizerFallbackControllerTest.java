package br.com.caju.authorizer.controller;

import static br.com.caju.authorizer.support.TransactionSupport.defaultTransaction;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.caju.authorizer.domain.enums.AuthorizerStatusCodeEnum;
import br.com.caju.authorizer.service.AuthorizerFallbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AuthorizerFallbackControllerTest {

    private AuthorizerFallbackController controller;
    private AuthorizerFallbackService service;

    @BeforeEach
    public void setup() {
        service = mock(AuthorizerFallbackService.class);
        controller = new AuthorizerFallbackController(service);
    }

    @Test
    @DisplayName("Given Transaction When Approve Fallback Transaction Then Return Transaction Status")
    public void givenTransactionWhenApproveFallbackTransactionThenReturnTransactionStatus() {
        var transaction = defaultTransaction().build();

        when(service.approveFallbackTransaction(any())).thenReturn(String.valueOf(AuthorizerStatusCodeEnum.APPROVED));

        var response = controller.approveFallbackTransaction(transaction);
        assertThat(response).isNotEmpty();
    }
}
