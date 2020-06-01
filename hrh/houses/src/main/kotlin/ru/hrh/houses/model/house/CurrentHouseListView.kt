package ru.hrh.houses.model.house

import net.kiss.service.model.ID
import net.kiss.service.model.page.HasPageInfo
import net.kiss.service.model.page.ItemPageInfo
import ru.hrh.houses.entity.HouseEntity

data class CurrentHouseListView(
  val id: ID,
  val name: String,
  val description: String?,
  override val _page: ItemPageInfo
): HasPageInfo

fun HouseEntity.toCurrentListView(pageInfo: ItemPageInfo) = CurrentHouseListView(
  id = id.toString(),
  name = name,
  description = description,
  _page = pageInfo
)
