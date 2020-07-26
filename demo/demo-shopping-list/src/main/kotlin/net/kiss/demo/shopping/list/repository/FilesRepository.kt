package net.kiss.demo.shopping.list.repository

import net.kiss.demo.shopping.list.entity.FileEntity
import net.kiss.demo.shopping.list.entity.GoodEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface FilesRepository: ReactiveCrudRepository<FileEntity, UUID> {
}
