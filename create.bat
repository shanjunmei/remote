call mvn archetype:generate -DgroupId=com.ffzx.remote -DartifactId=remote-pom -DarchetypeArtifactId=maven-archetype-quickstart   -DarchetypeCatalog=internal -Dversion=1.0
cd remote-pom
call mvn archetype:generate -DgroupId=com.ffzx.remote.core -DartifactId=remote-core -DarchetypeArtifactId=maven-archetype-quickstart   -DarchetypeCatalog=internal -Dversion=1.0
call mvn archetype:generate -DgroupId=com.ffzx.remote.client -DartifactId=remote-client -DarchetypeArtifactId=maven-archetype-quickstart   -DarchetypeCatalog=internal -Dversion=1.0
call mvn archetype:generate -DgroupId=com.ffzx.remote.server -DartifactId=remote-server -DarchetypeArtifactId=maven-archetype-quickstart   -DarchetypeCatalog=internal -Dversion=1.0