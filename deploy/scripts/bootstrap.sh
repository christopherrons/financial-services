#!/bin/bash
source setenv.sh

function verifyDirectory() {
  deployDirectory="/home/$LXC_USER/deploy"
  if [ "$PWD" != "$deployDirectory" ]; then
    echo "Aborted: File has to be run from $deployDirectory but was run from $PWD"
    exit
  fi
}

function init() {
  mkdir -p "$RELEASE_DIR"
}

function saveRelease() {
  nrOfJar="$(ls -t *.jar | wc -l)"
  if (("$nrOfJar" > 2)); then
    previousRelease="$(readlink -f previous)"
    mv "$previousRelease" "$RELEASE_DIR"
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
  nrOldReleases="$(ls -l "$RELEASE_DIR" | wc -l)"
  while ((nrOldReleases > 5)); do
    oldestRelease=$(ls -t "$RELEASE_DIR" | tail -1)
    rm "$RELEASE_DIR"/"$(ls -t "$RELEASE_DIR" | tail -1)"
    echo "Deleting release: $oldestRelease"
    nrOldReleases="$(ls -t "$RELEASE_DIR" | wc -l)"
  done
}

# Procedure
verifyDirectory
init
saveRelease
updateVersion
cleanOldReleases
