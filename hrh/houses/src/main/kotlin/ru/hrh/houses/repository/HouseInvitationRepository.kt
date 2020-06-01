package ru.hrh.houses.repository

import net.kiss.service.model.page.PageRequest
import net.kiss.service.model.sort.SortRequest
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.hrh.houses.entity.HouseInvitationEntity
import ru.hrh.houses.entity.update.ResolveInvitationEntityUpdate
import ru.hrh.houses.model.invitation.InvitationResolution
import java.time.LocalDateTime
import java.util.*

// language=PostgreSQL
const val SELECT_HOUSE_INVITATIONS = """
    SELECT *
    FROM house_invites
    WHERE
      (:house_id IS NULL OR house_id = :house_id)
      AND (:email IS NULL OR user_email ilike '%' || :email || '%')
      AND resolution_status IN (:statuses)
"""

@Repository
interface HouseInvitationRepository : ReactiveCrudRepository<HouseInvitationEntity, Long> {

  // language=PostgreSQL
  @Query("""
    SELECT h_inv.* FROM ($SELECT_HOUSE_INVITATIONS) h_inv
    ORDER BY :#{#sort.statement}
    OFFSET :#{#page.offset}
    LIMIT :#{#page.limit}
  """)
  fun getHouseInvitations(
    @Param("house_id") houseId: Long?,
    @Param("email") email: String?,
    @Param("statuses") statuses: Set<InvitationResolution>,
    @Param("page") page: PageRequest,
    @Param("sort") sort: SortRequest
  ): Flux<HouseInvitationEntity>

  // language=PostgreSQL
  @Query("""SELECT count(h_inv.*) FROM ($SELECT_HOUSE_INVITATIONS) h_inv""")
  fun getHouseInvitationsCount(
    @Param("house_id") houseId: Long?,
    @Param("email") email: String?,
    @Param("statuses") statuses: Set<InvitationResolution>
  ): Mono<Int>

  // language=PostgreSQL
  @Query("""
    UPDATE house_invites
    SET
      resolution_status = :#{#update.resolutionStatus},
      resolution = :#{#update.resolution},
      resolved_by = :#{#update.updatedBy},
      resolved_at = :#{update.updatedAt}
    WHERE
      id = :id
  """)
  @Modifying
  fun updateResolution(
    @Param("id") id: Long,
    @Param("update") update: ResolveInvitationEntityUpdate
  ): Mono<Void>

}
