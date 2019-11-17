package net.kiss.demo.users.helpers.sql

import net.kiss.demo.users.helpers.entity.UserEntityHelper

object SqlValues {
  val frodo = UserEntityHelper.userEntity(
    id = 1000,
    username = "frodo"
  )

  val sam = UserEntityHelper.userEntity(
    id = 1001,
    username = "sam"
  )

  val aragorn = UserEntityHelper.userEntity(
    id = 1002,
    username = "aragorn"
  )

  val gandalf = UserEntityHelper.userEntity(
    id = 1003,
    username = "gandalf"
  )
}
