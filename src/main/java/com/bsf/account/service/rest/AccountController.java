package com.bsf.account.service.rest;

import com.bsf.account.service.common.model.AddAccountRequest;
import com.bsf.account.service.common.model.AddAccountResponse;
import com.bsf.account.service.common.model.TransferFundsRequest;
import com.bsf.account.service.common.model.TransferFundsResponse;
import com.bsf.account.service.service.AccountService;
import com.bsf.account.service.service.FundsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("account")
public class AccountController {

    private AccountService accountService;
    private FundsService fundsService;

    @PostMapping
    public ResponseEntity<AddAccountResponse> addAccount(@Valid @RequestBody AddAccountRequest addAccountRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.addAccount(addAccountRequest));
    }

    @PostMapping("{accountId}/transferFunds")
    public ResponseEntity<TransferFundsResponse> transferFunds(
            @Valid @PathVariable Long accountId,
            @Valid @RequestBody TransferFundsRequest transferFundsRequest) {
        return ResponseEntity.ok(fundsService.transferFunds(accountId, transferFundsRequest));
    }
}
