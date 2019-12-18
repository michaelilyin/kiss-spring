package net.kiss.starter.service.security.user

import net.kiss.auth.model.AdditionalInfo

interface CurrentUser {
  val authenticated: Boolean
  val info: AdditionalInfo?
}
