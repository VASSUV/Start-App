package ru.vassuv.startapp

class D (val name: String = "Tom", val age: Int = 15) {
    operator fun component1() = name
    operator fun component2() = age
}

fun main () {

    val (name, age) = D()

}