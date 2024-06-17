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
                // Run Gradle build
                sh './gradlew clean build'
            }
        }
        stage('Test') {
            steps {
                // Run Gradle tests
                sh './gradlew test'
            }
        }
        stage('Build Docker Image') {
            steps {
                docker.build("${env.DOCKER_IMAGE_NAME}:${env.BUILD_NUMBER}")
            }
        }
        stage('Push Docker Image') {
            steps {
                docker.withRegistry('docker.io', 'docker-hub-credentials') {
                    docker.image("${env.DOCKER_IMAGE_NAME}:${env.BUILD_NUMBER}").push()
                }
            }
        }
        stage('Deploy App on k8s') {
            steps {
                withCredentials([string(credentialsId: 'k8s-token', variable: 'api_token')]) {
                    sh 'kubectl --token $api_token --server http://192.168.49.2  --insecure-skip-tls-verify=true apply -f k8s/deployment.yaml '
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
