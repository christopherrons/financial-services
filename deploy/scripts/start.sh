#!/bin/bash
source setenv.sh

function verifyDirectory() {
  deployDirectory="/home/$LXC_USER/deploy"
  if [ "$PWD" != "$deployDirectory" ]; then
    echo "Aborted: File has to be run from $deployDirectory but was run from $PWD"
    exit
  fi
}

function setNohupFileName() {
  nohupFile="/home/$LXC_USER/deploy/nohup.out"
  if test -f "$nohupFile"; then
      echo "Rename previous nohup"
      mkdir -p old_nohup
      mv nohup.out old_nohup/nohup_"$(date +'%Y-%m-%d_%H:%M:%S')".out
  fi
}

function checkIfRunning() {
  versionFile="/home/$LXC_USER/deploy/$COMMON_VERSION_FILE"
  if test -f "$versionFile"; then
      echo "Application running aborting..."
      exit
  fi
}

# Set the version
version=current
if [[ ${1+x} ]]
then
    version=$1
fi

# Persist version running
verifyDirectory
checkIfRunning
versionFile="$COMMON_VERSION_FILE"
touch "$versionFile"
echo "$version" > "$versionFile"

# Start the application with specified version
setNohupFileName
echo "Starting Application: $version"
nohup java -jar "$JVM_OPTS" "$version" &




