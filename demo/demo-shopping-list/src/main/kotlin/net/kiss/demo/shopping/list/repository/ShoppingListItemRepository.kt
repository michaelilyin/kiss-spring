package net.kiss.demo.shopping.list.repository

import net.kiss.demo.shopping.list.entity.ShoppingListItemEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ShoppingListItemRepository : ReactiveCrudRepository<ShoppingListItemEntity, Long> {
}
