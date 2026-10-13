#!/usr/bin/env groovy

library identifier: 'jenkins-shared-library@main', retriever: modernSCM(
    [$class: 'GitSCMSource',
    remote: 'https://github.com/ali-waqar22/jenkins-shared-library.git',
    credentialsId: 'github-credentials'
    ]
)

pipeline {

    agent any
    tools {
        maven 'maven-3.9'
    }
    environment{
        IMAGE_NAME = 'aliwaqarbulc/demo-app:jma-3.0'
    }

    stages {
        stage("build app") {
            steps {
                 script{
                  echo "building the application jar..."
                   buildJar()
                }
            }
        }
        stage("build image") {
            steps {
                 script{
                  echo "building the docker image..."
                   buildImage(env.IMAGE_NAME)
                   dockerLogin()
                   dockerPush(env.IMAGE_NAME)
                }
            }
        }
        stage("deploy") {
            steps {
                script{
                 echo "deploying docker image to EC2...."
                 def dockerComposeCmd = "docker-compose -f docker-compose.yaml up -d"
                 sshagent (['ec2-server-key']) {   
                     sh "scp -o StrictHostKeyChecking=no docker-compose.yaml ec2-user@43.204.236.249:/home/ec2-user"
                     sh "ssh -o StrictHostKeyChecking=no ec2-user@43.204.236.249 ${dockerComposeCmd}" 
                    }
                }
            }
        }
    }
}
