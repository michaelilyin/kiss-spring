package net.kiss.demo.products.graphql.loader

import net.kiss.demo.products.model.ProductCategory
import net.kiss.starter.grqphql.loader.GraphQLLoader
import net.kiss.starter.grqphql.loader.LoaderAdapter

@GraphQLLoader(Loader.CATEGORY_BY_ID)
class CategoryByIdLoader : LoaderAdapter<String, ProductCategory> {
  override suspend fun load(args: List<String>): List<ProductCategory> {
    return args.map { id ->
      ProductCategory(
        id = id,
        name = "$id name",
        parentId = null
      )
    }
  }
}
