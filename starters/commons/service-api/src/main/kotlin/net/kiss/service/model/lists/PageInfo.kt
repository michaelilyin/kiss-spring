package net.kiss.service.model.lists

data class PageInfo(
  val totalItems: Int,
  val totalPages: Int,
  val page: Int,
  val size: Int
)
