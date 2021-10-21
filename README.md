# loans-lah-tdd-workshop

### Source
https://github.com/ThoughtWorksInc/sea-tdd-workshop-09-2021

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

## How to add acceptance test
`cd loan/loan-acceptance-test`  
`docker-compose up --build`  

## How to test
Run `./build.sh` from the main directory to build all modules

## How to build & run
Run `./start.sh` from the main directory to run all modules

### Troubleshooting
In any case you'd like to clean docker containers and LoansLah docker's images run `./reset.sh`

## Tasks

1. Given an amount and an interest rate calculate total outstanding amount.  
   Formula:  
   `Daily Interest = (Principal * Interest Rate) / Interest Basis`
   `Total Interest = Daily Interest * Duration`  
   `Total Outstanding = Principal + Total Interest`
    - If amount = $1000  and duration is 365 and interest rate is 10% then outstanding amount = $1100
    - If amount = $1000 and and duration is 10 and interest rate is 20% then outstanding amount = $1005.48  
      Refer to loan calculator [here](https://docs.google.com/spreadsheets/d/1dKo7YAgzPUfivOaJ1gAPDaEoaXroFLxSa62ZmVtrkLI/edit#gid=0)
####
2. Given a user of LoansLah  
   When he/she takes a loan of $1000 for a duration of 1 month (30 days) & at interest rate of 20%  
   Then the loan outstanding should have been $1016.44 but got $1012.33
####
3. Given a loan with duration more than 1 year,  
   Then interest rate should be set as the base interest rate set by MAS ([external service](https://random-data-api.com/api/number/random_number)).
    - Between 1 - 30 days interest rate 20%.
    - Between 31 - 180 days interest rate 15%.
    - Between 181 - 365 days interest rate 10%.
    - Any longer duration: base interest rate set by MAS.
####
4. Write new Acceptance Test to make our application call the external dependency.  
   The existing acceptance test is never calling our external Interest Rate Provider, because the duration is still under 365 days.
####
5. Support both types of loans:
    - Currently, if you would like to paid off a 365-day loan for $100 on the 5th day you need to pay $100 + $10 = $110
    - Now, if you would like to pay off a 365-day loan for $100 on the 5th day you should pay $100 + $0.14 = $100.14