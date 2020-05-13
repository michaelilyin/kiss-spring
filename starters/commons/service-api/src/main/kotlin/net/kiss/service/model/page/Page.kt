package net.kiss.service.model.page

data class Page<T>(
  val items: List<T>,
  val total: Int
) {
}

fun <T, D> newPage(
  items: Collection<T>,
  transform: (T) -> D
): Page<D> {
  return Page(
    items = items.map { transform(it) },
    total = items.size
  )
}

fun <T> newPage(items: Collection<T>): Page<T> {
  return newPage(items) { it }
}

fun <T, D> newPage(
  items: Collection<T>,
  size: Int,
  transform: (T) -> D
): Page<D> {
  return Page(
    items = items.map { transform(it) },
    total = size
  )
}
