pipeline {

    agent any

    stages {

        stage("test") {

            steps {
                script{
                  echo "testing the application..."
                }
            }
        }
        stage("build") {
            steps {
                 script{
                  echo "building the application..."
                }
            }
        }
        stage("deploy") {

            steps {
                script{
                 echo "deploying the application..."
                 sshagent (['ec2-server-key']) {
                     def dockerCmd = 'docker run -p 3080:3080 -d aliwaqarbulc/react-nodejs-example:1.0'   
                     sh "ssh -o StrictHostKeyChecking=no ec2-user@65.2.5.28 ${dockerCmd}" 
                    }
                }
            }
        }
    }
}
