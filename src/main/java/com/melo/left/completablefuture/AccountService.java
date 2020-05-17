package com.melo.left.completablefuture;

import java.util.concurrent.CompletableFuture;


public interface AccountService {

    CompletableFuture<Void> add(int account, int amount);
}
