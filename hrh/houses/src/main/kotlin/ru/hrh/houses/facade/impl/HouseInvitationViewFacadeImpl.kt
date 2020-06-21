package ru.hrh.houses.facade.impl

import net.kiss.auth.model.CurrentUser
import net.kiss.service.model.page.PageRequest
import net.kiss.service.model.sort.SortRequest
import net.kiss.starter.service.facade.Facade
import org.springframework.beans.factory.annotation.Autowired
import reactor.core.publisher.Flux
import ru.hrh.houses.facade.HouseInvitationViewFacade
import ru.hrh.houses.model.invitation.HouseInvitationListView
import ru.hrh.houses.model.invitation.filter.HouseInvitationsFilter
import ru.hrh.houses.service.HouseInvitationService

@Facade
class HouseInvitationViewFacadeImpl @Autowired constructor(
  private val houseInvitationService: HouseInvitationService
) : HouseInvitationViewFacade {
  override fun getUserInvitations(
    currentUser: CurrentUser,
    filter: HouseInvitationsFilter,
    page: PageRequest,
    sort: SortRequest
  ): Flux<HouseInvitationListView> {
    houseInvitationService.getUserInvitations()
  }

  override fun getHouseInvitations(
    houseId: String,
    filter: HouseInvitationsFilter,
    page: PageRequest,
    sort: SortRequest
  ): Flux<HouseInvitationListView> {
    TODO("Not yet implemented")
  }
}
