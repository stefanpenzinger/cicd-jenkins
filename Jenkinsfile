pipeline {
    agent any

    environment {
        DOCKER_IMAGE_NAME = 'gespenzt/cicd-jenkins'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/stefanpenzinger/cicd-jenkins.git'
            }
        }
        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }
        stage('Test') {
            steps {
                // Run Gradle tests
                sh './gradlew test'
            }
        }
        stage('Build Docker Image and push it') {
            steps {
                script {
                    docker.withRegistry('', 'docker-hub-credentials') {
                        def customImage = docker.build("${env.DOCKER_IMAGE_NAME}:latest")
                        customImage.push()
                    }
                }
            }
        }
        stage('Deploy App on k8s') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'k8s-secret', variable: 'TOKEN')]) {
                        sh 'kubectl --token $TOKEN --server http://192.168.49.2  --insecure-skip-tls-verify=true apply -f k8s/deployment.yaml '
                        sh 'kubectl --token $TOKEN --server http://192.168.49.2  --insecure-skip-tls-verify=true apply -f k8s/service.yaml '
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}
