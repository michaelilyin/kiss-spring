package net.kiss.starter.grqphql.loader

import com.expediagroup.graphql.spring.execution.DataLoaderRegistryFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.future.asCompletableFuture
import mu.KotlinLogging
import org.dataloader.BatchLoader
import org.dataloader.DataLoader
import org.dataloader.DataLoaderRegistry
import org.springframework.core.annotation.AnnotationUtils
import java.util.concurrent.CompletionStage

class DataLoaderAdapterRegistryFactory(
  loaderAdapter: List<LoaderAdapter<*, *>>
) : DataLoaderRegistryFactory {

  private val logger = KotlinLogging.logger { }

  private val loaders = loaderAdapter.asSequence()
    .map { loader ->
      val anno = AnnotationUtils.findAnnotation(loader::class.javaObjectType, GraphQLLoader::class.java)
        ?: throw IllegalArgumentException(loader::class.simpleName)

      @Suppress("UNCHECKED_CAST")
      val adapter = loader as LoaderAdapter<Any, Any>
      anno.loader to Loader(anno.loader, adapter)
    }
    .toMap()


  override fun generate(): DataLoaderRegistry {
    val registry = DataLoaderRegistry()

    loaders.forEach { (key, value) ->
      registry.register(key, DelegatedDataLoader(key, value))
    }

    return registry
  }

  class Loader(val key: String, private val adapter: LoaderAdapter<Any, Any>) : BatchLoader<Any, Any> {
    private val logger = KotlinLogging.logger {  }

    override fun load(keys: List<Any>): CompletionStage<List<Any>> {
      logger.info { "Call loader $key with $keys" }
      return GlobalScope.async { adapter.load(keys) }.asCompletableFuture()
    }
  }

  class DelegatedDataLoader(
    val key: String,
    loader: BatchLoader<Any, Any>
  ) : DataLoader<Any, Any>(loader) {
    override fun toString(): String {
      return "DelegatedDataLoader(key='$key')"
    }
  }

}
