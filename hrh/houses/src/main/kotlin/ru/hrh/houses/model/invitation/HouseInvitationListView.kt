package ru.hrh.houses.model.invitation

import net.kiss.service.model.ID
import net.kiss.service.model.page.HasPageInfo
import net.kiss.service.model.page.ItemPageInfo
import ru.hrh.houses.entity.HouseInvitationEntity
import java.time.LocalDateTime
import java.util.*

data class HouseInvitationListView(
  val id: ID,
  val userEmail: String,
  val invitedBy: UUID,
  val invitedAt: LocalDateTime,
  val invitation: String,
  val resolutionStatus: InvitationResolution,
  val resolution: String?,
  val resolvedBy: UUID?,
  val resolvedAt: LocalDateTime?,
  override val _page: ItemPageInfo
): HasPageInfo {
}

fun HouseInvitationEntity.toListView(pageInfo: ItemPageInfo) = HouseInvitationListView(
  id = id!!.toString(),
  userEmail = userEmail,
  invitedBy = invitedBy,
  invitedAt = invitedAt,
  invitation = invitation,
  resolutionStatus = resolutionStatus,
  resolution = resolution,
  resolvedAt = resolvedAt,
  resolvedBy = resolvedBy,
  _page = pageInfo
)
