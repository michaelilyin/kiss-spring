package net.kiss.demo.products.graphql.query

import com.expediagroup.graphql.annotations.GraphQLID
import com.expediagroup.graphql.spring.operations.Query
import net.kiss.demo.products.model.Product
import org.springframework.stereotype.Component

@Component
class ProductQuery : Query {
  suspend fun product(@GraphQLID id: String): Product? {
    val product = Product(
      id = id,
      name = "Some Product",
      description = "Some description",
      categoryId = "1"
    )
    return product
  }
}
