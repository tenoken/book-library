pipeline {
    agent any

    tools {
        maven 'Maven 3.9.10' // Adjust to match your Jenkins Maven install
        jdk 'JAVA 21'
    }

    environment {
        SONARQUBE = 'Local SonarQube' // Name from Jenkins config
        SONAR_TOKEN = credentials('sonar_token') // Stored in Jenkins credentials
        JAVA_HOME = '/opt/java/openjdk' // or actual path
        PATH = "${env.JAVA_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/tenoken/book-library.git'
            }
        }

        stage('Build & Test') {
            steps {
                echo "JAVA_HOME: ${JAVA_HOME}"
                sh 'mvn clean verify'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv("${SONARQUBE}") {
                    sh '''
                    mvn sonar:sonar \
                      -Dsonar.projectKey=book-library \
                      -Dsonar.login=$SONAR_TOKEN \
                      -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                    '''
                }
            }
        }
    }
}
