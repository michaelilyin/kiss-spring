package net.kiss.starter.grqphql.loader

interface LoaderAdapter<A, R> {
  suspend fun load(args: List<A>): List<R>
}
