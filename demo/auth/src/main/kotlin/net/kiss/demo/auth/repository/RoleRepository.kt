package net.kiss.demo.auth.repository

import net.kiss.demo.auth.entity.RoleEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository: PagingAndSortingRepository<RoleEntity, Long> {
}
