import org.gradle.api.Project
import org.gradle.api.artifacts.DependencyResolveDetails

fun Project.ensureAlignedDependencies(
  useVersion: String,
  predicate: DependencyResolveDetails.() -> Boolean
) =
  configurations.all {
    resolutionStrategy {
      eachDependency {
        if (predicate(this))
          useVersion(useVersion)
      }
    }
  }

private fun CharSequence.matchesOneOf(patterns: List<Regex>) =
  patterns.any { pattern ->
    pattern.matches(this)
  }

private fun CharSequence.matchesNoneOf(patterns: List<Regex>) =
  !matchesOneOf(patterns)

private fun match(
  groupToMatch: String,
  exclusions: List<String>
): DependencyResolveDetails.() -> Boolean =
  {
    with(requested) {
      group == groupToMatch &&
          name.matchesNoneOf(exclusions.map(String::toRegex))
    }
  }

private val kotlinExclusions = listOf(
  "kotlin-(gradle-plugin-core|jdk-annotations|android-sdk-annotations|swing|jdbc|stdlib-validator|android-compiler-plugin|gradle-plugin-test|maven-plugin-test|build-common-test|stdlib-gen|js-tests-junit|js-tests|js-library|build-common)",
  "kdoc",
  "kdoc-maven-plugin",
  "kunit"
)

fun Project.ensureAlignedKotlinLibs(kotlinVersion: String) =
  ensureAlignedDependencies(
    kotlinVersion,
    match("org.jetbrains.kotlin", kotlinExclusions)
  )

fun Project.ensureAlignedAndroidSupportLibs(supportLibVersion: String) =
  ensureAlignedDependencies(
    supportLibVersion,
    match("com.android.support", listOf("multidex", "multidex-instrumentation"))
  )
