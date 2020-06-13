package com.melo.left.concurrent.transfer;

public class Account {

    private Allocator allocator;
    private int balance;

    void transfer(Account target, int amt) {
        while (!allocator.apply(this, target)) {
         ;
        }
        try {
            synchronized (this) {
                synchronized (target) {
                    if (this.balance > amt) {
                        this.balance -= amt;
                        target.balance += amt;
                    }
                }
            }
        } finally {
            allocator.free(this, target);
        }
    }
}
