package io.axoniq.foodordering.service;

import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class AcceptVoteService {

    public static final LocalTime deadLine = LocalTime.of(23, 0);

    public boolean check() {
        return LocalTime.now().isBefore(deadLine);
    }

    public String message() {
        return "There was a late attempt to update aggregate with voteId: ";
    }
}
