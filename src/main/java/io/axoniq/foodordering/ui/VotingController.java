package io.axoniq.foodordering.ui;

import io.axoniq.foodordering.coreapi.ChangeVoteCommand;
import io.axoniq.foodordering.coreapi.CreateVoteCommand;
import io.axoniq.foodordering.coreapi.FindVoteQuery;
import io.axoniq.foodordering.query.VoteView;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RequestMapping("/api/v1")
@RestController
public class VotingController {
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public VotingController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping("/")
    public CompletableFuture<UUID> createVote() {
        return commandGateway.send(new CreateVoteCommand(UUID.randomUUID()));
    }

    @PostMapping("/{voteId}/change/{menuId}")
    public void changeVote(@PathVariable("voteId") String voteId,
                           @PathVariable("menuId") Integer menuId) {
        commandGateway.send(new ChangeVoteCommand(
                UUID.fromString(voteId), menuId)
        );
    }

    @GetMapping("/{voteId}")
    public CompletableFuture<VoteView> findVote(@PathVariable("voteId") String voteId) {
        return queryGateway.query(
                new FindVoteQuery(UUID.fromString(voteId)),
                ResponseTypes.instanceOf(VoteView.class)
        );
    }
}
