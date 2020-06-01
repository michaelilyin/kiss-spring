package ru.hrh.houses.model.invitation

import ru.hrh.houses.entity.update.ResolveInvitationEntityUpdate
import java.time.LocalDateTime
import java.util.*

data class HouseInvitationResolutionInput(val resolution: String) {
}

fun HouseInvitationResolutionInput.toResolveUpdate(
  status: InvitationResolution,
  userId: UUID,
  updatedAt: LocalDateTime = LocalDateTime.now()
): ResolveInvitationEntityUpdate = ResolveInvitationEntityUpdate(
  status, this.resolution, userId, updatedAt
)
