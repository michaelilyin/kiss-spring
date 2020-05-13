package ru.hrh.houses.service.impl

import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import net.kiss.auth.model.CurrentUser
import net.kiss.service.model.page.Page
import net.kiss.service.model.page.PageRequest
import net.kiss.service.model.page.newPage
import net.kiss.service.model.sort.SortRequest
import net.kiss.starter.r2dbc.TxHelper
import net.kiss.starter.service.utils.awaitList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.hrh.houses.model.invitation.*
import ru.hrh.houses.repository.HouseInvitationRepository
import ru.hrh.houses.service.HouseInvitationService
import java.time.LocalDateTime

@Service
class HouseInvitationServiceImpl @Autowired constructor(
  private val houseInvitationRepository: HouseInvitationRepository,
  private val txHelper: TxHelper
) : HouseInvitationService {
  override suspend fun createInvitation(input: InvitationCreateInput, currentUser: CurrentUser): HouseInvitationView {
    return txHelper.wrap {
      val entity = input.toEntity(currentUser.info.id)

      val saved = houseInvitationRepository.save(entity).awaitFirst()

      // TODO: send notification to user
      // TODO: send email

      saved.toView()
    }.awaitFirst()
  }

  override suspend fun getHouseInvitations(
    houseId: String,
    filter: HouseInvitationsFilter,
    page: PageRequest,
    sort: SortRequest
  ): Page<HouseInvitationView> {
    return txHelper.wrap {
      val items = houseInvitationRepository.getHouseInvitations(
        houseId = houseId.toLong(),
        active = filter.active,
        accepted = filter.accepted,
        rejected = filter.rejected,
        cancelled = filter.cancelled,
        limit = page.limit,
        offset = page.offset,
        order = sort.statement()
      )
      val count = houseInvitationRepository.getHouseInvitationsCount(
        houseId = houseId.toLong(),
        active = filter.active,
        accepted = filter.accepted,
        rejected = filter.rejected,
        cancelled = filter.cancelled
      )

      newPage(items.awaitList(), count.awaitFirst()) { it.toView() }
    }.awaitFirst()
  }

  override suspend fun getUserInvitations(user: CurrentUser, filter: HouseInvitationsFilter): Page<HouseInvitationView> {
    return txHelper.wrap {
      val invitations = houseInvitationRepository.getHouseInvitationsByUserEmail(
        email = user.info.email,
        active = filter.active,
        accepted = filter.accepted,
        rejected = filter.rejected,
        cancelled = filter.cancelled
      )

      newPage(invitations.awaitList()) { it.toView() }
    }.awaitFirst()
  }

  override suspend fun resolveInvitation(
    id: String,
    resolution: InvitationResolution,
    input: InvitationResolutionInput,
    user: CurrentUser
  ): HouseInvitationView {
    return txHelper.wrap {
      houseInvitationRepository.updateResolution(
        id.toLong(),
        resolution.name,
        input.resolution,
        user.info.id,
        LocalDateTime.now()
      ).awaitFirstOrNull()

      val updated = houseInvitationRepository.findById(id.toLong())

      updated.awaitFirst().toView()
    }.awaitFirst()
  }
}
