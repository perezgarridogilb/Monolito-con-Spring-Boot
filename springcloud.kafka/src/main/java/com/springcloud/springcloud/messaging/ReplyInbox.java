package com.springcloud.springcloud.messaging;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.springcloud.springcloud.models.Repply;

@Component
public class ReplyInbox {
    private final ConcurrentHashMap<String, CompletableFuture<Repply<?>>> pending = new ConcurrentHashMap<>();

    public CompletableFuture<Repply<?>> register(String correlationId) {
        CompletableFuture<Repply<?>> future = new CompletableFuture<>();
        pending.put(correlationId, future);
        return future;
     }

     public void complete(String correlationId, Repply<?> repply) {
        // CompletableFuture<Repply<?>> future = new CompletableFuture<>();
        if (correlationId == null) {
            throw new NullPointerException("correlationId can't be null");
        }
        CompletableFuture<Repply<?>> future = pending.remove(correlationId);
        if (future != null) {
            
            future.complete(repply);
        }
     }
}
