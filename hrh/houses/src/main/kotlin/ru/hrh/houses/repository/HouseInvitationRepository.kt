package ru.hrh.houses.repository

import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.hrh.houses.entity.HouseInvitationEntity
import ru.hrh.houses.model.invitation.InvitationResolution
import java.time.LocalDateTime
import java.util.*

const val RESOLUTION_FILTER = """
    (
      (:active IS TRUE AND resolution_status = 'NEW')
      OR (:rejected IS TRUE AND resolution_status = 'REJECTED')
      OR (:accepted IS TRUE AND resolution_status = 'ACCEPTED')
      OR (:cancelled IS TRUE AND resolution_status = 'CANCELLED')
    )
  """

// language=PostgreSQL
const val SELECT_HOUSE_INVITATIONS = """
    SELECT *
    FROM house_invites
    WHERE
      house_id = :house_id
      AND $RESOLUTION_FILTER
"""

@Repository
interface HouseInvitationRepository : ReactiveCrudRepository<HouseInvitationEntity, Long> {

  // language=PostgreSQL
  @Query("""
    SELECT h_inv.* FROM ($SELECT_HOUSE_INVITATIONS) h_inv
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

  // language=PostgreSQL
  @Query("""
    SELECT count(h_inv.*) FROM ($SELECT_HOUSE_INVITATIONS) h_inv
  """)
  fun getHouseInvitationsCount(
    @Param("house_id") houseId: Long,
    @Param("active") active: Boolean,
    @Param("rejected") rejected: Boolean,
    @Param("accepted") accepted: Boolean,
    @Param("cancelled") cancelled: Boolean
  ): Mono<Int>

  // language=PostgreSQL
  @Query("""
    SELECT house_invites.*
    FROM house_invites
    WHERE user_email = :email
    AND $RESOLUTION_FILTER
  """)
  fun getHouseInvitationsByUserEmail(
    @Param("email") email: String,
    @Param("active") active: Boolean,
    @Param("rejected") rejected: Boolean,
    @Param("accepted") accepted: Boolean,
    @Param("cancelled") cancelled: Boolean
  ): Flux<HouseInvitationEntity>


  // language=PostgreSQL
  @Query("""
    UPDATE house_invites
    SET
      resolution_status = :resolution_status,
      resolution = :resolution,
      resolved_by = :updated_by,
      resolved_at = :updated_at
    WHERE
      id = :id
  """)
  @Modifying
  fun updateResolution(
    @Param("id") id: Long,
    // TODO: replace with enum value when issue in SpringData will be resolved
    @Param("resolution_status") resolutionStatus: String,
    @Param("resolution") resolution: String,
    @Param("updated_by") updatedBy: UUID,
    @Param("updated_at") updatedAt: LocalDateTime
  ): Mono<Void>

}
