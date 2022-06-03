package com.buinam.schedulemanger.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

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

    @Override
    @Async
    public Future<Integer> testFuture() {
        try {
            List<String> users = new ArrayList<>();
            users.add("buinam");
            users.add("buinam2");
            users.add("buinam3");
            System.out.println("before the loop");
            for (String user : users) {
                CompletableFuture<String> future = saveUsers(user);

            }
            System.out.println("after the loop");
            return new AsyncResult<>(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AsyncResult<>(0);
    }

}
