package tasks


import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.bundling.Compression
import org.gradle.api.tasks.bundling.*
import org.gradle.kotlin.dsl.delegateClosureOf
import org.gradle.kotlin.dsl.withGroovyBuilder

open class FirstTask : DefaultTask() {


    @TaskAction
    fun execute() {

    }

}