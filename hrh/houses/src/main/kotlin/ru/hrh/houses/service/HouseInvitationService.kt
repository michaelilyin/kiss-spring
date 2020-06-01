package ru.hrh.houses.service

import net.kiss.auth.model.CurrentUser
import net.kiss.service.model.page.PageRequest
import net.kiss.service.model.sort.SortRequest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.hrh.houses.model.invitation.*
import ru.hrh.houses.model.invitation.filter.HouseInvitationsFilter

interface HouseInvitationService {
  fun createInvitation(input: InvitationCreateInput, currentUser: CurrentUser): Mono<HouseInvitationView>

  fun getHouseInvitations(
    houseId: String,
    filter: HouseInvitationsFilter,
    page: PageRequest,
    sort: SortRequest
  ): Flux<HouseInvitationListView>

  fun getUserInvitations(
    user: CurrentUser,
    filter: HouseInvitationsFilter,
    page: PageRequest,
    sort: SortRequest
  ): Flux<HouseInvitationListView>

  fun resolveInvitation(
    id: String,
    resolution: InvitationResolution,
    input: HouseInvitationResolutionInput,
    user: CurrentUser
  ): Mono<HouseInvitationView>
}
