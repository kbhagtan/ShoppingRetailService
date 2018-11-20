pipeline {

	environment {
    registry = "docker_hub_account/kbhagtan3"
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
			def customImage = docker.build registry + ":$BUILD_NUMBER"
			customImage.push()
			}
      }
    }
  }
}