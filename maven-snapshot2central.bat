set url="https://oss.sonatype.org/content/repositories/snapshots"
set
CALL mvn clean package deploy -DaltDeploymentRepository=central_maven_snapshot::default::%url% -f bachelor-dependencies
CALL mvn clean package deploy -DaltDeploymentRepository=central_maven_snapshot::default::%url% -f bachelor-fw
CALL mvn clean package deploy -DaltDeploymentRepository=central_maven_snapshot::default::%url% -f bachelor-iam