package io.axoniq.foodordering.coreapi

import org.axonframework.commandhandling.RoutingKey
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

class CreateVoteCommand(
        @RoutingKey val voteId: UUID
)

data class ChangeVoteCommand(
        @TargetAggregateIdentifier val voteId: UUID,
        val menuId: Int
)

data class CloseVoteCommand(
        @TargetAggregateIdentifier val voteId: UUID
)