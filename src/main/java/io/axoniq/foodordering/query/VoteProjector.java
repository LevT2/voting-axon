package io.axoniq.foodordering.query;

import io.axoniq.foodordering.coreapi.FindVoteQuery;
import io.axoniq.foodordering.coreapi.VoteCastEvent;
import io.axoniq.foodordering.coreapi.VoteCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class VoteProjector {
    private final VoteViewRepository voteViewRepository;

    public VoteProjector(VoteViewRepository voteViewRepository) {
        this.voteViewRepository = voteViewRepository;
    }

    @EventHandler
    public void on(VoteCreatedEvent event) {
        VoteView voteView = new VoteView(event.getVoteId(), 0);
        voteViewRepository.save(voteView);
    }

    @EventHandler
    public void on(VoteCastEvent event) {
        voteViewRepository.findById(event.getVoteId()).ifPresent(
                voteView -> voteView.castVote(event.getMenuId())
        );
    }

    @QueryHandler
    public VoteView handle(FindVoteQuery query) {
        return voteViewRepository.findById(query.getVoteId()).orElse(null);
    }
}
