pipeline {
    agent any

    stages {
        stage ('Compile and Package Stage') {

            steps {
                withMaven(maven : 'Maven6') {
                    sh "mvn clean install "
                }
            }
        }
        
        stage ('Deploy Stage') {
            steps {
                     sh 'BUILD_ID=dontKillMe JENKINS_NODE_COOKIE=dontKillMe java -Dhudson.util.ProcessTree.disable=true -jar /var/lib/jenkins/workspace/PipelineAsCode/target/ShoppingRetailService-0.0.1-SNAPSHOT.jar  --server.port=8086 &'
            }
        }
  }
}