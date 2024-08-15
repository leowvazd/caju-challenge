package br.com.caju.authorizer.entity;

import br.com.caju.authorizer.domain.enums.AuthorizerStatusCodeEnum;
import br.com.caju.authorizer.domain.enums.MccEnum;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    private Long account;

    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    private MccEnum mcc;

    private String merchant;

    @Setter
    @Nullable
    @Enumerated(EnumType.STRING)
    private AuthorizerStatusCodeEnum transactionCode;
}

