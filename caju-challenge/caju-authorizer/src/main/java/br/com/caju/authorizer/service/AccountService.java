package br.com.caju.authorizer.service;

import static br.com.caju.authorizer.domain.ApplicationConstants.ACCOUNT_ID;
import static java.util.Objects.isNull;
import static net.logstash.logback.marker.Markers.append;

import br.com.caju.authorizer.entity.Account;
import br.com.caju.authorizer.exception.AccountException;
import br.com.caju.authorizer.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public record AccountService(AccountRepository accountRepository) {

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account findAccountById(Long id) {
        log.info(append(ACCOUNT_ID, id), "Starting to find account by id");
        var response = accountRepository.findById(id).orElse(null);

        if (isNull(response)) {
            throw new AccountException("The account with the given id does not exist");
        }

        log.info(append(ACCOUNT_ID, id), "Account was found successfully");
        return response;
    }
}
