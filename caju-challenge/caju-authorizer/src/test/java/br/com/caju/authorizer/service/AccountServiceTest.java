package br.com.caju.authorizer.service;

import static br.com.caju.authorizer.support.AccountSupport.defaultAccount;
import static br.com.caju.authorizer.support.ConstantsSupport.ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.caju.authorizer.exception.AccountException;
import br.com.caju.authorizer.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Optional;

public class AccountServiceTest {

    private AccountService service;
    private AccountRepository repository;

    @BeforeEach
    public void setup() {
        repository = mock(AccountRepository.class);
        service = new AccountService(repository);
    }

    @Test
    @DisplayName("Given Account When Create Account Then Return Account")
    public void givenAccountWhenCreateAccountThenReturnAccount() {
        var account = defaultAccount().build();

        when(repository.save(any())).thenReturn(defaultAccount().build());

        var response = service.createAccount(account);
        assertThat(response).hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("Given Account Id When Find Account Then Return Account")
    public void givenAccountIdWhenFindAccountThenReturnAccount() {
        when(repository.findById(any())).thenReturn(Optional.ofNullable(defaultAccount().build()));

        var response = service.findAccountById(ID);
        assertThat(response).hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("Given Null Account Id When Find Account Then Throw Exception")
    public void givenNullAccountIdWhenFindAccountThenThrowException() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findAccountById(ID)).isInstanceOf(AccountException.class);
    }
}
