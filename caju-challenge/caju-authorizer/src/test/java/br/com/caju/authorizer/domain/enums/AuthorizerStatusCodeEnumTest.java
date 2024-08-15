package br.com.caju.authorizer.domain.enums;

import static br.com.caju.authorizer.support.ConstantsSupport.ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AuthorizerStatusCodeEnumTest {

    @Test
    @DisplayName("Given Authorizer Status Code When Response Json Then Return Json String")
    public void givenAuthorizerStatusCodeWhenResponseJsonThenReturnJsonString() {
        var code = AuthorizerStatusCodeEnum.toResponseJson("51");
        assertThat(code).isEqualTo("{\"code\": \"51\"}");
    }

    @Test
    @DisplayName("Given Authorizer Status Code When Response Approve Json Then Return Json Approve String")
    public void givenAuthorizerStatusCodeWhenResponseApproveJsonThenReturnJsonApproveString() {
        var code = AuthorizerStatusCodeEnum.toResponseApproveJson("00", ID);
        assertThat(code).isEqualTo("{\"code\": \"00\", \"transaction_id\": 1}");
    }
}
