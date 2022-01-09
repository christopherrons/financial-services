#!/bin/bash
source setenviroment_app.sh

function verifyDirectory() {
  deployDirectory="/home/ubuntu/deploy"
  if [ "$PWD" != "$deployDirectory" ]; then
    echo "Aborted: File has to be run from $deployDirectory but was run from $PWD"
    exit
  fi
}

function init() {
  mkdir -p "$releaseDir"
}

function saveRelease() {
  nrOfJar="$(ls -t *.jar | wc -l)"
  if (("$nrOfJar" > 2)); then
    previousRelease="$(readlink -f previous)"
    mv "$previousRelease" "$releaseDir"
  fi
}

function updateVersion() {
  nrOfJar="$(ls -t *.jar | wc -l)"
  if (("$nrOfJar" > 1)); then
    previousRelease="$(readlink -f current)"
    ln -fs "$(basename "$previousRelease")" previous
    echo "Previous set to: $previousRelease"
  fi

  newRelease="$(ls -t *.jar | head -1)"
  ln -fs "$newRelease" current
  echo "Current set to: $newRelease"
}

function cleanOldReleases() {
  nrOldReleases="$(ls -l "$releaseDir" | wc -l)"
  while ((nrOldReleases > 5)); do
    oldestRelease=$(ls -lt "$releaseDir" | tail -1)
    rm "$oldestRelease"
    echo "Deleting release: $oldestRelease"
    nrOldReleases="$(ls -l "$releaseDir" | wc -l)"
  done
}

# Procedure
verifyDirectory
init
saveRelease
updateVersion
cleanOldReleases
