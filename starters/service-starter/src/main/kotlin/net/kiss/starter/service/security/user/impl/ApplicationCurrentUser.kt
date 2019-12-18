package net.kiss.starter.service.security.user.impl

import net.kiss.auth.model.AdditionalInfo
import net.kiss.starter.service.security.user.CurrentUser

class ApplicationCurrentUser : CurrentUser {
  override val authenticated = false
  override val info: AdditionalInfo? = null

}
