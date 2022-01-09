def releaseVersions = []
def fileNameMap = [:]
def pickedReleaseVersion
def releaseDir = '/var/lib/jenkins/jobs/shadoworderbook-release'

node {

    dir(releaseDir) {
        def files = findFiles(glob: '**/*.jar')
        echo "${files}"
        files.each {
            fileNameMap[it.name] = it
            releaseVersions << it.name
        }
    }
}

pipeline {
    agent any
    stages {
        stage("Choose Release") {
            steps {
                script {
                    pickedReleaseVersion = input(
                            id: 'userInput', message: 'Enter the release version to deploy: ',
                            parameters: [
                                    choice(choices: releaseVersions,
                                            name: 'Release Version')
                            ])
                }
            }
        }
        stage("Deploy") {
            steps {
                script {
                    def host = input(
                            id: 'userInput', message: 'Enter the host name :',
                            parameters: [
                                    string(defaultValue: 'None',
                                            description: 'The host name',
                                            name: 'Host Name'),
                            ])
                    sh "rsync ${'/var/lib/jenkins/jobs/shadoworderbook-release'}/${fileNameMap[pickedReleaseVersion]} ubuntu@${host}:/home/ubuntu/deploy"
                    sh "ssh ubuntu@${host} 'ln -sf /home/ubuntu/deploy/${pickedReleaseVersion} deploy/current'"
                }
            }
        }
        stage("Clean Workspace") {
            steps {
                cleanWs()
            }
        }
    }
}
