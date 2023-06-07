plugins {
    alias(libs.plugins.buildconfig)
    id("minecraft")
    id("publish")
}

repositories {
    mavenCentral()
    mavenLocal()
}

val modId: String by extra
val modName: String by extra
val modGroup: String by extra

buildConfig {
    packageName("space.impact.$modId")
    buildConfigField("String", "MODID", "\"${modId}\"")
    buildConfigField("String", "MODNAME", "\"${modName}\"")
    buildConfigField("String", "VERSION", "\"${project.version}\"")
    buildConfigField("String", "GROUPNAME", "\"${modGroup}\"")
    useKotlinOutput { topLevelConstants = true }
}

dependencies {
    compileOnly(fileTree(mapOf("dir" to "libs/", "include" to listOf("*.jar"))))
}
