#!/bin/bash
docker-compose down --remove-orphans

pushd ./loan; ./build.sh
popd

docker-compose build