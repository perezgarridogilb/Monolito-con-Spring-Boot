package com.springcloud.kafka.products_command.handlers;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import com.springcloud.kafka.products_command.messaging.ReplyInbox;
import com.springcloud.kafka.products_command.models.Repply;

@Configuration
public class RepliesConsumer {
    private final ReplyInbox repplyInbox;

    public RepliesConsumer(ReplyInbox replyInbox) {
        this.repplyInbox = replyInbox;
    }

    @Bean
    public Consumer<Message<Repply<?>>> handleReplies() {
        return message -> {
            if (message == null || message.getPayload() == null) {
                return;
            }

            String correlationId = message.getHeaders().get("correlationId", String.class);

            if (correlationId != null) {
                repplyInbox.complete(correlationId, message.getPayload());
            }
        };
    }
}
