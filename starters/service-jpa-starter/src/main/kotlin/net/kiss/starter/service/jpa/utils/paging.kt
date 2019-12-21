package net.kiss.starter.service.jpa.utils

import net.kiss.service.model.lists.*
import org.springframework.data.domain.Page as SPage
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.PageRequest as SPageRequest
import org.springframework.data.domain.Sort as SSort

fun List<SortField>.toSort(): SSort = SSort.by(
  this.map {
    SSort.Order(SSort.Direction.fromString(it.direction), it.field)
  }
)

fun PageRequest.toPageable(sort: List<SortField>? = null): Pageable = SPageRequest.of(
  page,
  size,
  sort?.toSort() ?: SSort.unsorted()
)

fun SSort.toSortInfo(): List<SortField> = map {
  SortField(
    field = it.property,
    direction = it.direction.name
  )
}.toList()

fun <T, R> SPage<T>.toPage(mapper: (T) -> R): PageResult<R> = PageResult(
  listInfo = ListInfo(
    sort = sort.toSortInfo()
  ),
  pageInfo = PageInfo(
    page = number,
    size = size,
    totalPages = totalPages
  ),
  items = content.map(mapper)
)


fun <T> List<T>.toListResult(sort: SSort): ListResult<T> = ListResult(
  listInfo = ListInfo(
    sort = sort.toSortInfo()
  ),
  items = this
)

