#!/bin/sh

if [ -z "$1" ]
then
    echo "Please specify stage (either staging or production)"
else
    if  [ "staging" != "$1" ] && [ "production" != "$1" ]
    then
        echo "Please specify stage (either staging or production)"
    else
        if [ "production" == "$1" ]
        then
            SUBDOMAIN="www"
        else
            SUBDOMAIN="staging"
        fi

        aws s3 sync resources/exported_site/ s3://$SUBDOMAIN.epxlabs.com --acl public-read --exclude 'vendor/*'
        aws s3 sync resources/public/vendor/modernizr s3://$SUBDOMAIN.epxlabs.com/vendor/modernizr --acl public-read
        aws s3 sync resources/exported_site/vendor/font-awesome/fonts s3://$SUBDOMAIN.epxlabs.com/vendor/font-awesome/fonts --acl public-read
        aws s3 sync resources/exported_site/vendor/rs-plugin/fonts s3://$SUBDOMAIN.epxlabs.com/vendor/rs-plugin/fonts --acl public-read
        aws s3 sync resources/exported_site/vendor/simple-line-icons/fonts s3://$SUBDOMAIN.epxlabs.com/vendor/simple-line-icons/fonts --acl public-read

    fi
fi
