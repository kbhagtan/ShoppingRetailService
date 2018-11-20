pipeline {

	environment {
    registry = "kbhagtan3/pipeline"
	}
	
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
			def dockerImage =  docker.build registry + ":$BUILD_NUMBER"
			}
			sh "docker run -dp 9066:9066 kbhagtan3/pipeline:${env.BUILD_ID}" 

      }
    }
    }
  }