package ru.hrh.houses.entity

import org.springframework.data.annotation.Id
import org.springframework.data.domain.Page
import org.springframework.data.relational.core.mapping.Table
import ru.hrh.houses.model.invitation.InvitationResolution
import java.time.LocalDateTime
import java.util.*

@Table("house_invites")
data class HouseInvitationEntity(
  @Id
  val id: Long?,
  val userEmail: String,
  val invitedBy: UUID,
  val invitedAt: LocalDateTime,
  val invitation: String,
  var resolutionStatus: InvitationResolution,
  var resolution: String?,
  var resolvedBy: UUID?,
  var resolvedAt: LocalDateTime?
): Pageable {
  override var _count: Int? = null
}
