package ru.hrh.houses.permissions

import kotlinx.coroutines.reactive.awaitFirst
import net.kiss.auth.model.ObjectPermissions
import net.kiss.auth.model.longValue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.hrh.houses.repository.HouseRepository
import ru.hrh.houses.repository.HouseUserRepository

@Component
class HousePermissions @Autowired() constructor(
  private val houseUserRepository: HouseUserRepository,
  private val houseRepository: HouseRepository
) : ObjectPermissions {
  override val type = "house"

  val read = ObjectPermissions.Permission(this) { id, userId ->
    houseUserRepository.isMemberOf(id.longValue(), userId).awaitFirst()
  }

  val update = ObjectPermissions.Permission(this) { id, userId ->
    houseRepository.isOwnerOf(id.longValue(), userId).awaitFirst()
  }

  val delete = ObjectPermissions.Permission(this) { id, userId ->
    houseRepository.isOwnerOf(id.longValue(), userId).awaitFirst()
  }

  val invite = ObjectPermissions.Permission(this) { id, userId ->
    houseRepository.isOwnerOf(id.longValue(), userId).awaitFirst()
  }
}
