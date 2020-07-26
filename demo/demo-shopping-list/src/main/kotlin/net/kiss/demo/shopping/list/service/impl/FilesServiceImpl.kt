package net.kiss.demo.shopping.list.service.impl

import net.kiss.demo.shopping.list.dto.files.BinaryFileView
import net.kiss.demo.shopping.list.dto.files.FileView
import net.kiss.demo.shopping.list.dto.files.toBinaryView
import net.kiss.demo.shopping.list.dto.files.toView
import net.kiss.demo.shopping.list.entity.FileEntity
import net.kiss.demo.shopping.list.repository.FilesRepository
import net.kiss.demo.shopping.list.service.FilesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.io.InputStream
import java.io.OutputStream
import java.util.*

@Service
class FilesServiceImpl @Autowired() constructor(
  private val filesRepository: FilesRepository
) : FilesService {
  override fun uploadFile(id: String, filename: String, inputStream: InputStream): Mono<FileView> {
    val bytes = inputStream.readAllBytes()
    val entity = FileEntity(
      key = UUID.fromString(id),
      filename = filename,
      data = bytes
    )
    return filesRepository.save(entity)
      .map { it.toView() }
  }

  override fun getFileMeta(id: String): Mono<FileView> {
    return filesRepository.findById(UUID.fromString(id))
      .map { it.toView() }
  }

  override fun getFileBytes(id: String): Mono<ByteArray> {
    return filesRepository.findById(UUID.fromString(id))
      .map { it.data }
  }
}
