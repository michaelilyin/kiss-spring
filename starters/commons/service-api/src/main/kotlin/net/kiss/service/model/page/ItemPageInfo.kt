package net.kiss.service.model.page

data class ItemPageInfo(val total: Int)

interface HasPageInfo {
  val _page: ItemPageInfo
}
