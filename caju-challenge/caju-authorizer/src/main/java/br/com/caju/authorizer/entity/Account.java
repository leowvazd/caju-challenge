package br.com.caju.authorizer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    private String owner;

    private Double food;

    private Double meal;

    private Double cash;
}

