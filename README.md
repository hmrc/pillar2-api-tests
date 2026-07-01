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
/usr/bin/env bash ./run_tests_local.sh
```

## Scalafmt

Check all project files are formatted as expected with:

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

#### Run security tests on jenkins
`./run_tests.sh`

## License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
