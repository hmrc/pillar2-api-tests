#!/usr/bin/env bash

#sbt clean -Denvironment="${ENVIRONMENT:=local}" "testOnly uk.gov.hmrc.api.cucumber.runner.Runner" -Dsecurity.assessment=true


BROWSER=$1
ENVIRONMENT=$2

sbt clean -Dbrowser="${BROWSER:=chrome}" -Denvironment="${ENVIRONMENT:=local}" -Dsecurity.assessment=true "testOnly uk.gov.hmrc.api.Specs.* -- -n ApiAcceptanceTests" testReport