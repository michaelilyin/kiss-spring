package ru.hrh.houses.entity.update

import ru.hrh.houses.model.invitation.InvitationResolution
import java.time.LocalDateTime
import java.util.*

data class ResolveInvitationEntityUpdate(
  val resolutionStatus: InvitationResolution,
  val resolution: String,
  val updatedBy: UUID,
  val updatedAt: LocalDateTime
) {

}
