import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.3.0.M2"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	kotlin("jvm") version "1.3.61"
	kotlin("plugin.spring") version "1.3.61"
	kotlin("plugin.jpa") version "1.3.61"
	id("com.bmuschko.docker-java-application") version "6.1.3"
}

group = "com.mif"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
	//implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	implementation("io.springfox:springfox-swagger2:2.9.2")
	implementation("io.springfox:springfox-swagger-ui:2.9.2")
	implementation("org.flywaydb:flyway-core")
	implementation("org.jdbi:jdbi3-postgres:3.11.1")
	implementation("org.jdbi:jdbi3-core:3.11.1")
	implementation("org.jdbi:jdbi3-kotlin:3.11.1")
	implementation("org.jdbi:jdbi3-kotlin-sqlobject:3.11.1")
	implementation("org.jdbi:jdbi3-kotlin:3.11.1")
	implementation("org.postgresql:postgresql:42.2.8")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.+")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

docker {
	javaApplication {
		baseImage.set("openjdk:11-slim")
		maintainer.set("Lukas Milasauskas 'lukas@milasauskas.com'")
		ports.set(listOf(8080))
		images.set(setOf("mila973/car-service:${version}"))
		jvmArgs.set(listOf("-Xms256m", "-Xmx2048m"))
	}
}