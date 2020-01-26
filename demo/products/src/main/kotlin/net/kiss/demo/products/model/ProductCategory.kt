package net.kiss.demo.products.model

import com.expediagroup.graphql.annotations.GraphQLID
import com.expediagroup.graphql.annotations.GraphQLIgnore

/**
 * @author ilyin
 */
data class ProductCategory(
  @GraphQLID
  val id: String,
  val name: String,

  @GraphQLIgnore
  val parentId: Long?
) {

}
