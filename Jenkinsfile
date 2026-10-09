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
                 def dockerCmd = "docker run -p 3080:3080 -d ${IMAGE_NAME}"
                 sshagent (['ec2-server-key']) {   
                     sh "ssh -o StrictHostKeyChecking=no ec2-user@65.2.5.28 ${dockerCmd}" 
                    }
                }
            }
        }
    }
}
