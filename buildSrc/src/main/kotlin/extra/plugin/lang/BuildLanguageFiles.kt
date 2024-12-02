package extra.plugin.lang

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.io.File

open class BuildLanguageFiles : DefaultTask() {
  @Internal
  @set:Option(option = "language-file", description = "Language json file")
  var languageFile = ""

  @Internal
  @set:Option(option = "language-code", description = "Language code (e.g. en_us)")
  var languageCode = ""

  @TaskAction
  fun execute() {
    if (languageFile.isEmpty())
      throw GradleException("missing required option --language-file")

    val file = project.projectDir.resolve(languageFile)

    if (!file.exists())
      throw GradleException("file $file does not exist")

    val (tmpBlockFile, tmpItemFile) = processLangFile(file)
    val resourceDir = project.mkdir(project.projectDir.resolve("src/main/resources/locales/$languageCode"))

    tmpBlockFile.renameTo(resourceDir.resolve("blocks.properties"))
    tmpItemFile.renameTo(resourceDir.resolve("items.properties"))
  }

  private fun processLangFile(json: File): Files {
    val files = initTempFiles()
    val (blockStream, itemStream) = initWriters(files.blocks, files.items)

    try {
      json.bufferedReader()
        .lineSequence()
        .mapNotNull(::splitIfPossible)
        .forEach { (k, v) -> when {
          blockStream.test(k, v) -> blockStream.write(k, v)
          itemStream.test(k, v)  -> itemStream.write(k, v)
        } }
    } finally {
      blockStream.close()
      itemStream.close()
    }

    return files
  }

  private fun initTempFiles(): Files {
    return Files(project.file("tmp-blocks-$languageCode"), project.file("tmp-items-$languageCode"))
  }

  private fun initWriters(blockFile: File, itemFile: File): Pair<PropertyWriter, PropertyWriter> =
    BlockWriter(blockFile.bufferedWriter()) to ItemWriter(itemFile.bufferedWriter())

  private fun splitIfPossible(line: String): Pair<String, String>? {
    val q = line.indexOf('"')
    if (q == -1)
      return null

    val q2 = line.indexOf('"', q+1)

    return line.substring(q+1, q2) to line.substring(line.indexOf('"', q2+1)+1, line.lastIndexOf('"'))
  }
}

private data class Files(val blocks: File, val items: File)
