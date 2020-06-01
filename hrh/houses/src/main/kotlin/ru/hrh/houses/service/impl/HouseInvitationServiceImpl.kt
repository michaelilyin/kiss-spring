package ru.hrh.houses.service.impl

import net.kiss.auth.model.CurrentUser
import net.kiss.service.interop.bifunction
import net.kiss.service.model.page.ItemPageInfo
import net.kiss.service.model.page.PageRequest
import net.kiss.service.model.sort.SortRequest
import net.kiss.starter.r2dbc.TxHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.hrh.houses.model.invitation.*
import ru.hrh.houses.model.invitation.filter.HouseInvitationsFilter
import ru.hrh.houses.model.invitation.filter.statuses
import ru.hrh.houses.repository.HouseInvitationRepository
import ru.hrh.houses.service.HouseInvitationService
import java.time.LocalDateTime

@Service
class HouseInvitationServiceImpl @Autowired constructor(
  private val houseInvitationRepository: HouseInvitationRepository,
  private val txHelper: TxHelper
) : HouseInvitationService {
  @Transactional
  override fun createInvitation(input: InvitationCreateInput, currentUser: CurrentUser): Mono<HouseInvitationView> {
    val entity = input.toEntity(currentUser.info.id)

    return houseInvitationRepository.save(entity)
      // TODO: send notification to user
      // TODO: send email
      .map { saved -> saved.toView() }
  }

  @Transactional
  override fun getHouseInvitations(
    houseId: String,
    filter: HouseInvitationsFilter,
    page: PageRequest,
    sort: SortRequest
  ): Flux<HouseInvitationListView> {
    val countMono = houseInvitationRepository.getHouseInvitationsCount(
      houseId = houseId.toLong(),
      email = filter.email,
      statuses = filter.statuses()
    ).map { ItemPageInfo(it) }

    return houseInvitationRepository
      .getHouseInvitations(
        houseId = houseId.toLong(),
        email = filter.email,
        statuses = filter.statuses(),
        page = page,
        sort = sort
      ).withLatestFrom(countMono, bifunction { invitation, count ->
        invitation.toListView(count)
      })
  }

  override fun getUserInvitations(
    user: CurrentUser,
    filter: HouseInvitationsFilter,
    page: PageRequest,
    sort: SortRequest
  ): Flux<HouseInvitationListView> {
    val countMono = houseInvitationRepository.getHouseInvitationsCount(
      houseId = null,
      email = user.info.email,
      statuses = filter.statuses()
    ).map { ItemPageInfo(it) }

    return houseInvitationRepository
      .getHouseInvitations(
        houseId = null,
        email = user.info.email,
        statuses = filter.statuses(),
        page = page,
        sort = sort
      ).withLatestFrom(countMono, bifunction { invitation, count ->
        invitation.toListView(count)
      })
  }

  @Transactional
  override fun resolveInvitation(
    id: String,
    resolution: InvitationResolution,
    input: HouseInvitationResolutionInput,
    user: CurrentUser
  ): Mono<HouseInvitationView> {
    val update = input.toResolveUpdate(
      resolution,
      user.info.id
    )
    return houseInvitationRepository.updateResolution(id.toLong(), update)
      .then(houseInvitationRepository.findById(id.toLong()))
      .map { updated -> updated.toView() }
  }
}
