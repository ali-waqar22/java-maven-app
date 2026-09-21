#!/user/bin/env

@Library('jenkins-shared--library')
def gv

pipeline {

    agent any

    stages {

        stage("init") {

            steps {
                script{
                  gv = load "script.groovy"
                }
            }
        }
        stage("build jar") {
            steps {
                 script{
                 buildJar()
                }
            }
        }
        stage("build image") {
            steps {
                 script{
                 buildImage()
                }
            }
        }
        stage("deploy") {
            when {
                expression {
                    BRANCH_NAME == 'main' || BRANCH_NAME == 'master'
                }
            }
            steps {
                script{
                 gv.deployApp()
                }
            }
        }
    }
}
