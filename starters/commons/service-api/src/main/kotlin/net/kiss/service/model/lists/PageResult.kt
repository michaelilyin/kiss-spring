package net.kiss.service.model.lists

import kotlin.collections.List

data class PageResult<T>(
  val listInfo: ListInfo,
  val pageInfo: PageInfo,
  val items: List<T>
)
