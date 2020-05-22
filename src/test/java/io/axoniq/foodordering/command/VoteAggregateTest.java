package io.axoniq.foodordering.command;

import io.axoniq.foodordering.coreapi.CreateVoteCommand;
import io.axoniq.foodordering.coreapi.VoteCastEvent;
import io.axoniq.foodordering.coreapi.VoteCreatedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class VoteAggregateTest {

    private FixtureConfiguration<VoteAggregate> fixture;

    private static final UUID uuid = UUID.randomUUID();

    private static final int newMenuId = 5;

    @BeforeEach
    void setUp() {
        fixture = new AggregateTestFixture<>(VoteAggregate.class);
    }

    @Test
    public void givenNoPriorActivity_whenCreateVoteCommand() {
        fixture.givenNoPriorActivity()
                .when(new CreateVoteCommand(uuid))
                .expectEvents(new VoteCreatedEvent(uuid), new VoteCastEvent(uuid, 0));
    }
}