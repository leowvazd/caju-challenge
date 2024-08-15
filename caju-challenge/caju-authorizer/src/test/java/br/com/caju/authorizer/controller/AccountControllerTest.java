package br.com.caju.authorizer.controller;

import static br.com.caju.authorizer.support.AccountSupport.defaultAccount;
import static br.com.caju.authorizer.support.ConstantsSupport.ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.caju.authorizer.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AccountControllerTest {

    private AccountController controller;
    private AccountService service;

    @BeforeEach
    public void setup() {
        service = mock(AccountService.class);
        controller = new AccountController(service);
    }

    @Test
    @DisplayName("Given Account When Create Account Then Return Account")
    public void givenAccountWhenCreateAccountThenReturnAccount() {
        var account = defaultAccount().build();

        when(service.createAccount(any())).thenReturn(defaultAccount().build());

        var response = controller.createAccount(account);
        assertThat(response).hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("Given Account Id When Find Account Then Return Account")
    public void givenAccountIdWhenFindAccountThenReturnAccount() {
        when(service.findAccountById(any())).thenReturn(defaultAccount().build());

        var response = controller.findAccountById(ID);
        assertThat(response).hasNoNullFieldsOrProperties();
    }
}

