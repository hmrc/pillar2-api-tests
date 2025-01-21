#!/usr/bin/env bash

browser="chrome"
if [ $# -gt 0  ];
then
  browser="$1"
fi

environment="local"

export ZAP_FORWARD_ENABLE="true"
export ZAP_FORWARD_PORTS=$(lsof -i -P | grep LISTEN | grep :$PORT | grep java | awk '{ print $9}' | sed 's/\*://g' | paste -sd " " -)
export ZAP_LOCAL_SCANNER_CONFIG=sm-api.json
export ZAP_LOCAL_ALERT_FILTERS="${PWD}/alert-filters.json"

(
  cd $WORKSPACE/dast-config-manager
  make local-zap-running
)

echo "Running tests..."
echo "=========================================="
echo "Browser:              ${browser}"
echo "Env:                  ${environment}"
echo "ZAP Proxy Required:   true"
echo "ZAP alert filters:    ${ZAP_LOCAL_ALERT_FILTERS}"
echo "=========================================="

#sbt -Denvironment=$environment -Dzap.proxy=true clean 'testOnly uk.gov.hmrc.api.cucumber.runner.Runner'

sbt -Dbrowser=${BROWSER} -Denvironment=${ENVIRONMENT} -Dzap.proxy=true "testOnly runner.Runner" testReport
echo "Generating ZAP HTML report..."
curl "http://localhost:11000/OTHER/core/other/htmlreport/" -o ./target/zap-report.html
if [ $? -eq 0 ]; then
  echo "ZAP report successfully generated at ./target/zap-report.html"
else
  echo "Failed to generate ZAP report. Please check ZAP is running and accessible."
fi

(
  cd $WORKSPACE/dast-config-manager
  make local-zap-stop
)