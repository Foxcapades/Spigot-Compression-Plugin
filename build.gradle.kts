allprojects {
  group = "io.foxcapades.mc"
  version = "2.0.0-SNAPSHOT"
}

val releaseDir = mkdir(project.layout.buildDirectory.dir("release"))

tasks.register("release") {
  val releaseTasks = subprojects.asSequence()
    .mapNotNull { it.tasks.findByName("release") }
    .toList()

  val inputFiles = releaseTasks.flatMap { it.outputs.files }
  val outputFiles = inputFiles.map { releaseDir.resolve(it.name) }

  dependsOn(releaseTasks)

  inputs.files(inputFiles)
  outputs.files(outputFiles)

  doLast {
    for (i in inputFiles.indices) {
      inputFiles[i].copyTo(target = outputFiles[i], overwrite = true)
    }
  }
}

tasks.register("clean") {
  val cleanTasks = subprojects.asSequence()
    .mapNotNull { it.tasks.findByName(":clean") }
    .toList()
    .toTypedArray()

  dependsOn(*cleanTasks)

  doLast { releaseDir.deleteRecursively() }
}
