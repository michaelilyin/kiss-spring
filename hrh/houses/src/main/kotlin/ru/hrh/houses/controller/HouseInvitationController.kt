package ru.hrh.houses.controller

import net.kiss.auth.model.CurrentUser
import net.kiss.service.interop.emptyAsNull
import net.kiss.service.model.page.PageRequest
import net.kiss.service.model.sort.SortRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.hrh.houses.model.invitation.*
import ru.hrh.houses.model.invitation.filter.HouseInvitationsFilter
import ru.hrh.houses.service.HouseInvitationService

@RestController
@RequestMapping("/api")
class HouseInvitationController @Autowired constructor(
  private val houseInvitationService: HouseInvitationService
) {

  @PreAuthorize("hasAnyRole(@roles.houseMember)")
  @GetMapping("/invitations", produces = [MediaType.APPLICATION_STREAM_JSON_VALUE])
  fun getCurrentInvitations(
    page: PageRequest, sort: SortRequest, currentUser: CurrentUser
  ): Flux<HouseInvitationListView> {
    val filter = HouseInvitationsFilter(active = true)
    return houseInvitationService.getUserInvitations(currentUser, filter, page, sort)
  }

  @PreAuthorize("""
    hasAnyRole(@roles.houseMember) && !hasRole(@roles.demo)
      && hasPermission(#invitationId, @invitationPermission.type, @invitationPermission.accept.invoke(#currentUser))
   """)
  @PutMapping("/invitations/{invitationId}/accept")
  fun acceptInvitation(
    @PathVariable("invitationId") invitationId: String,
    @RequestBody input: HouseInvitationResolutionInput,
    currentUser: CurrentUser
  ): Mono<HouseInvitationView> {
    return houseInvitationService.resolveInvitation(invitationId, InvitationResolution.ACCEPTED, input, currentUser)
  }

  @PreAuthorize("""
    hasAnyRole(@roles.houseMember) && !hasRole(@roles.demo)
      && hasPermission(#invitationId, @invitationPermission.type, @invitationPermission.reject.invoke(#currentUser))
   """)
  @PutMapping("/invitations/{invitationId}/reject")
  fun rejectInvitation(
    @PathVariable("invitationId") invitationId: String,
    @RequestBody input: HouseInvitationResolutionInput,
    currentUser: CurrentUser
  ): Mono<HouseInvitationView> {
    return houseInvitationService.resolveInvitation(invitationId, InvitationResolution.REJECTED, input, currentUser)
  }

  @PreAuthorize("""
    hasAnyRole(@roles.houseMember) && !hasRole(@roles.demo)
      && hasPermission(#invitationId, @invitationPermission.type, @invitationPermission.cancel)
   """)
  @PutMapping("/invitations/{invitationId}/cancel")
  fun cancelInvitation(
    @PathVariable("invitationId") invitationId: String,
    @RequestBody input: HouseInvitationResolutionInput,
    currentUser: CurrentUser
  ): Mono<HouseInvitationView> {
    return houseInvitationService.resolveInvitation(invitationId, InvitationResolution.CANCELLED, input, currentUser)
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
  ): Mono<HouseInvitationView> {
    if (houseId != input.houseId) {
      throw IllegalArgumentException()
    }
    return houseInvitationService.createInvitation(input, currentUser)
  }

  @PreAuthorize("""
    hasRole(@roles.houseMember) && hasPermission(#houseId, @housePermissions.type, @housePermissions.invite)
  """)
  @GetMapping("/{houseId}/invitations", produces = [MediaType.APPLICATION_STREAM_JSON_VALUE])
  fun getHouseInvitations(
    @PathVariable("houseId") houseId: String,
    @RequestParam("active", defaultValue = "true") active: Boolean,
    @RequestParam("accepted", defaultValue = "false") accepted: Boolean,
    @RequestParam("rejected", defaultValue = "false") rejected: Boolean,
    @RequestParam("cancelled", defaultValue = "false") cancelled: Boolean,
    @RequestParam("email") email: String?,
    page: PageRequest,
    sort: SortRequest
  ): Flux<HouseInvitationListView> {
    val filter = HouseInvitationsFilter(email.emptyAsNull(), active, accepted, rejected, cancelled)
    return houseInvitationService.getHouseInvitations(houseId, filter, page, sort)
  }
}
