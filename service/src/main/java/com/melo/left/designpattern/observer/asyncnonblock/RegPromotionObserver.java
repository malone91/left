package com.melo.left.designpattern.observer.asyncnonblock;

import com.google.common.eventbus.Subscribe;

public class RegPromotionObserver {

    @Subscribe
    public void handleRegSuccess() {
        System.out.println("新用户添加优惠券成功");
    }
}
