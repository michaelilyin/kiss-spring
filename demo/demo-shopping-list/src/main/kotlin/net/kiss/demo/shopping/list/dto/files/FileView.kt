package net.kiss.demo.shopping.list.dto.files

import net.kiss.demo.shopping.list.entity.FileEntity

data class FileView(
  val id: String,
  val filename: String
) {
}

fun FileEntity.toView() = FileView(
  id = id.toString(),
  filename = filename
)

data class BinaryFileView(
  val id: String,
  val filename: String,
  val data: ByteArray
) {
}

fun FileEntity.toBinaryView() = BinaryFileView(
  id = id.toString(),
  filename = filename,
  data = data
)
