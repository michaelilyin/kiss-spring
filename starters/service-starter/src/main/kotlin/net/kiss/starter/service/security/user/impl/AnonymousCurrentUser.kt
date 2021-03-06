package net.kiss.starter.service.security.user.impl

import net.kiss.auth.model.AdditionalInfo
import net.kiss.auth.model.CurrentUser
import java.lang.IllegalStateException

class AnonymousCurrentUser : CurrentUser {
  override val authenticated = false
  override val info: AdditionalInfo
    get() {
      throw IllegalStateException("current user")
    }

}
