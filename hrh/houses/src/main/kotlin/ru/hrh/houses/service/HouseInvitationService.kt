package ru.hrh.houses.service

import net.kiss.auth.model.CurrentUser
import net.kiss.service.model.page.Page
import net.kiss.service.model.page.PageRequest
import net.kiss.service.model.sort.SortRequest
import ru.hrh.houses.model.invitation.HouseInvitation
import ru.hrh.houses.model.invitation.HouseInvitationsFilter
import ru.hrh.houses.model.invitation.InvitationCreateInput

interface HouseInvitationService {
  suspend fun createInvitation(input: InvitationCreateInput, currentUser: CurrentUser): HouseInvitation
  suspend fun getHouseInvitations(
    houseId: String,
    filter: HouseInvitationsFilter,
    page: PageRequest,
    sort: SortRequest
  ): Page<HouseInvitation>
}
