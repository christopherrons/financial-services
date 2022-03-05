def releaseVersions = []
def fileNameMap = [:]
def pickedReleaseVersion
def releaseDir = '/var/lib/jenkins/jobs/shadoworderbook-release'

node {
    dir(releaseDir) {
        def files = findFiles(glob: '**/*.tar.gz')
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
                                    choice(choices: releaseVersions.reverse(),
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
                    def deployDir = "/home/ubuntu/deploy"
                    sh "rsync ${'/var/lib/jenkins/jobs/shadoworderbook-release'}/${fileNameMap[pickedReleaseVersion]} ubuntu@${host}:${deployDir}"
                    sh "ssh ubuntu@${host} 'tar -xf ${deployDir}/${pickedReleaseVersion} --directory ${deployDir}'"
                    sh "ssh ubuntu@${host} 'rm ${deployDir}/${pickedReleaseVersion}'"

                    sh "ssh ubuntu@${host} 'cd ${deployDir} && bash bootstrap.sh'"
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