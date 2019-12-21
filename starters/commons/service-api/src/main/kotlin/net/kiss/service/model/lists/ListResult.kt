package net.kiss.service.model.lists

import kotlin.collections.List

data class ListResult<T>(
  val listInfo: ListInfo,
  val items: List<T>
)
