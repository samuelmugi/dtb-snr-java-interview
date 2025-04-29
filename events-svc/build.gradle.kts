plugins {
	id("org.springframework.boot")
	id("io.spring.dependency-management")
}

dependencies {
	implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.5.0")
}
tasks.bootJar {
	archiveFileName.set("${project.name}.jar")
}