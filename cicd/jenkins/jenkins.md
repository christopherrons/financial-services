# Jenkins Pipeline Scripts

The work tool Jenkins is used to compile, release and deploy the application.

## Requirements

* Running Jenkins server
* `Pipeline Utility Steps Plugin`

## Release Pipeline

### Description

The [JenkinsReleasePipelineScript.groovy](JenkinsReleasePipelineScript.groovy) script builds a release and sets the
version as specified by the user. The jar file
is archived and stored under `/var/lib/jenkins/jobs/shadoworderbook-release/builds/<build>/archive`.

## Deploy Pipeline

### Requirements

* Jenkins has to have access to repository with the script and be configured to find the file.
* The jenkins user on the jenkins master has to have its ssh key in `./ssh/autharized_keys` on the target node. The
  jenkins key kan be found in: `sudo su jenkins ./.ssh/id_rsa.pup`

### Description

The script `JenkinsDeployPipelineScript`  takes an archived jar file chosen by the user and copies it to a user
specified server. The current version on the target server is switch to version copied by the script.
