#!/usr/bin/env bash

sbt clean -Denvironment="${ENVIRONMENT:=local}" "testOnly uk.gov.hmrc.api.cucumber.runner.Runner" -Dsecurity.assessment=true