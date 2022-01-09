package com.christopherrons.shadoworderbook.helper;

abstract class TaskTest : DefaultTask() {
    @TaskAction
    fun greet() {
        println("hello from GreetingTask")
    }
}