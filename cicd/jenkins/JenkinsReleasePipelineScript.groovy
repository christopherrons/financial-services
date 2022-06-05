def releaseVersion

pipeline {
    agent any
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
                archiveArtifacts artifacts: "financial-services/build/releases/shadow-orderbook-${releaseVersion}.tar.gz", followSymlinks: false
            }
        }

        stage("Clean Workspace") {
            steps {
                cleanWs()
            }
        }

        /* stage('Update repo') {
             steps {
                 git tag -a ${releaseVersion} -m "Gradle Release Build ${releaseVersion}"
                 git push-- tags
                 sed - i "s/1.0.0/${releaseVersion}-SNAPSHOT/g" shadow -orderbook/build.gradle.kts
                 git add shadow -orderbook/build.gradle.kts
                 git commit - m "Gradle Release next development"
                 git push
             }

         }*/
    }
}

