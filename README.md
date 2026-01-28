**This is the template README. Please update this with project specific content.**

# pillar2-api-tests


## Pre-requisites

### Services

Start Mongo Docker container as follows:

```bash
docker run --rm -d -p 27017:27017 --name mongo percona/percona-server-mongodb:5.0
```

Start `PILLAR_2_ALL` services as follows:

```bash
sm2 --start PILLAR_2_ALL
```

## Tests

Run tests as follows:

```bash
/usr/bin/env bash /Users/prsat/Desktop/Project/pillar2-api-tests/run_tests_local.sh
```

## Scalafmt

Check all project files are formatted as expected as follows:

```bash
sbt scalafmtCheckAll scalafmtCheck
```

Format `*.sbt` and `project/*.scala` files as follows:

```bash
sbt scalafmtSbt
```

Format all project files as follows:

```bash
sbt scalafmtAll
```

#### Run security tests locally
To run zap tests for any of the services, download from https://www.zaproxy.org/download/, extract and execute the
following in the root of the extracted folder:

`./zap.sh -daemon -config api.disablekey=true -port 11000`

and run below command to run security tests locally:

`./run_zap_tests_local.sh`

#### Run security tests on jenkins
`./run_tests.sh`

## License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
