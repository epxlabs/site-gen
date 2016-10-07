#! /bin/sh

PATH_PREFIX="src/site_generator"
CONF_PATH="${PATH_PREFIX}/config"

if [ -z "$1" ]
then
    echo "First argument must be the name of the environment (dev, staging, production)"
else
    cp "${CONF_PATH}/${1}.clj" "${PATH_PREFIX}/active_config.clj"
fi
