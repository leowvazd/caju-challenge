package br.com.caju.authorizer.controller;

import static br.com.caju.authorizer.domain.ApplicationConstants.REQUEST_MAPPING_ACCOUNT;

import br.com.caju.authorizer.entity.Account;
import br.com.caju.authorizer.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(REQUEST_MAPPING_ACCOUNT)
public record AccountController(AccountService accountService) {

    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Return created account")})
    @Operation(description = "Create the account")
    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @Operation(description = "Find account by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Return the transaction")})
    @GetMapping("/{id}")
    public Account findAccountById(@PathVariable Long id) {
        return accountService.findAccountById(id);
    }
}
