# loans-lah-tdd-workshop

## Structures
### loan
Backend application, exposes APIs for loan domain (e.g. taking a loan, viewing loans belonged to an account), containing 2 modules:
- **loan-server**: Application code and unit tests for loan service.
- **loan-acceptance-test**: Acceptance tests of loan service APIs.

### db
Database module, used by docker

## Setup
Setup for project for [IntelliJ](doc/SETUP.md)

## Requirements
`openjdk-11`

`docker` latest version

`docker-compose` [compatible](https://docs.docker.com/compose/compose-file/) with version 3.7 of compose files

## How to test
Run `./build.sh` from the main directory to build all modules

## How to build & run
Run `./build.sh` from the main directory to build all modules

Next, run `./start.sh` to run all of them.

## Troubleshooting
In any case you'd like to clean docker containers and LoansLah docker's images run `./reset.sh`
