import org.gradle.api.Project
import java.io.File
import java.time.LocalDateTime

private fun String.execute(workingDir: File): String? {
  Runtime.getRuntime().exec(this, null, workingDir)
    .let {
      it.waitFor()
      return try {
        it.inputStream.reader().readText().trim()
          .takeIf(String::isNotEmpty)
      } catch (e: Exception) {
        null
      }
    }
}

private fun Project.hgLog(template: String): String? =
  """hg log -r "." --template $template""".execute(rootDir)

fun Project.hgCommitHash(): String =
  hgLog("{node|short}") ?: "none"

fun Project.hgLatestTag(): String? =
  hgLog("{latesttag('re:^\\d.*\$')}")
    ?.takeIf { it.isNotEmpty() && it != "null" }

fun Project.hgDistanceFromLatestTag() =
  hgLog("{latesttag('re:^\\d.*\$') % '{distance}'}")?.toInt()

fun Project.hgCommitCountToCurrent() =
  hgLog("{revset('ancestors(\".\")'|count}")?.toInt()

fun Project.hgCommitTimestamp(): String? =
  hgLog("{date|isodate}")

fun Project.buildDateTime() = LocalDateTime.now().toString()

fun computeVersionCode(versionName: String): Int? {
  val (maj, min, p) = """^(\d+)\.(\d+).?(\d*)$""".toRegex().find(versionName)
    ?.groupValues?.drop(1)?.take(3) ?: listOf("0", "0", "")

  return 1_000_000 * maj.toInt() + 10_000 * min.toInt() + 100 * p.toInt()
}

fun Project.computeVersionFromHg(): String =
  hgLatestTag()?.plus(
    hgDistanceFromLatestTag()?.takeIf { it > 0 }?.let { "-$it" } ?: ""
  ) ?: "dev"
