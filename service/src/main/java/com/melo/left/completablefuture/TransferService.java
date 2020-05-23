package com.melo.left.completablefuture;

import java.util.concurrent.CompletableFuture;


public interface TransferService {

    CompletableFuture<Void> transfer(int fromAccount, int toAccount, int amount);
}
