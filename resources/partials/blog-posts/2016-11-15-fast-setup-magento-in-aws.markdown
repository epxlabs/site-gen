# Magento: quickly set-up autoscaling Magento on AWS with a Prodution and Dev environment

<table>
  <tr>
    <td>
      <img style="display:block;" width="100%" src="https://s3.amazonaws.com/blog-images.epxlabs.com/fast-setup-magento-in-aws/1.png">
    </td>
    <td>
      <img style="display:block;" width="100%" src="https://s3.amazonaws.com/blog-images.epxlabs.com/fast-setup-magento-in-aws/2.png">
    </td>
  </tr>
</table>

We have open-sourced the following [cloudformation templates](https://github.com/epxlabs/magento-in-aws) so you can effortlessly deploy the environments illustrated above.
<br>
Figure 1 is a standard out-of-the-box autoscaling production setup. The MySQL database is provisioned on RDS and leverages cross availability zone replication.
<br>
Figure 2 is better suited for a development environment to save costs on infrastructure. You may turn off instances when they are not required.

This work is meant to enhance the current Quick Start guide found here: [Magento on the AWS Cloud: Quick Start Reference Deployment](http://docs.aws.amazon.com/quickstart/latest/magento/welcome.html)

We have reworked the guides Cloudformation templates and corrected some flaws.

*****


### The AWS Quickstart Guide, Before our Enhancements:

- Over-provisioned resources: depending on the region, the quick start setup will create 2 or 3 availability zones with an EC2 NAT in each. If your setup is small these are unnecessary costs.
- Incorrectly configured Load-balancer: only two zones are included in the load balancing even when a third is provisioned.
- We prefer not to use EC2 NATs, we like to simplify our infrastructure  management. The Quick-start guide uses EC2s.
- The sample development environment that we diagrammed is not included, only the RDS based option exists.

*****

### After our enhancements you now have the following improvements:

- Choose your own # of availability zoens: now you can select the exact number of availablity zones (up to 5) for setup. If there are less AZs in your region than what you would like to provision the maximum number allowed will be setup.
- Load balancing correcting: all your provisioned AZs will be included in load balancing.
- Less Management: the EC2 NATs have been replaced by NAT Gateways and we have added a Bastion (as the entry point).
- Cheaper Development environment option available. (We typically suggest that production and staging/dev are mimic's of one another so you can properly test infrastructure level changes in staging, however, this might be cost prohibitive for your organization. So, we've included a cheaper single EC2 option that can be turned off when not in use.) 

*****

## How to use our templates:

- Clone the repository from our github account:

```clojure
git clone git@github.com:epxlabs/magento-in-aws.git
cd magento-in-aws
```

### We suggest placing the cloudformation templates into S3 on your AWS account:

**STEP 1.** Download Magento 1.9.x and Magento Sample Data - 1.x in `tar.gz` format (you will probably need to register at https://magento.com/) 

**STEP 2.** Create an s3 bucket for your Magento sample data.

```clojure
aws s3 mb s3://<mybucket> --region <region_if_needed>
# eg.aws s3 mb s3://epxlabs-magento-data --region us-west-2
```

**STEP 3.** Upload a.k.a. `sync` the Magento and Magento Sample Data to your s3 bucket:

```clojure
aws s3 sync . s3://epxlabs-magento-data --exclude '.git/*' --acl public-read --delete --region us-west-2
```

**STEP 4.** Create an s3 bucket for the templates:

```clojure
aws s3 mb s3://<mybucket> --region <region_if_needed>
# eg.aws s3 mb s3://epxlabs-magento-templates --region us-west-2
```

**STEP 5.** Change default value for `TemplatesBucket` parameter in the `master.template` file from the epxlabs/magento-in-aws repo, or, override it when you deploy the new stack.

```clojure
  "Parameters" : {
    "TemplatesBucket" : {
      "Type" : "String",
      "Default" : "https://s3-{*INSERT BUCKET REGION*}.amazonaws.com/{*INSERT BUCKET NAME*}",
      "Description" : "Path to bucket with templates for setup",
      "ConstraintDescription" : "must be valid path to bucket with templates. Ex. https://s3-us-west-2.amazonaws.com/epxlabs-magento-templates"
    },
```

**STEP 6.** Change default values for the `MagentoReleaseMedia` and `MagentoSampleData` parameters in the `master.template` or override them when you deploy new stack.

```clojure
    "MagentoReleaseMedia": {
      "Description": "Amazon S3 path to download magento .tar.gz file (s3://mybucket/magento-1.9.2.2.tar.gz)",
      "Default": "s3://{*INSERT S3 BUCKET NAME HERE*}/magento-1.9.3.0-2016-10-11-06-05-14.tar.gz",
      "Type": "String"
    },
    "MagentoSampleData": {
      "Description": "[ optional ] Amazon S3 path to download magento sample data file ((s3://mybucket/magento-sample-data.tar.gz)",
      "Default": "s3://{*INSERT S3 BUCKET NAME HERE*}/magento-sample-data-1.9.2.4-2016-10-11-07-38-13.tar.gz",
      "Type": "String"
    },
```

**STEP 7.** Upload a.k.a. `sync` the templates into your new s3 bucket

```clojure
aws s3 sync . s3://epxlabs-magento-templates --exclude '.git/*' --acl public-read --delete --region us-west-2
```

**STEP 8.** Create a new stack using Cloudformation in the AWS Console or using the AWS CLI.

**STEP 9.** Any questions, issues, errors, or propositions please contact hello@epxlabs.com

### Hint: How to figure out the real count of Availability Zones in regions

```clojure
$ for i in $(aws ec2 describe-regions|grep RegionName|awk '{print $2}'| tr -d \"); do echo Region $i contains; aws ec2 describe-availability-zones --region $i|grep ZoneName; done
...
Region us-east-1 contains
            "ZoneName": "us-east-1a"
            "ZoneName": "us-east-1c"
            "ZoneName": "us-east-1d"
            "ZoneName": "us-east-1e"
Region us-west-1 contains
            "ZoneName": "us-west-1a"
            "ZoneName": "us-west-1b"
...
```
