# The Serverless Framework: Getting Setup
##### By Alex Martin

Today, I'm going to walk you through a great demo to help you understand the basic setup of a serverless project using the serverless framework. Special thanks to [this repo](https://github.com/serverless/serverless)  for being such a great resource.

First, a brief introduction to the four foundational parts of the serverless framework as of 1.0:

**Functions** are independent units of employment, code used to perform individual tasks like saving a user to the database or processing a file.

**Events** are anything that triggers a function to execute, such as an HTTP endpoint, an S3 bucket upload, et cetera.

**Resources** are components used by functions, such as dynamoDB table, an s3 bucket, etc.

**Services** are the units of organization, e.g. a project file; this is what gets deployed when you run `serverless deploy`.

Now let's get to the demo!

1. Make sure you have Node version 4 or higher installed by running `node -v`

2. Install serverless by running `npm install serverless -g`

3. Make a directory for this project and `cd` into it

4. Create the serverless frameworking using `serverless create -t aws-nodejs`. This will generate three files: 
    * **handler.js** - Contains the actual code of your functions.
    * **event.json** - Contains the test data with which to trigger functions.
    * **serverless.yml** - Defines your project's functions and the events that trigger them. This is also where resources such as AWS DynamoDB tables and S3 buckets can be defined.
5. Add an HTTP endpoint to your **serverless.yml** file like such:

```clojure
functions:
    hello:
        handler: handler.hello
        events: 
            - http:
                path: hello
                method: get
```

6. Create an AWS account if you don't already have one.
7. Go to the IAM page. Click on Users and then Create New Users.
![image-1]~*serverless-demo-1.png*~ 
8. Enter a name in the first field to remind you that this user is the framework (something like "serverless-demo"), and click Create.
9. Copy and store the API key and secret somewhere.
10. In the users tab, click on your user. Then look for Managed Policies and click Attach Policy.
![image-2]~*serverless-demo-2.png*~ 
11. In the next screen, search for AdministratorAccess and click Attach.
12. Run `aws configure` in your terminal to configure your AWS credentials.
13. Finally, run `serverless deploy`!

This should return an API endpoint that points to your lambda function. Copy and paste it into your browser and check it out!
