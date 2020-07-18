package com.melo.left.designpattern.observer.asyncnonblock;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

import java.util.List;
import java.util.concurrent.Executors;

public class UserController {

    private UserService userService;
    private EventBus eventBus;
    private static final int DEFAULT_EVENT_BUS_POOL_SIZE = 20;

    public UserController() {
        //同步阻塞模式
//        eventBus = new EventBus();
        eventBus = new AsyncEventBus(Executors.newFixedThreadPool(DEFAULT_EVENT_BUS_POOL_SIZE));
    }

    public void setRegObservers(List<Object> observers) {
        for (Object object : observers) {
            eventBus.register(object);
        }
    }

    public Long regist(String telephone, String password) {
        Long userId = userService.regist(telephone, password);
        eventBus.post(userId);

        return userId;
    }
}
