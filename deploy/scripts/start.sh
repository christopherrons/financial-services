#!/bin/bash
source setenviroment_app.sh

function verifyDirectory() {
  deployDirectory="/home/ubuntu/deploy"
  if [ "$PWD" != "$deployDirectory" ]; then
    echo "Aborted: File has to be run from $deployDirectory but was run from $PWD"
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
versionFile="$commonVersionFile"
touch "$versionFile"
echo "$version" > "$versionFile"

# Start the application with specified version
echo "Starting Application: $version"
java -jar "$version"




