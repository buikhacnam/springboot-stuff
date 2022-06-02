package com.buinam.schedulemanger.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class FutureServiceImpl implements FutureService {

    @Override
    @Async
    public CompletableFuture<String> saveUsers(String name) {
        //sleep for sometime
        System.out.println("Saving user: " + name + " from thread " + Thread.currentThread().getName());
        String newName = name;
        try {
            Thread.sleep(3000);
            newName = name + " - " + Thread.currentThread().getName();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Saved user: " + newName + " from thread " + Thread.currentThread().getName());
        return CompletableFuture.completedFuture(newName);
    }

}
