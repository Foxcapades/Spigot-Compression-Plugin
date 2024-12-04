package extra.plugin.mojang

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

open class GetPlayerUUID : DefaultTask() {
  @Internal
  @set:Option(option = "username", description = "Username of the player whose UUID should be retrieved.")
  var username = ""

  @TaskAction
  fun execute() {
    if (username.isBlank())
      throw GradleException("must provide a username via --username")

    println()
    println()
    println("UUID Value: " + Mojang.lookupUserID(username))
    println()
  }
}
