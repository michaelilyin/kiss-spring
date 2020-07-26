package net.kiss.demo.shopping.list.service

import net.kiss.demo.shopping.list.dto.files.BinaryFileView
import net.kiss.demo.shopping.list.dto.files.FileView
import reactor.core.publisher.Mono
import java.io.InputStream
import java.io.OutputStream

interface FilesService {
  fun uploadFile(id: String, filename: String, inputStream: InputStream): Mono<FileView>
  fun getFileMeta(id: String): Mono<FileView>
  fun getFileBytes(id: String): Mono<ByteArray>
}
