package ru.hrh.houses.model.invitation.filter

import ru.hrh.houses.model.invitation.InvitationResolution

data class HouseInvitationsFilter(
  val email: String? = null,
  val active: Boolean = false,
  val accepted: Boolean = false,
  val rejected: Boolean = false,
  val cancelled: Boolean = false
)

fun HouseInvitationsFilter.statuses(): Set<InvitationResolution> {
  val res = mutableSetOf<InvitationResolution>()

  if (active) {
    res += InvitationResolution.NEW
  }
  if (accepted) {
    res += InvitationResolution.ACCEPTED
  }
  if (rejected) {
    res += InvitationResolution.REJECTED
  }
  if (cancelled) {
    res += InvitationResolution.CANCELLED
  }

  return res
}
