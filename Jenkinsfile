pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from GitHub
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
        stage('Deploy Docker Image') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'dockerhub-secret', variable: 'dockerhubpwd')]) {
                        sh 'docker login -u gespenzt -p ${dockerhubpwd}'
                    }
                    sh 'docker push gespenzt/cicd-jenkins:latest'
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

       /*  stage('Deploy to Kubernetes') {
            steps {
                script {
                    kubernetesDeploy(configs: 'k8s/deployment.yaml', kubeConfig: [path: '/home/jenkins/.kube/config'])
                }
            }
        } */
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
