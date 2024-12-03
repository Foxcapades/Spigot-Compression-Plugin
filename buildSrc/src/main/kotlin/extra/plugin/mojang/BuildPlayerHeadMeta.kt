package extra.plugin.mojang

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

open class BuildPlayerHeadMeta : DefaultTask() {
  @Internal
  @set:Option(option = "username", description = "Username of the player whose texture to use for the generated item meta.")
  var username = ""

  @TaskAction
  fun execute() {
    if (username.isBlank())
      throw GradleException("must provide a username via --username")

    val message = Mojang.lookupTextureData(Mojang.lookupUserID(username))
      ?.let(Mojang::buildItemMeta)
      ?: "No texture data found!"

    println()
    println()
    println(message)
    println()
  }
}
