package br.com.caju.authorizer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthorizerApplicationTest {

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Given Authorizer Application When Run Application Then Return Success")
    void givenAuthorizerApplicationWhenRunApplicationThenReturnSuccess() {
        assertDoesNotThrow(() -> {
            try (var mockedSpringApplication = mockStatic(SpringApplication.class)) {
                mockedSpringApplication.when(() -> SpringApplication.run(AuthorizerApplication.class, new String[]{})).thenReturn(null);
                AuthorizerApplication.main(new String[]{});
            }
        });
    }
}
