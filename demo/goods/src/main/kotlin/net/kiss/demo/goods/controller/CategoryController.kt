package net.kiss.demo.goods.controller

import net.kiss.demo.goods.model.Category
import net.kiss.service.model.lists.ListValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/categories")
class CategoryController {
  @GetMapping("top")
  suspend fun getTopCategories(): ListValue<Category> {
    return ListValue(listOf())
  }

  @GetMapping("{id}")
  suspend fun getCategory(@PathVariable("id") id: String): Category {
    throw TODO()
  }

  suspend fun getSubCategories(): ListValue<Category> {
    return ListValue(listOf())
  }
}
