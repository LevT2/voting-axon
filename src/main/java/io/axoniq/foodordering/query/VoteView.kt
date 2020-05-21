package io.axoniq.foodordering.query

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class VoteView(
        @Id val voteId: UUID,
        var menuId: Int
) {

    fun castVote(menuId: Int) {
        this.menuId = menuId
    }
}

interface VoteViewRepository : JpaRepository<VoteView, UUID>