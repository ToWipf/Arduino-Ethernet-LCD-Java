# ELCD / Tele2

Create:
> mvn archetype:generate -DarchetypeGroupId=com.airhacks -DarchetypeArtifactId=quarkee-archetype

Build:
> mvn package -Dquarkus.package.uber-jar=true

Live test:
> mvn compile quarkus:dev