package com.melo.left.completablefuture.impl;

import com.melo.left.completablefuture.AccountService;
import com.melo.left.completablefuture.TransferService;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;


public class TransferServiceImpl implements TransferService {

    @Inject
    private AccountService accountService;

    @Override
    public CompletableFuture<Void> transfer(int fromAccount, int toAccount, int amount) {
        return accountService.add(fromAccount, -1 * amount)
                .thenCompose(v -> accountService.add(toAccount, amount));
    }
}
