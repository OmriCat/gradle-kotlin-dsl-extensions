import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven

fun RepositoryHandler.jitpack() =
  maven("https://gradle.extensions.jitpack.io")

