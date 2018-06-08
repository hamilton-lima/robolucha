#!/bin/bash

mvn sonar:sonar \
  -Dsonar.organization=hamilton-lima-github \
  -Dsonar.host.url=https://sonarcloud.io \
  -Dsonar.test=src-test \
  -Dsonar.login=dbfeba694a8a50bfef25e166a9148ac23e3968d5
