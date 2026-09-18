def gv

pipeline {

    agent any
    tools {
        maven 'maven-3.9'
    }

    stages {

        stage("init") {

            steps {
                script{
                  gv = load "script.groovy"  
                }
            }
        }
        stage("build Jar") {

            steps {
                script{
                    gv.buildJar()
                }
            }
        }
        stage("build IMG") {
            
            steps {
                script{
                    gv.buildImage()
                }
            }
        }
        stage("deploy") {
            
            steps {
                script{
                    gv.deployApp()
                }
            }
        }
    }
}
