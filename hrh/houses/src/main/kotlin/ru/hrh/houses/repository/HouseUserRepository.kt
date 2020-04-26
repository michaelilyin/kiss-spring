package ru.hrh.houses.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import ru.hrh.houses.entity.HouseUserEntity

@Repository
interface HouseUserRepository : ReactiveCrudRepository<HouseUserEntity, Long> {
}
