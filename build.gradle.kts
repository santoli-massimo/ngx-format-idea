import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("java")
    id("org.jetbrains.grammarkit") version "2022.3.2.2"
    id("org.jetbrains.intellij.platform") version "2.2.1"
    id("org.jetbrains.kotlin.jvm") version "2.1.0"
}

group = "com.msan"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }

}

dependencies {
    intellijPlatform {
        intellijIdeaUltimate("2024.3.3")
//        local("/Users/massimo/Applications/IntelliJ IDEA Ultimate.app")
        bundledPlugins(
            "com.intellij.java",
            "com.intellij.properties",
            "org.jetbrains.kotlin",
            "org.jetbrains.plugins.gradle",
            "org.intellij.intelliLang",
            "com.intellij.css",
            "JavaScript",
            "AngularJS",
            "HtmlTools",
            "JSIntentionPowerPack", "com.intellij.diagram", "tslint", "intellij.webpack",
        )
        pluginVerifier()
    }
}

sourceSets {
    main {
        java {
            srcDirs("src/main/gen")
        }
    }
}

tasks {
    generateParser {
        sourceFile.set(file("src/main/kotlin/com/msan/ngxformatidea/parser/NgxParser.bnf"))
        pathToParser.set("gen/com/msan/ngxformatidea/parser/NgxParser.java")
        pathToPsiRoot.set("psi")
        targetRootOutputDir.set(file("src/main/gen/"))
        purgeOldFiles.set(true)
    }

    generateLexer {
        sourceFile.set(file("src/main/kotlin/com/msan/ngxformatidea/lexer/NgxLexer.flex"))
        targetOutputDir.set(file("src/main/gen/com/msan/ngxformatidea/lexer/"))
        purgeOldFiles.set(true)
        dependsOn(generateParser)
    }

    compileKotlin {
        dependsOn(generateLexer)
    }

    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "21"
        targetCompatibility = "21"
        options.forkOptions.memoryMaximumSize = "8192"
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>{
        compilerOptions.jvmTarget = JvmTarget.JVM_21
    }

    patchPluginXml {
        sinceBuild.set("243")
        untilBuild.set("243.*")
        dependsOn(generateParser)
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
        dependsOn(generateParser)
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
        dependsOn(generateParser)
    }

    jar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        dependsOn(generateParser)
    }

    runIde {
//        local(file("/Users/massimo/Applications/IntelliJ IDEA Ultimate.app/Contents"))
        dependsOn(generateParser)
        jvmArgs("-Didea.log.level=DEBUG")
    }
}
