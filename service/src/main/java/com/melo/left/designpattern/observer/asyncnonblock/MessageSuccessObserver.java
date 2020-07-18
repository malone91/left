package com.melo.left.designpattern.observer.asyncnonblock;

import com.google.common.eventbus.Subscribe;

public class MessageSuccessObserver {

    @Subscribe
    public void handleMessageSuccess() {
        System.out.println("新用户发送短信成功");
    }
}
