package io.axoniq.foodordering.coreapi

import java.util.*

class VoteCreatedEvent(
        val voteId: UUID
)

data class VoteCastEvent(
        val voteId: UUID,
        val menuId: Int
)
