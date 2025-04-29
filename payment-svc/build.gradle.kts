plugins {
	id("org.springframework.boot")
	id("io.spring.dependency-management")
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
	runtimeOnly("org.postgresql:r2dbc-postgresql")
	implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.5.0")
}
tasks.bootJar {
	archiveFileName.set("${project.name}.jar")
}