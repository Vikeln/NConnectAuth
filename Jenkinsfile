pipeline {
    agent any
    environment {
        VERSION = version()
        workspace = pwd()
        serviceName = name()
        registryCredential = "TmwauraHarb"
        ImageName = "harb.diab.mfs.co.ke/total-mobiconnect/${serviceName}:${VERSION}.${BUILD_NUMBER}"
    }

    stages {
        stage ('Clone Repository'){
            steps {
                checkout scm
            }
        }
        stage ('Build Jar File') {
            steps {
              withMaven(maven: 'M3'){
                    sh "mvn clean package -Dmaven.test.skip=true  sonar:sonar  -Dsonar.projectKey=users -Dsonar.host.url=http://10.38.83.165:9000  -Dsonar.login=a13e777d162f155d6186b5c5f9e29b5649ae6f5f"
                }
            }
        }
        stage('Build Docker Image'){
            steps {
               script{
                app = docker.build("${ImageName}")
                }
             }
        }
        stage('Push Image to Docker Registry') {
          steps{
            script {
              docker.withRegistry("https://harb.diab.mfs.co.ke","TmwauraHarb"){
                appname = app.push("${VERSION}.${BUILD_NUMBER}")
              }
            }
          }
        }

        stage("Deploy For Test on K8s"){
            steps{
                sh "sed -i 's|ImageName|${ImageName}|' Kubernetes/deployment.yaml"
                sh "kubectl apply -f Kubernetes/deployment.yaml"
            }
        }
    }
}
def version(){
    pom = readMavenPom file: 'pom.xml'
    return pom.version
}

def name(){
    pom = readMavenPom file: 'pom.xml'
    return pom.name
}
