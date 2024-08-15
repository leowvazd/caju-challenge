package br.com.caju.authorizer.service;

import static java.util.Objects.isNull;

import br.com.caju.authorizer.entity.Account;
import br.com.caju.authorizer.exception.AccountException;
import br.com.caju.authorizer.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public record AccountService(AccountRepository accountRepository) {

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account findAccountById(Long id) {
        var response = accountRepository.findById(id).orElse(null);

        if (isNull(response)) {
            throw new AccountException("The account with the given id does not exist");
        }
        return response;
    }
}
