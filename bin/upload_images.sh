#!/bin/bash -ex
CURRENT_DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
BLOG_IMAGES="resources/public/blog_images"
BASE_S3_URL="s3://blog-images.epxlabs.com"
aws s3 sync "${BLOG_IMAGES}/" "${BASE_S3_URL}/" --acl public-read
