package ru.hrh.houses.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import org.springframework.stereotype.Repository
import ru.hrh.houses.entity.HouseEntity

@Repository
interface HouseRepository: ReactiveCrudRepository<HouseEntity, Long> {

}
