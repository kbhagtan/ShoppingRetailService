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
		withDockerRegistry([credentialsId: 'docker-registry-credentials', url: "https://hub.docker.com/r/kbhagtan3/pipeline/"]) {
        def image = docker.build("docker_hub_account/kbhagtan3:$BUILD_NUMBER", "--build-arg PACKAGE_VERSION=$BUILD_NUMBER ./tmp-docker-build-context")
        image.push()
      }  		
      }
    }
  }
}