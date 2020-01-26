package net.kiss.starter.graphql.model

import net.kiss.service.model.lists.PageRequest

interface PageRequestInput {
  val page: PageRequest
}
