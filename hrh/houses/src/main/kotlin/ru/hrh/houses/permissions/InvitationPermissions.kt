package ru.hrh.houses.permissions

import net.kiss.auth.model.CurrentUser
import net.kiss.auth.model.ObjectPermissions
import org.springframework.stereotype.Component

@Component
class InvitationPermission : ObjectPermissions {
  override val type = "invitation"

  val accept = { user: CurrentUser ->
    ObjectPermissions.Permission(this) { id, userId ->
      false
    }
  }

  val reject = { user: CurrentUser ->
    ObjectPermissions.Permission(this) { id, userId ->
      false
    }
  }

  val cancel = ObjectPermissions.Permission(this) { id, userId ->
    false
  }
}
