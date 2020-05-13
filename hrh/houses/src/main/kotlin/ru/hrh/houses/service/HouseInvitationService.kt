package ru.hrh.houses.service

import net.kiss.auth.model.CurrentUser
import net.kiss.service.model.page.Page
import net.kiss.service.model.page.PageRequest
import net.kiss.service.model.sort.SortRequest
import ru.hrh.houses.model.invitation.*

interface HouseInvitationService {
  suspend fun createInvitation(input: InvitationCreateInput, currentUser: CurrentUser): HouseInvitationView

  suspend fun getHouseInvitations(
    houseId: String,
    filter: HouseInvitationsFilter,
    page: PageRequest,
    sort: SortRequest
  ): Page<HouseInvitationView>

  suspend fun getUserInvitations(user: CurrentUser, filter: HouseInvitationsFilter): Page<HouseInvitationView>

  suspend fun resolveInvitation(
    id: String,
    resolution: InvitationResolution,
    input: InvitationResolutionInput,
    user: CurrentUser
  ): HouseInvitationView
}
