package net.kiss.service.model.sort

import org.springframework.data.domain.Sort

data class SortRequest(
  val field: String, val desc: Boolean = false
) {
  val order = if (desc) "desc" else "asc"

  fun statement(): String {
    return "$field $order"
  }
}

fun SortRequest.toSpringSort() = Sort.by(
  if (desc) Sort.Order.desc(field) else Sort.Order.asc(field)
)
