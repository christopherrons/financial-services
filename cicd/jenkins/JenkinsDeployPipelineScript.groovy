def releaseVersions = []
def fileNameMap = [:]
def pickedReleaseVersion
def releaseDir = '/var/lib/jenkins/jobs/financial-services-release'

node('master') {
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
    agent { label 'master' }
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
                    def deployDir = "/home/herron/deploy"
                    sh "rsync ${'/var/lib/jenkins/jobs/financial-services-release'}/${fileNameMap[pickedReleaseVersion]} herron@${host}:${deployDir}"
                    sh "ssh herron@${host} 'tar -xf ${deployDir}/${pickedReleaseVersion} --directory ${deployDir}'"
                    sh "ssh herron@${host} 'rm ${deployDir}/${pickedReleaseVersion}'"
                    sh "ssh herron@${host} 'cd ${deployDir} && bash bootstrap.sh'"
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
