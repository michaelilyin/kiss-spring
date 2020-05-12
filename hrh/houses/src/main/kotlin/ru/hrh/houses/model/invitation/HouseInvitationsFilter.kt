package ru.hrh.houses.model.invitation

data class HouseInvitationsFilter(
  val active: Boolean,
  val accepted: Boolean,
  val rejected: Boolean,
  val cancelled: Boolean
)
