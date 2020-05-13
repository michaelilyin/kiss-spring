package ru.hrh.houses.model.invitation

data class HouseInvitationsFilter(
  val active: Boolean = false,
  val accepted: Boolean = false,
  val rejected: Boolean = false,
  val cancelled: Boolean = false
)
