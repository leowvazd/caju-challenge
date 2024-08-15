package br.com.caju.authorizer.exception;

import static br.com.caju.authorizer.support.ConstantsSupport.MESSAGE_ERROR;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AccountExceptionTest {

    @Test
    @DisplayName("Given Message When Account Error Then Must Return Exception")
    public void givenMessageWhenAccountErrorThenMustReturnException() {
        AccountException exception = new AccountException(MESSAGE_ERROR);
        assertThat(MESSAGE_ERROR).isEqualTo(exception.getMessage());
    }

}
