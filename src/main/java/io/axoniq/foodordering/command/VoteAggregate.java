package io.axoniq.foodordering.command;

import io.axoniq.foodordering.coreapi.ChangeVoteCommand;
import io.axoniq.foodordering.coreapi.CreateVoteCommand;
import io.axoniq.foodordering.coreapi.VoteCastEvent;
import io.axoniq.foodordering.coreapi.VoteCreatedEvent;
import io.axoniq.foodordering.service.AcceptVoteService;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.UUID;

@Aggregate
public class VoteAggregate {

    private static final Logger logger = LoggerFactory.getLogger(VoteAggregate.class);

    @Autowired
    private AcceptVoteService acceptVoteService;

    @AggregateIdentifier
    private UUID voteId;
    private int menuId;

    public VoteAggregate() {
        // Required by Axon
    }

    @CommandHandler
    public VoteAggregate(CreateVoteCommand command) {
        AggregateLifecycle.apply(new VoteCreatedEvent(command.getVoteId()));
        AggregateLifecycle.apply(new VoteCastEvent(command.getVoteId(),0));
    }

    @CommandHandler
    public void handle(ChangeVoteCommand command) {
        if (acceptVoteService.check()) {
            AggregateLifecycle.apply(new VoteCastEvent(command.getVoteId(), command.getMenuId()));
        } else {
            logger.warn(acceptVoteService.message() + voteId);
        }
    }

    @EventSourcingHandler
    public void on(VoteCreatedEvent event) {
        voteId = event.getVoteId();
    }

    @EventSourcingHandler
    public void on(VoteCastEvent event) {
        voteId = event.getVoteId();
        menuId = event.getMenuId();
    }
}
