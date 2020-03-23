package net.kiss.demo.cart.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId

@Document("carts")
data class CartEntity(
  @Id
  @MongoId
  val id: String?,
  @Indexed(unique = true)
  val userId: String
) {
}
