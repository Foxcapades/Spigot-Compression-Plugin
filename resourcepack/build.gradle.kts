import org.gradle.kotlin.dsl.support.zipTo

val PackFileName = "pack.mcmeta"

val releaseDir = mkdir(project.layout.buildDirectory.dir("release"))
val stagingDir = mkdir(project.layout.buildDirectory.dir("staging"))

val contentPath = projectDir.resolve("content")

val copyIcon = tasks.register("copy-icon") {
  val inputFile = rootDir.resolve("docs/icon.png")
  val outputFile = stagingDir.resolve("pack.png")

  inputs.files(inputFile)
  outputs.files(outputFile)

  doLast { inputFile.copyTo(target = outputFile, overwrite = true) }
}

val copyAssets = tasks.register("copy-assets") {
  val inputDir = contentPath.resolve("assets")
  val outputDir = stagingDir.resolve("assets")

  inputs.dir(inputDir)
  outputs.dir(outputDir)

  doLast { inputDir.copyRecursively(target = outputDir, overwrite = true) }
}

val copyMeta = tasks.register("copy-meta") {
  val inputFile = contentPath.resolve("pack.mcmeta")
  val outputFile = stagingDir.resolve("pack.mcmeta")

  inputs.files(inputFile)
  outputs.files(outputFile)

  doLast { inputFile.copyTo(target = outputFile, overwrite = true) }
}

tasks.register("release") {
  val outputFile = releaseDir.resolve("BlockCompression-${project.version}-Resources.zip")

  inputs.dir(stagingDir)
  outputs.files(outputFile)

  dependsOn(copyIcon, copyAssets, copyMeta)

  doLast { zipTo(outputFile, stagingDir) }
}

tasks.register("clean") {
  doLast {
    stagingDir.deleteRecursively()
    releaseDir.deleteRecursively()
  }
}
