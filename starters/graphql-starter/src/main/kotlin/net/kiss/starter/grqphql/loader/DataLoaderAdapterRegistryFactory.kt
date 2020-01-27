package net.kiss.starter.grqphql.loader

import com.expediagroup.graphql.spring.execution.DataLoaderRegistryFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.future.asCompletableFuture
import org.dataloader.DataLoader
import org.dataloader.DataLoaderRegistry
import org.springframework.core.annotation.AnnotationUtils

class DataLoaderAdapterRegistryFactory(
  loaderAdapter: List<LoaderAdapter<*, *>>
) : DataLoaderRegistryFactory {

  private val loaders = loaderAdapter.asSequence()
    .map { loader ->
      val anno = AnnotationUtils.findAnnotation(loader::class.javaObjectType, GraphQLLoader::class.java)
        ?: throw IllegalArgumentException(loader::class.simpleName)
      @Suppress("UNCHECKED_CAST")
      anno.loader to (loader as LoaderAdapter<Any, Any>)
    }
    .toMap()


  override fun generate(): DataLoaderRegistry {
    val registry = DataLoaderRegistry()

    loaders.forEach { (key, value) ->
      registry.register(key, DataLoader<Any, Any> { args ->
        GlobalScope.async { value.load(args) }.asCompletableFuture()
      })
    }

    return registry
  }

}
