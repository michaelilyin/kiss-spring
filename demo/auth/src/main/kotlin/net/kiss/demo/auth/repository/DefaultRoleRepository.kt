package net.kiss.demo.auth.repository

import net.kiss.demo.auth.entity.DefaultRoleEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface DefaultRoleRepository : CrudRepository<DefaultRoleEntity, Long> {
}
