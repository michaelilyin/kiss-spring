package net.kiss.auth.model

import net.kiss.auth.model.AdditionalInfo

interface CurrentUser {
  val authenticated: Boolean
  val info: AdditionalInfo?
}
