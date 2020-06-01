package net.kiss.service.model.sort

import org.springframework.data.domain.Sort

data class SortRequest(
  val field: String, val desc: Boolean = false
) {
  val order: String
    get() = if (desc) "desc" else "asc"

  val statement: String
    get() = "${this.field} $order"
}

fun SortRequest.toSpringSort() = Sort.by(
  if (desc) Sort.Order.desc(field) else Sort.Order.asc(field)
)
