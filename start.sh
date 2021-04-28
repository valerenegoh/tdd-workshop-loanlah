#!/bin/bash

docker-compose down --remove-orphans

trap "exit" INT TERM ERR
trap "kill 0" EXIT

docker-compose up --build --force-recreate &

wait