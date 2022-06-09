//package com.buinam.schedulemanger.sandbox;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.Future;
//import java.util.logging.Logger;
//
//@SpringBootTest
//@Service
//public class TestFuture {
//    Logger logger = Logger.getLogger(TestFuture.class.getName());
//    @Async
//    public CompletableFuture<String> saveUsers(String name) {
//        //sleep for sometime
//        System.out.println("Saving user: " + name + " from thread " + Thread.currentThread().getName());
//        String newName = name;
//        try {
//            Thread.sleep(3000);
//            newName = name + " - " + Thread.currentThread().getName();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("Saved user: " + newName + " from thread " + Thread.currentThread().getName());
//        return CompletableFuture.completedFuture(newName);
//    }
//
//    @Test
//    public void testFuture() throws ExecutionException, InterruptedException {
//        CompletableFuture<String> future = saveUsers("buinam");
//        System.out.println("future: " + future.toString());
//        System.out.println("future.get(): " + future.get());
//    }
//
//    @Test
//    @Async
//    public void testFuture2() {
//        List<String> users = new ArrayList<>();
//        users.add("buinam");
//        users.add("buinam2");
//
//        saveUsers(users.get(0));
//        saveUsers(users.get(1));
//    }
//}
