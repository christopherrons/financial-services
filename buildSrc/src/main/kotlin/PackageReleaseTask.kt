import org.gradle.api.tasks.TaskAction
import org.gradle.api.DefaultTask

class PackageReleaseTask : DefaultTask() {


    @TaskAction
    fun greet() {
        println("hello from GreetingTask")
    }
}