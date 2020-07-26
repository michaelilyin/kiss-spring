package net.kiss.demo.shopping.list.controller

import net.kiss.demo.shopping.list.dto.files.BinaryFileView
import net.kiss.demo.shopping.list.dto.files.FileView
import net.kiss.demo.shopping.list.service.FilesService
import org.reactivestreams.Publisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.buffer.DefaultDataBuffer
import org.springframework.core.io.buffer.DefaultDataBufferFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ZeroCopyHttpOutputMessage
import org.springframework.http.codec.multipart.FilePart
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.SynchronousSink
import reactor.kotlin.core.publisher.toFlux
import java.awt.image.DataBuffer
import java.nio.file.Files
import java.util.*

fun ServerHttpResponse.writeByteArrays(bytes: Flux<ByteArray>): Mono<Void> {
  val factory = this.bufferFactory()
  val dataBuffers = bytes.map { factory.wrap(it) }
  return this.writeWith(dataBuffers)
}

@RestController
@RequestMapping("/api/files")
class FilesController @Autowired() constructor(
  private val filesService: FilesService
) {
  @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
  fun upload(@RequestPart("file") file: FilePart): Mono<FileView> {
    val id = UUID.randomUUID().toString()
    val temp = Files.createTempFile(id, "")
    file.transferTo(temp).block()
    return  filesService.uploadFile(id, file.filename(), Files.newInputStream(temp))
  }

  @GetMapping("/{id}")
  fun download(@PathVariable("id") id: String, response: ServerHttpResponse): Mono<Void> {
    return filesService.getFileMeta(id)
      .flatMap { view: FileView ->
        response.headers[HttpHeaders.CONTENT_DISPOSITION] = "attachment; filename=${view.filename}"
        return@flatMap response.writeByteArrays(filesService.getFileBytes(id).toFlux())
      }
  }
}
