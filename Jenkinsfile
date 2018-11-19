pipeline {
    agent any

    stages {
         stage ('Clone Stage'){
             steps{
                 git "https://github.com/kbhagtan/ShoppingRetailService.git"
             }
         }


        stage ('Package Stage') {

            steps {
                withMaven(maven : 'Maven6') {
                    sh "mvn clean install -Dmaven.test.skip=true"
                }
            }
        }
        
        stage ('Deploy Stage') {

            steps {
                     sh 'JENKINS_NODE_COOKIE=dontKillMenohup java -jar /var/lib/jenkins/workspace/PipelineAsCode/target/ShoppingRetailService-0.0.1-SNAPSHOT.jar  --server.port=8086 &'
            }
        }
  }
}