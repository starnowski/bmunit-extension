#!/usr/bin/env bash

echo "Deploying tag $TRAVIS_TAG"

mvn --settings .travis/settings.xml deploy
