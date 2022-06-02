package com.buinam.schedulemanger.service;

import java.util.concurrent.CompletableFuture;

public interface FutureService {
    CompletableFuture<String> saveUsers(String user);
}
