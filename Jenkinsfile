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
		
		stage ('Dockerise') {
		
		   steps {
		     sh "apt-get install curl 
			     && curl -fsSLO https://get.docker.com/builds/Linux/x86_64/docker-17.04.0-ce.tgz \
                 && tar xzvf docker-17.04.0-ce.tgz \
                 && mv docker/docker /usr/local/bin \
                 && rm -r docker docker-17.04.0-ce.tgz"
		     sh "docker build . -t ShoppingRetailService:${env.BUILD_ID}"
		   }
		}
		
        
        stage ('Deploy Stage') {

            steps {
                     sh 'BUILD_ID=dontKillMe JENKINS_NODE_COOKIE=dontKillMe java -Dhudson.util.ProcessTree.disable=true -jar /var/lib/jenkins/workspace/PipelineAsCode/target/ShoppingRetailService-0.0.1-SNAPSHOT.jar  --server.port=8086 &'
            }
        }
  }
}