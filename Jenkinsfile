pipeline {
    agent any // Tells Jenkins it can run on any available environment

    tools {
        // Ensures Maven and Java are available (must be configured in Jenkins settings)
        maven 'Maven 3.4.1' 
        jdk 'Java 17'
    }

    stages {
        stage('Checkout') {
            steps {
                // Pulls the latest code from your Git repository
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Building the application...'
                // Compiles the code and creates the executable .jar file
                sh 'mvn clean package -DskipTests' 
            }
        }

        stage('Test') {
            steps {
                echo 'Running unit tests...'
                // Runs your JUnit/Mockito tests
                sh 'mvn test' 
            }
            post {
                always {
                    // Saves the test results so you can view them in the Jenkins dashboard
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Deploy') {
            when {
                // Only run the deployment stage if we are on the main branch
                branch 'main' 
            }
            steps {
                echo 'Deploying to staging environment...'
                // This is where you would put your script to push the Docker image
                // or restart your application server with the new .jar file
                sh './deploy.sh'
            }
        }
    }
}
