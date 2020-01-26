package net.kiss.starter.graphql.model

import net.kiss.service.model.lists.SortField

interface SortRequestInput {
  val sort: List<SortField>
}
