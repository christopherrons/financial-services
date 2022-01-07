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
                                            description: 'Path of config file',
                                            name: 'Release Version'),
                            ])
                }
            }
        }

        stage('Build') {
            steps {
                git branch: 'main', url: 'https://github.com/christopherrons/shadow-orderbook.git'
                dir('shadow-orderbook') {
                    sh "./gradlew build -Prelease=true -PreleaseVersion=${releaseVersion}"
                }
                archiveArtifacts artifacts: "shadow-orderbook/build/libs/shadow-orderbook-${releaseVersion}.jar", followSymlinks: false
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
                sed - i "s/1.0.0/${releaseVersion}-SNAPSHOT/g" shadow -orderbook/build.gradle
                git add shadow -orderbook/build.gradle
                git commit - m "Gradle Release next development"
                git push
            }

        }*/
    }
}

