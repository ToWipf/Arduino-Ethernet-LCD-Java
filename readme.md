
Create:
mvn archetype:generate -DarchetypeGroupId=com.airhacks -DarchetypeArtifactId=quarkee-archetype

Build:
> mvn package

Live test:
> mvn compile quarkus:dev