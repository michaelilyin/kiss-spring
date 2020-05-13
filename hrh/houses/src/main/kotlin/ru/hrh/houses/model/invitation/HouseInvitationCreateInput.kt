package ru.hrh.houses.model.invitation

import ru.hrh.houses.entity.HouseInvitationEntity
import java.time.LocalDateTime
import java.util.*

data class InvitationCreateInput(
  val houseId: String,
  val userEmail: String,
  val invitation: String
)

fun InvitationCreateInput.toEntity(currentUser: UUID) = HouseInvitationEntity(
  id = null,
  houseId = houseId.toLong(),
  userEmail = userEmail,
  invitedBy = currentUser,
  invitedAt = LocalDateTime.now(),
  invitation = invitation,
  resolutionStatus = InvitationResolution.NEW,
  resolution = null,
  resolvedBy = null,
  resolvedAt = null
)
