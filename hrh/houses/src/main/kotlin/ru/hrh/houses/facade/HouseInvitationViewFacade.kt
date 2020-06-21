package ru.hrh.houses.facade

import net.kiss.auth.model.CurrentUser
import net.kiss.service.model.page.PageRequest
import net.kiss.service.model.sort.SortRequest
import reactor.core.publisher.Flux
import ru.hrh.houses.model.invitation.HouseInvitationListView
import ru.hrh.houses.model.invitation.filter.HouseInvitationsFilter

interface HouseInvitationViewFacade {
  fun getUserInvitations(
    currentUser: CurrentUser,
    filter: HouseInvitationsFilter,
    page: PageRequest,
    sort: SortRequest
  ): Flux<HouseInvitationListView>

  fun getHouseInvitations(
    houseId: String,
    filter: HouseInvitationsFilter,
    page: PageRequest,
    sort: SortRequest
  ): Flux<HouseInvitationListView>
}
