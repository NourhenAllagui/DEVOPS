pipeline {
    agent any
stages {
stage("Cloning Project from Git") {
steps { 
git branch: 'Nourhen', credentialsId: 'GitCredentials', url: 'https://github.com/NourhenAllagui/DEVOPS.git'
}
}
stage("Build") {
steps {
dir("TimesheetProject"){
bat "mvn compile"}
}}
stage("Unit tests") {
steps {
dir("TimesheetProject"){
bat "mvn test"}
}}

stage("Code Quality") {
steps {
dir("TimesheetProject"){
bat "mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install"
bat "mvn sonar:sonar"}
}}

stage("Clean- Package"){
steps {
dir("TimesheetProject"){
bat "mvn clean package"}
}}

stage("Deploy with Nexus") {
steps {
dir("TimesheetProject"){
bat "mvn deploy:deploy-file -DgroupId=tn.esprit.spring -DartifactId=Timesheet-spring-boot-core-data-jpa-mvc-REST-1 -Dversion=2.0 -DgeneratePom=true -Dpackaging=war -DrepositoryId=deploymentRepo -Durl=http://localhost:8081/repository/maven-releases/ -Dfile=target/Timesheet-spring-boot-core-data-jpa-mvc-REST-1-2.0.war -DskipTests"
}}
}
}
post {
failure {
emailext attachLog: true, body: '''There was an error that prevented a Build Success ! 
Do check the attached log or the console output for further details. 

Jenkins Team ''', to: '$DEFAULT_RECIPIENTS' , subject: 'Build Failure on Pipeline'
    }
}
}
