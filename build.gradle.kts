plugins {
  `kotlin-dsl`
  id("nebula.resolution-rules") version "5.1.1"
  `maven-publish`
}

group = "com.omricat"
version = "0.2"

repositories {
  mavenCentral()
  jcenter()
}

dependencies {
  resolutionRules("com.netflix.nebula:gradle-resolution-rules:0.52.0")
}

val sourcesJar by tasks.creating(Jar::class) {
  dependsOn(JavaPlugin.CLASSES_TASK_NAME)
  classifier = "sources"
  from(java.sourceSets["main"].allSource)
}

val javadocJar by tasks.creating(Jar::class) {
  dependsOn(JavaPlugin.JAVADOC_TASK_NAME)
  classifier = "javadoc"
  from(java.docsDir)
}

publishing {
  (publications) {
    "mavenJava".invoke(MavenPublication::class) {
      from(components["java"])
      artifact(sourcesJar)
      artifact(javadocJar)
    }
  }
}
