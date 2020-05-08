package ru.hrh.houses.model.invitation

data class InvitationCreateInput(
  val houseId: String,
  val userEmail: String,
  val invitation: String
)
