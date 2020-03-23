package net.kiss.demo.cart.model.external

import com.expediagroup.graphql.annotations.GraphQLID
import com.expediagroup.graphql.federation.directives.ExtendsDirective
import com.expediagroup.graphql.federation.directives.ExternalDirective
import com.expediagroup.graphql.federation.directives.FieldSet
import com.expediagroup.graphql.federation.directives.KeyDirective

@ExtendsDirective
@KeyDirective(fields = FieldSet("id"))
data class Product(
  @GraphQLID
  @ExternalDirective
  val id: String
) {
}
