def buildJar(){
    echo 'building the application...'
    sh 'mvn package'
}

def buildImage(){
    echo 'building docker image...'
    withCredentials ([usernamePassword(credentialsId:'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
         sh 'docker build -t aliwaqarbulc/demo-app:jma-2.0 .'
         sh 'echo $PASS | docker login -u $USER --password-stdin'
         sh 'docker push aliwaqarbulc/demo-app:jma-2.0'
    }
}

def deployApp(){
    echo 'deploying the application...'
}
return this
