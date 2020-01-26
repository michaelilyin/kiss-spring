package net.kiss.demo.products.graphql

import com.expediagroup.graphql.annotations.GraphQLID
import com.expediagroup.graphql.spring.operations.Query
import net.kiss.demo.products.model.ProductCategory
import org.springframework.stereotype.Component

/**
 * @author ilyin
 */
@Component
class CategoryQuery : Query {
  suspend fun category(@GraphQLID id: String): ProductCategory? {
    return ProductCategory(
      id = id,
      name = "Some Category",
      parentId = null
    )
  }
}
