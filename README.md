# Gradle Kotlin DSL Extensions

Whilst the [Gradle Kotlin DSL][1] is still under development,
there are a few slightly rough edges. This is (not quite) a Gradle plugin
to help soften those rough edges. It contains Kotlin extension functions
and properties but no actual plugin.

----
### Usage

As there is no plugin, it's enough to add this to your `buildscript` dependencies:

```gradle
buildscript {
    repositories {
        maven("https://jitpack.io")
    }

    dependencies {
        classpath("com.github.omricat:gradle-kotlin-dsl-extensions:latest.version")
    }
}
```

[1]: https://github.com/gradle/kotlin-dsl/
