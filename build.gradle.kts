plugins {
    java
}

val mavenLocalPath = ".repo/mvn-repo"

repositories {
    maven("$projectDir/$mavenLocalPath")
    // mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.11.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.27.3")
}

tasks.withType<Test> {
    useJUnitPlatform()
    options {
        systemProperty("file.encoding", "UTF-8")
    }
}

tasks.compileJava {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc> {
    options.encoding("UTF-8")
}