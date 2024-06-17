plugins {
    id("java")
}

group = "cicd"
version = "1.0"

repositories {
    mavenCentral()
}

val springBootVersion = "3.3.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}