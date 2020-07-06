package net.kiss.demo.shopping.list.repository

import net.kiss.demo.shopping.list.entity.ShoppingListEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ShoppingListRepository : ReactiveCrudRepository<ShoppingListEntity, Long> {
}
