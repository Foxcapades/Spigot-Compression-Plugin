import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.readText

repositories {
  when (val args = System.getenv("MAVEN_ARGS")) {
    null -> {
      when (val path = findProperty("maven-local")) {
        null -> mavenLocal()
        else -> maven { url = uri(path as String) }
      }
    }

    else -> {
      Regex("(?:-s|--settings)(?:=| *)(.+?\\.xml)")
        .find(args)
        ?.let { Path(it.groups[1]!!.value) }
        ?.takeIf { it.exists() }
        ?.readText()
        ?.let {
          val start = it.indexOf("<localRepository>") + 17
          if (start < 17)
            return@let null

          val end = it.indexOf("</localRepository>", start)
          if (end < start)
            return@let null

          it.substring(start, end).trim()
        }
        ?.let(::uri)
        ?.also { maven { url = it } }
    }
  }
}

dependencies {
  compileOnly("org.spigotmc", "spigot-api", "1.21.3-R0.1-SNAPSHOT")
  compileOnly("org.bukkit", "craftbukkit", "1.21.3-R0.1-SNAPSHOT")
}
