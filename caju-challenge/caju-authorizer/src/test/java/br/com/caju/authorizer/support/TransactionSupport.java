package br.com.caju.authorizer.support;

import static br.com.caju.authorizer.domain.enums.AuthorizerStatusCodeEnum.APPROVED;
import static br.com.caju.authorizer.support.ConstantsSupport.*;

import br.com.caju.authorizer.domain.enums.MccEnum;
import br.com.caju.authorizer.entity.Transaction;

public class TransactionSupport {
    public static Transaction.TransactionBuilder defaultTransaction() {
        return Transaction
                .builder()
                .withId(ID)
                .withAccount(ACCOUNT)
                .withTotalAmount(TOTAL_AMOUNT)
                .withMcc(MccEnum.FOOD)
                .withMerchant(MERCHANT)
                .withTransactionCode(APPROVED);
    }
}
