package ru.hrh.houses.service.impl

import kotlinx.coroutines.reactive.awaitFirst
import net.kiss.auth.model.CurrentUser
import net.kiss.service.model.page.Page
import net.kiss.service.model.page.PageRequest
import net.kiss.service.model.sort.SortRequest
import net.kiss.service.model.sort.toSpringSort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.hrh.houses.model.invitation.*
import ru.hrh.houses.repository.HouseInvitationRepository
import ru.hrh.houses.service.HouseInvitationService

@Service
class HouseInvitationServiceImpl @Autowired constructor(
  private val houseInvitationRepository: HouseInvitationRepository
) : HouseInvitationService {
  @Transactional
  override suspend fun createInvitation(input: InvitationCreateInput, currentUser: CurrentUser): HouseInvitation {
    val entity = input.toEntity(currentUser.info.id)

    val saved = houseInvitationRepository.save(entity).awaitFirst()

    return saved.toView()
  }

  @Transactional
  override suspend fun getHouseInvitations(
    houseId: String,
    filter: HouseInvitationsFilter,
    page: PageRequest,
    sort: SortRequest
  ): Page<HouseInvitation> {
    houseInvitationRepository.getHouseInvitations(
      houseId = houseId.toLong(),
      active = filter.active,
      accepted = filter.accepted,
      rejected = filter.rejected,
      cancelled = filter.cancelled,
      limit = page.limit,
      offset = page.offset,
      order = sort.statement()
    )
    TODO("Not yet implemented")
  }
}
