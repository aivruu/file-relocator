# file-relocator

[![](https://jitpack.io/v/aivruu/file-relocator.svg)](https://jitpack.io/#aivruu/file-relocator)

`file-relocator` is a simple utility that allow download files since URL's and relocate these files into any specified directory.

> [!NOTE]\
> This library is very basic, and doesn't provide many specific options for the download and relocation
> of the files, only meets their basic functions.

### Documentation
- [Javadoc](https://jitpack.io/com/github/aivruu/file-relocator/latest/javadoc/)

### Features
* Allows download files since URL's.
* Allows relocate any file to a directory.

### Download
```kotlin
repositories {
  maven("https://jitpack.io/")
}

dependencies {
  // You can visualize the latest version on the document header.
  implementation("com.github.aivruu:file-relocator:VERSION")
}

tasks {
  shadowJar {
    // Relocate library package into own project packages.
    relocate("me.qeklydev.relocator", "com.yourPackage.com")
  }
}
```

### Requirements
- Java 21 or newer.

### Building
This library use Gradle-Kotlin for project.
```
git clone https://github.com/aivruu/file-relocator.git
cd file-relocator
./gradlew build
```

JDK 21 or newer is fully required.
