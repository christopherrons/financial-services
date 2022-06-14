def releaseVersion

pipeline {
    agent { label 'ubuntu' }
    tools {
        jdk 'jdk-17'
    }
    stages {
        stage('Add release version') {
            steps {
                script {
                    releaseVersion = input(
                            id: 'userInput', message: 'Enter the release version :',
                            parameters: [
                                    string(defaultValue: 'None',
                                            description: 'The release version of the app.',
                                            name: 'Release Version'),
                            ])
                }
            }
        }

        stage('Build') {
            steps {
                git branch: 'main', url: 'https://github.com/christopherrons/financial-services.git'
                sh "./gradlew build -PreleaseVersion=${releaseVersion}"
                sh "./gradlew packageRelease -PreleaseVersion=${releaseVersion}"
                archiveArtifacts artifacts: "financial-services/build/releases/financial-services-${releaseVersion}.tar.gz", followSymlinks: false
            }
        }

        stage("Clean Workspace") {
            steps {
                cleanWs()
            }
        }
    }
}

