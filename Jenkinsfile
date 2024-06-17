pipeline {
    agent any

    environment {
        DOCKER_IMAGE_NAME = 'gespenzt/cicd-jenkins'
    }

    tools {
        go '1.22.2'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/stefanpenzinger/cicd-jenkins.git'
            }
        }
        stage('Build') {
            steps {
                sh 'go build -v'
            }
        }
        stage('Test') {
            steps {
                sh 'go test -v'
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
                    kubernetesDeploy(configs: "k8s/deploymentAndService.yaml", kubeconfigId: "k8s-config")
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
