pipeline {
    agent any

    stages {
	stage('For every new commit')
	{
	
	steps{
	{
	if (env.GIT_COMMIT != env.GIT_PREVIOUS_SUCCESSFUL_COMMIT)
	echo "Triggering a new build"
	}
	}
	}
	
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
                     sh 'nohup java -jar /var/lib/jenkins/workspace/PipelineAsCode/target/ShoppingRetailService-0.0.1-SNAPSHOT.jar  --server.port=8086 &'
            }
        }
	
	
  }
  }