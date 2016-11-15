# Magento: fast setup in AWS

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

With our [cloudformation templates](https://github.com/epxlabs/magento-in-aws) You can create one of the options above.
Figure 1 is recommended for production setup, as the database is available in the RDS and uses cross-az replication.
On the other hand to develop better use of Scheme 2, you can turn off instances when they are not needed, while the RDS can not be shut down.

Our decision is made on the basis of [Magento on the AWS Cloud: Quick Start Reference Deployment](http://docs.aws.amazon.com/quickstart/latest/magento/welcome.html)

We have reworked Cloudformation templates from this manual to correct some flaws.

### Before

- depending on the region setup will create 2 or 3 availability zones with EC2 NAT in each. If your setup small, it's not interesting to pay for unnecessary resources.
- only two zones included in balance even when the three is created.
- used EC2 NATs
- only RDS option exists

### After

- now You can select the exact number of available zones (up to 5), which will be used for setup. If there is less AZ in region than You choose, the greatest possible number will be created.
- all created AZ will be included in the balancing
- EC2 NATs replaced by NAT Gateways and added Bastion (as the entry point)
  (In order to save is likely to make a version of a NAT type of choice. Can later)
- added option to EC2 Mysql

## How to use:

- Download repo

```clojure
git clone git@github.com:epxlabs/magento-in-aws.git
cd magento-in-aws
```

If You want to take all stuff to your AWS account:

- Create s3 bucket for templates

```clojure
aws s3 mb s3://<mybucket> --region <region_if_needed>
# eg.aws s3 mb s3://epxlabs-magento-templates --region us-west-2
```

- Upload all templates to our s3 bucket

```clojure
aws s3 sync . s3://epxlabs-magento-templates --exclude '.git/*' --acl public-read --delete --region us-west-2
```

- Change default value for TemplatesBucket parameter in master.template, or override it while deploy new stack.

- Download Magento and Magento Sample Data (probably You'll need to register at https://magento.com/)

- Create s3 bucket for them

- Upload them there

- Change default values for MagentoReleaseMedia and MagentoSampleData parameters in master.template or override them while deploy new stack.

- Create new stack using Cloudformation in AWS Console or using AWS CLI.

- Any questions, issues, propositions please send to alex@epxlabs.com

### Hint: How to figureout real count of AZ in regions

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
