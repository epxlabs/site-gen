#!/bin/bash -ex

CURRENT_DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

RESOURCES_DIR="resources"
PUBLIC_VENDOR="${RESOURCES_DIR}/public/vendor"
EXPORTED_SITE="${RESOURCES_DIR}/exported_site"
EXPORTED_VENDOR="${EXPORTED_SITE}/vendor"

STAGE="${1}"

if [ -z "${STAGE}" ]
then
    echo "Please specify stage (either staging or production)"
else
    if  [ "staging" != "${STAGE}" ] && [ "production" != "${STAGE}" ]
    then
        echo "Please specify stage (either staging or production)"
    else
        if [ "production" == "${STAGE}" ]
        then
            SUBDOMAIN="www"
        else
            SUBDOMAIN="staging"
        fi
    fi
fi

BASE_S3_URL="s3://${SUBDOMAIN}.epxlabs.com"

pushd $CURRENT_DIR/..

    bin/set_env.sh "${STAGE}"
    lein export-site

    aws s3 sync "${EXPORTED_SITE}/" $BASE_S3_URL --acl public-read --exclude 'vendor/*'
    aws s3 sync "${PUBLIC_VENDOR}/modernizr" "${BASE_S3_URL}/vendor/modernizr" --acl public-read
    aws s3 sync "${EXPORTED_VENDOR}/font-awesome/fonts" "${BASE_S3_URL}/vendor/font-awesome/fonts" --acl public-read
    aws s3 sync "${EXPORTED_VENDOR}/rs-plugin/fonts" "${BASE_S3_URL}/vendor/rs-plugin/fonts" --acl public-read
    aws s3 sync "${EXPORTED_VENDOR}/simple-line-icons/fonts" "${BASE_S3_URL}/vendor/simple-line-icons/fonts" --acl public-read

popd
