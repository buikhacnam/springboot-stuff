package com.buinam.schedulemanger.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface FutureService {
    CompletableFuture<String> saveUsers(String user);

    Future<Integer> testFuture();
}
