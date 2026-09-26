def gv

pipeline {
    agent any
    tools {
        maven 'Maven'
    }

    stages {
        stage("increment version"){
            steps {
                script{
                    echo 'incrementing app version...'
                    // Use Groovy triple-single-quotes for clean multi-line shell commands
                    // Use standard single-quotes around the Maven argument to protect the $ from Bash
                    sh '''
                        mvn build-helper:parse-version versions:set \
                        '-DnewVersion=${parsedVersion.majorVersion}.${parsedVersion.nextMinorVersion}.${parsedVersion.incrementalVersion}' \
                        versions:commit
                    '''
                    def matcher = readFile('pom.xml') =~ `<version>(.+)<\version>`
                    def version =matcher[0][1]
                    env.IMAGE_NAME = "$version - $BUILD_NUMBER"
                }
            }
        }    

        stage("build app") {
            steps {
                script{
                   echo 'building the application...'
                   sh 'mvn clean package'
                }
            }
        }

        stage("build image") {
            steps {
                script{
                    echo 'building docker image...'
                    withCredentials ([usernamePassword(credentialsId:'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                        sh 'docker build -t aliwaqarbulc/demo-app:$IMAGE_NAME .'
                        sh 'echo $PASS | docker login -u $USER --password-stdin'
                        sh 'docker push aliwaqarbulc/demo-app:$IMAGE_NAME'
                    }
                }
            }
        }

        stage("deploy") {
            steps {
                script{
                 echo "deploying the docker image..."
                }
            }
        } 

        stage ("commit version update"){
            steps {
                script{
                    withCredentials ([usernamePassword(credentialsId:'github-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                        sh 'git config --global user.email "aliwaqar.cys@gmail.com"'
                        sh 'git config --global user.name "Ali Waqar"'

                        sh 'git status'
                        sh 'git branch'
                        sh 'git config --list'

                        sh 'git remote set-url origin https://${USER}:${PASS}@github.com/ali-waqar22/java-maven-app.git'
                        sh 'git add .'
                        sh 'git commit -m "CI: Update version"'
                        sh 'git push origin HEAD:jenkins-jobs'
                    }
                }
            }
        }
    }
}
