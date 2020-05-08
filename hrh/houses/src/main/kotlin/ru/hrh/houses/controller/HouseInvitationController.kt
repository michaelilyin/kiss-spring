package ru.hrh.houses.controller

import net.kiss.auth.model.CurrentUser
import net.kiss.starter.service.utils.returnMono
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import ru.hrh.houses.model.invitation.InvitationCreateInput
import ru.hrh.houses.model.invitation.InvitationResolution
import java.lang.IllegalArgumentException

@Controller
@RequestMapping("/api")
class HouseInvitationController {
  @PreAuthorize("hasAnyRole(@roles.houseMember)")
  @GetMapping("/invitations")
  fun getCurrentInvitations(currentUser: CurrentUser) = returnMono {
    "[]"
  }

  @PreAuthorize("""
    hasAnyRole(@roles.houseMember) && !hasRole(@roles.demo)
      && hasPermission(#invitationId, @invitationPermission.type, @invitationPermission.accept.invoke(#currentUser))
   """)
  @PutMapping("/invitations/{invitationId}/accept")
  fun acceptInvitation(
    @PathVariable("invitationId") invitationId: String,
    currentUser: CurrentUser
  ) = returnMono {
    "{}"
  }

  @PreAuthorize("""
    hasAnyRole(@roles.houseMember) && !hasRole(@roles.demo)
      && hasPermission(#invitationId, @invitationPermission.type, @invitationPermission.reject.invoke(#currentUser))
   """)
  @PutMapping("/invitations/{invitationId}/reject")
  fun rejectInvitation(
    @PathVariable("invitationId") invitationId: String,
    @RequestBody resolution: InvitationResolution,
    currentUser: CurrentUser
  ) = returnMono {
    "{}"
  }

  @PreAuthorize("""
    hasAnyRole(@roles.houseMember) && !hasRole(@roles.demo)
      && hasPermission(#invitationId, @invitationPermission.type, @invitationPermission.cancel)
   """)
  @PutMapping("/invitations/{invitationId}/reject")
  fun cancelInvitation(
    @PathVariable("invitationId") invitationId: String,
    @RequestBody resolution: InvitationResolution,
    currentUser: CurrentUser
  ) = returnMono {
    "{}"
  }

  @PreAuthorize("""
    hasRole(@roles.houseMember) && !hasRole(@roles.demo)
      && hasPermission(#houseId, @housePermissions.type, @housePermissions.invite)
  """)
  @PostMapping("/{houseId}/invitations")
  fun createHouseInvitation(
    @PathVariable("houseId") houseId: String,
    @RequestBody input: InvitationCreateInput,
    currentUser: CurrentUser
  ) = returnMono {
    if (houseId != input.houseId) {
      throw IllegalArgumentException();
    }
    "{}"
  }

  @PreAuthorize("""
    hasRole(@roles.houseMember) && hasPermission(#houseId, @housePermissions.type, @housePermissions.invite)
  """)
  @GetMapping("/{houseId}/invitations")
  fun getHouseInvitations(
    @PathVariable("houseId") houseId: String,
    @RequestParam("active") active: Boolean,
    @RequestParam("rejected") rejected: Boolean,
    @RequestParam("cancelled") cancelled: Boolean
  ) = returnMono {
    "[]"
  }
}
