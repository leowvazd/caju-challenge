package br.com.caju.authorizer.controller;

import static br.com.caju.authorizer.domain.ApplicationConstants.REQUEST_MAPPING_FALLBACK;

import br.com.caju.authorizer.entity.Transaction;
import br.com.caju.authorizer.service.AuthorizerFallbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(REQUEST_MAPPING_FALLBACK)
public record AuthorizerFallbackController(AuthorizerFallbackService authorizerFallbackService) {

    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Return transaction code status")})
    @Operation(description = "Process the transaction with fallback")
    @PostMapping
    public String approveFallbackTransaction(@RequestBody Transaction transaction) {
        return authorizerFallbackService.approveFallbackTransaction(transaction);
    }
}
