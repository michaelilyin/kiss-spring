package ru.hrh.houses.model.invitation

import net.kiss.service.model.ID
import ru.hrh.houses.entity.HouseInvitationEntity
import java.time.LocalDateTime
import java.util.*

data class HouseInvitationView(
  val id: ID,
  val userEmail: String,
  val invitedBy: UUID,
  val invitedAt: LocalDateTime,
  val invitation: String,
  val resolutionStatus: InvitationResolution,
  val resolution: String?,
  val resolvedBy: UUID?,
  val resolvedAt: LocalDateTime?
) {
}

fun HouseInvitationEntity.toView() = HouseInvitationView(
  id = id!!.toString(),
  userEmail = userEmail,
  invitedBy = invitedBy,
  invitedAt = invitedAt,
  invitation = invitation,
  resolutionStatus = resolutionStatus,
  resolution = resolution,
  resolvedAt = resolvedAt,
  resolvedBy = resolvedBy
)
