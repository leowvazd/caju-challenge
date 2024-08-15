package br.com.caju.authorizer.domain;

import org.springframework.stereotype.Component;

@Component
public class ApplicationConstants {

    public static final String REQUEST_MAPPING_ACCOUNT = "/account";
    public static final String REQUEST_MAPPING_AUTHORIZER = "/authorizer";
    public static final String REQUEST_MAPPING_FALLBACK = "/fallback";
    public static final String ACCOUNT_ID = "account_id";
    public static final String TRANSACTION_ID = "transaction_d";
    public static final String TRANSACTION = "transaction";
    public static final String TRANSACTION_CODE = "transaction_code";
    public static final String MCC_CODE = "mcc_code";
    public static final String MERCHANT_NAME = "merchant_name";
    public static final String BALANCE_VALUE = "balance_value";

}
