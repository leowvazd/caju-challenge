package br.com.caju.authorizer.controller;

import static br.com.caju.authorizer.domain.ApplicationContants.REQUEST_MAPPING_AUTHORIZER;

import br.com.caju.authorizer.entity.Transaction;
import br.com.caju.authorizer.service.AuthorizerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(REQUEST_MAPPING_AUTHORIZER)
public record AuthorizerController(AuthorizerService authorizerService) {

    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Return transaction code status")})
    @Operation(description = "Process the simple transaction")
    @PostMapping
    public String approveSimpleTransaction(@RequestBody Transaction transaction) {
        return authorizerService.approveSimpleTransaction(transaction);
    }

    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Return the transaction")})
    @Operation(description = "Find transaction by id")
    @GetMapping("/transaction/{id}")
    public Transaction findTransactionById(@PathVariable Long id) {
        return authorizerService.findTransactionById(id);
    }
}
