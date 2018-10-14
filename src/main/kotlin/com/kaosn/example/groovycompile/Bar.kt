package com.kaosn.example.groovycompile

/**
 * @author kamil.osinski
 */

class Bar: FooInterface {
  override fun ping(message:String): String {
    println(message)
    return message
  }

  override fun returnNum(): Double {
    val a = 103 * Math.random()
    return 10 + 30 * a
  }
}