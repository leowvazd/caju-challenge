package br.com.caju.authorizer.domain.enums;

public enum AuthorizerStatusCodeEnum {
    APPROVED("00 - Approved"),
    INSUFFICIENT_FUNDS("51 - Insufficient funds"),
    PROCESSING_TRANSACTION_ERROR("07 - Processing Transaction Error");

    private final String code;

    AuthorizerStatusCodeEnum(String code) {
        this.code = code;
    }

    public static String toResponseJson(String code) {
        return "{\"code\": \"" + code + "\"}";
    }

    public static String toResponseApproveJson(String code, Long id) {
        return "{\"code\": \"" + code + "\", \"transaction_id\": " + id + "}";
    }
}