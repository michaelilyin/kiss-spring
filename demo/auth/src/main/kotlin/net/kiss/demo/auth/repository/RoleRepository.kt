package net.kiss.demo.auth.repository

import net.kiss.demo.auth.entity.RoleEntity
import org.springframework.data.repository.CrudRepository

interface RoleRepository: CrudRepository<RoleEntity, Long> {
}
