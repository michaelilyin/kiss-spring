package ru.hrh.houses.repository

import net.kiss.service.model.page.PageRequest
import net.kiss.service.model.sort.SortRequest
import org.springframework.data.domain.Sort
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import ru.hrh.houses.entity.HouseInvitationEntity
import ru.hrh.houses.model.invitation.HouseInvitationsFilter

@Repository
interface HouseInvitationRepository : ReactiveCrudRepository<HouseInvitationEntity, Long> {
  // language=PostgreSQL
  @Query("""
    SELECT house_invites.*, count(*) OVER () as _count FROM house_invites
    WHERE
      house_id = :house_id
      AND (
        (:active IS TRUE AND resolution_status = 'NEW')
        OR (:rejected IS TRUE AND resolution_status = 'REJECTED')
        OR (:accepted IS TRUE AND resolution_status = 'ACCEPTED')
        OR (:cancelled IS TRUE AND resolution_status = 'CANCELLED')
      )
    ORDER BY :order
    OFFSET :offset
    LIMIT :limit
  """)
  fun getHouseInvitations(
    @Param("house_id") houseId: Long,
    @Param("active") active: Boolean,
    @Param("rejected") rejected: Boolean,
    @Param("accepted") accepted: Boolean,
    @Param("cancelled") cancelled: Boolean,
    @Param("offset") offset: Int,
    @Param("limit") limit: Int,
    @Param("order") order: String
  ): Flux<HouseInvitationEntity>

}
