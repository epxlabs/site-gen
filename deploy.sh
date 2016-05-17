#!/bin/sh

aws s3 sync resources/exported_site/ s3://staging.epxlabs.com --acl public-read --exclude 'vendor/*'
aws s3 sync resources/public/vendor/modernizr s3://staging.epxlabs.com/vendor/modernizr --acl public-read --dryrun
aws s3 sync resources/exported_site/vendor/font-awesome/fonts s3://staging.epxlabs.com/vendor/font-awesome/fonts --acl public-read --dryrun
aws s3 sync resources/exported_site/vendor/rs-plugin/fonts s3://staging.epxlabs.com/vendor/rs-plugin/fonts --acl public-read
aws s3 sync resources/exported_site/vendor/simple-line-icons/fonts s3://staging.epxlabs.com/vendor/simple-line-icons/fonts --acl public-read --dryrun
