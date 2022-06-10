package tasks


import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.bundling.Compression
import org.gradle.api.tasks.bundling.*


open class FirstTask : DefaultTask() {

    @TaskAction
    fun execute() {
        print("Place holder")
    }

}