package br.com.caju.authorizer.repository;

import br.com.caju.authorizer.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.food = :food WHERE a.id = :id")
    void updateFoodBalanceById(@Param("id") Long id, @Param("food") Double food);

    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.meal = :meal WHERE a.id = :id")
    void updateMealBalanceById(@Param("id") Long id, @Param("meal") Double meal);

    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.cash = :cash WHERE a.id = :id")
    void updateCashBalanceById(@Param("id") Long id, @Param("cash") Double cash);
}
