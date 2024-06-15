pipeline {
    agent any

    environment {
        // Define your environment variables
        DOCKER_CREDENTIALS_ID = 'docker-hub-credentials' // Docker Hub credentials ID
        DOCKER_IMAGE = "gespenzt/cicd-jenkins"
        K8S_NAMESPACE = "cicd-jenkins"
        K8S_CREDENTIALS_ID = 'k8s-credentials'
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from GitHub
                git branch: 'main', url: 'https://github.com/stefanpenzinger/.git'
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
                script {
                    docker.withRegistry('', DOCKER_CREDENTIALS_ID) {
                        def customImage = docker.build(DOCKER_IMAGE)
                        customImage.push("latest")
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
