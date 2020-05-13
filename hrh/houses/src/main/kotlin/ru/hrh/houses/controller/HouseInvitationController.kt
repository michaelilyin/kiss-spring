package ru.hrh.houses.controller

import net.kiss.auth.model.CurrentUser
import net.kiss.service.model.page.Page
import net.kiss.service.model.page.PageRequest
import net.kiss.service.model.sort.SortRequest
import net.kiss.starter.service.utils.returnMono
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import ru.hrh.houses.model.invitation.*
import ru.hrh.houses.service.HouseInvitationService
import java.lang.IllegalArgumentException

@RestController
@RequestMapping("/api")
class HouseInvitationController @Autowired constructor(
  private val houseInvitationService: HouseInvitationService
) {

  @PreAuthorize("hasAnyRole(@roles.houseMember)")
  @GetMapping("/invitations")
  fun getCurrentInvitations(currentUser: CurrentUser): Mono<Page<HouseInvitationView>> = returnMono {
    houseInvitationService.getUserInvitations(
      currentUser,
      HouseInvitationsFilter(active = true)
    )
  }

  @PreAuthorize("""
    hasAnyRole(@roles.houseMember) && !hasRole(@roles.demo)
      && hasPermission(#invitationId, @invitationPermission.type, @invitationPermission.accept.invoke(#currentUser))
   """)
  @PutMapping("/invitations/{invitationId}/accept")
  fun acceptInvitation(
    @PathVariable("invitationId") invitationId: String,
    @RequestBody input: InvitationResolutionInput,
    currentUser: CurrentUser
  ): Mono<HouseInvitationView> = returnMono {
    houseInvitationService.resolveInvitation(invitationId, InvitationResolution.ACCEPTED, input, currentUser)
  }

  @PreAuthorize("""
    hasAnyRole(@roles.houseMember) && !hasRole(@roles.demo)
      && hasPermission(#invitationId, @invitationPermission.type, @invitationPermission.reject.invoke(#currentUser))
   """)
  @PutMapping("/invitations/{invitationId}/reject")
  fun rejectInvitation(
    @PathVariable("invitationId") invitationId: String,
    @RequestBody input: InvitationResolutionInput,
    currentUser: CurrentUser
  ): Mono<HouseInvitationView> = returnMono {
    houseInvitationService.resolveInvitation(invitationId, InvitationResolution.REJECTED, input, currentUser)
  }

  @PreAuthorize("""
    hasAnyRole(@roles.houseMember) && !hasRole(@roles.demo)
      && hasPermission(#invitationId, @invitationPermission.type, @invitationPermission.cancel)
   """)
  @PutMapping("/invitations/{invitationId}/cancel")
  fun cancelInvitation(
    @PathVariable("invitationId") invitationId: String,
    @RequestBody input: InvitationResolutionInput,
    currentUser: CurrentUser
  ): Mono<HouseInvitationView> = returnMono {
    houseInvitationService.resolveInvitation(invitationId, InvitationResolution.CANCELLED, input, currentUser)
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
  ): Mono<HouseInvitationView> = returnMono {
    if (houseId != input.houseId) {
      throw IllegalArgumentException();
    }
    houseInvitationService.createInvitation(input, currentUser)
  }

  @PreAuthorize("""
    hasRole(@roles.houseMember) && hasPermission(#houseId, @housePermissions.type, @housePermissions.invite)
  """)
  @GetMapping("/{houseId}/invitations")
  fun getHouseInvitations(
    @PathVariable("houseId") houseId: String,
    @RequestParam("active", defaultValue = "true") active: Boolean,
    @RequestParam("accepted", defaultValue = "false") accepted: Boolean,
    @RequestParam("rejected", defaultValue = "false") rejected: Boolean,
    @RequestParam("cancelled", defaultValue = "false") cancelled: Boolean,
    @RequestParam("offset", defaultValue = "0") offset: Int,
    @RequestParam("limit", defaultValue = "25") limit: Int,
    @RequestParam("sort", defaultValue = "id") sort: String,
    @RequestParam("desc", defaultValue = "false") desc: Boolean
  ): Mono<Page<HouseInvitationView>> = returnMono {
    houseInvitationService.getHouseInvitations(
      houseId,
      HouseInvitationsFilter(active, accepted, rejected, cancelled),
      PageRequest(offset, limit),
      SortRequest(sort, desc)
    )
  }
}
