package ru.hrh.houses.permissions

import kotlinx.coroutines.reactive.awaitFirst
import net.kiss.auth.model.ObjectPermissions
import net.kiss.auth.model.longValue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.hrh.houses.repository.HouseUserRepository

@Component
class HousePermissions @Autowired() constructor(
  private val houseUserRepository: HouseUserRepository
) : ObjectPermissions {
  override val type = "house"

  val read = ObjectPermissions.Permission(this) { id, userId ->
    houseUserRepository.isMemberOf(userId, id.longValue()).awaitFirst()
  }
}
