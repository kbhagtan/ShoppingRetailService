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
        
		 stage('Building image') {
			steps{
			script {
			docker build -t kbhagtan3/pipeline:$BUILD_NUMBER
			sudo docker push kbhagtan3/pipeline:$BUILD_NUMBER
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