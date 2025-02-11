plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.intellij") version "1.17.4"
    id("org.jetbrains.grammarkit") version "2022.3.2.2"
}

group = "com.msan"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

sourceSets {
    main {
        java {
            srcDirs("src/main/gen")
        }
    }
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2024.1.7")
    type.set("IU") // Target IDE Platform

//    plugins.set(listOf(/* Plugin Dependencies */))
    plugins.set(listOf("org.intellij.intelliLang"))

}


tasks {
//    buildSearchableOptions {
//        enabled = false
//    }

    generateParser {
        sourceFile.set(file("src/main/kotlin/com/msan/ngxformatidea/grammar/NgxParser.bnf"))
        pathToParser.set("gen/com/msan/ngxformatidea/grammar/NgxParser.java")
        pathToPsiRoot.set("psi")
        targetRootOutputDir.set(file("src/main/gen/"))
        purgeOldFiles.set(false)
    }

    generateLexer {
        sourceFile.set(file("src/main/kotlin/com/msan/ngxformatidea/grammar/_NgxLexer.flex"))
        targetOutputDir.set(file("src/main/gen/com/msan/ngxformatidea/grammar/"))
        purgeOldFiles.set(false)
        dependsOn(generateParser)
    }

    compileKotlin {
        dependsOn(generateParser)
        dependsOn(generateLexer)
    }

    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        sinceBuild.set("241")
        untilBuild.set("243.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }

    jar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    runIde {
//        ideDir.set(file("/Users/massimo/Library/Application Support/JetBrains/IntelliJIdea2024.3"))
        ideDir.set(file("/Users/massimo/Applications/IntelliJ IDEA Ultimate.app/Contents"))
    }
}