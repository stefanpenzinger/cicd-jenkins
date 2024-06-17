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
                node {
                    checkout scm
                    docker.withRegistry('docker.io', 'docker-hub-credentials') {
                        def customImage = docker.build("${env.DOCKER_IMAGE_NAME}:${env.BUILD_NUMBER}")
                        customImage.push()
                    }
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
