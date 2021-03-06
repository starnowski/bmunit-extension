#!/usr/bin/env bash

echo "Deploying tag $TRAVIS_TAG"

DIRNAME="$(dirname $0)"
# Setting gpg directory path
export GPG_DIR="$DIRNAME/deploy"

# Decrypting key files
openssl aes-256-cbc -pass pass:$ENCRYPTION_PASSWORD -in $GPG_DIR/pubring.gpg.enc -out $GPG_DIR/pubring.gpg -d
openssl aes-256-cbc -pass pass:$ENCRYPTION_PASSWORD -in $GPG_DIR/secring.gpg.enc -out $GPG_DIR/secring.gpg -d

mvn --settings .travis/settings.xml -DskipTests deploy -P sign-artifacts
