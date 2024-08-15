package br.com.caju.authorizer.support;

import static br.com.caju.authorizer.support.ConstantsSupport.*;

import br.com.caju.authorizer.entity.Account;

public class AccountSupport {
    public static Account.AccountBuilder defaultAccount() {
        return Account
                .builder()
                .withId(ID)
                .withOwner(OWNER)
                .withFood(BALANCE_VALUE)
                .withMeal(BALANCE_VALUE)
                .withCash(BALANCE_VALUE);
    }
}
