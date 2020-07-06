package net.kiss.demo.shopping.list.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("goods")
data class GoodEntity(
  @Id
  val id: Long?,
  var name: String,
  var description: String?
) {
}
