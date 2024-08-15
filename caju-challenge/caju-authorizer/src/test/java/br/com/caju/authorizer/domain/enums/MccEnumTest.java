package br.com.caju.authorizer.domain.enums;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MccEnumTest {

    @Test
    @DisplayName("Given Mcc Code When Get From Code Then Return Mcc Food")
    public void givenMccCodeWhenGetFromCodeThenReturnMccEnum() {
        var code = MccEnum.fromCode("5411");
        assertThat(code).isEqualTo(MccEnum.FOOD);
    }

    @Test
    @DisplayName("Given Mcc Code When Get From Code Then Return Mcc Meal")
    public void givenMccCodeWhenGetFromCodeThenReturnMccMeal() {
        var code = MccEnum.fromCode("5811");
        assertThat(code).isEqualTo(MccEnum.MEAL);
    }

    @Test
    @DisplayName("Given Mcc Code When Get From Code Then Return Mcc Cash")
    public void givenMccCodeWhenGetFromCodeThenReturnMccCash() {
        var code = MccEnum.fromCode("");
        assertThat(code).isEqualTo(MccEnum.CASH);
    }
}
