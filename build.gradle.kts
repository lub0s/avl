import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.7.21"
  base
  `java-library`
}

group = "dev.lub0s"
version = "1.0"

repositories {
  mavenCentral()
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
}

tasks.test {
  useJUnitPlatform()
  testLogging {
    setEvents(setOf("standardOut", "started", "passed", "skipped", "failed"))
  }
}

dependencies {
  testImplementation("org.jetbrains.kotlin:kotlin-test")
}
