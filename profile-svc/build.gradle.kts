plugins {
	id("org.springframework.boot")
	id("io.spring.dependency-management")
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
	runtimeOnly("org.postgresql:r2dbc-postgresql")
	implementation("name.nkonev.r2dbc-migrate:r2dbc-migrate-spring-boot-starter:3.2.0")
}
tasks.bootJar {
	archiveFileName.set("${project.name}.jar")
}