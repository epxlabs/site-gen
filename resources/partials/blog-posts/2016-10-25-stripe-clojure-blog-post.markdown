
# Getting Started with Stripe-Clojure

## Introduction

Stripe is a leading provider of payment and subscription management solutions for many businesses. The most notable feature of Stripe is their nearly immaculate implementation of a REST API. Developers can create applications to automate a lot of interactions with Stripe such as charging a customer or adding payment information. 

Stripe-Clojure is a Clojure development library which is streamlined for easy functional implementation of the Stripe API. The library handles API calls to Stripe which allows developers to easily interact with the API in a clean, easy to use functional environment. This article will cover the basics of the Stripe-Clojure library along with a few examples to get started accepting payments. The tutorial will be covered using emacs with a cider REPL.

## Stripe API Basics

### The Customer Object

The customer object is the core of every operation available through Stripe. Customer objects contain all of the information relevant to a customer and are required for charges and subscriptions. Each customer has a unique ID which is used for payments or to start a subscription.

### Subscriptions

Subscriptions are used to bill customers a set price every month. A Stripe account can have many different plans available and customers may have one or many different subscriptions. 

### Sources

Customer payment information is stored securely in Stripe's database as a source. When creating a source for a customer it is not necessary to hold any payment details which removes a potential security risk. It is also a good practice to communicate with Stripe using tokenized information. 

## Getting started with the library

Today we will show the basic process of creating a test customer using the Stripe-Clojure library. We will also run through the process of creating a subscription and processing payments. When testing the library it is important to use the "test" key so that no real payments are made. 

### Preparing to communicate with Stripe API

In order to make changes to your Stripe account using the API you must have permission. Stripe has different keys that entitle different permissions. Your API keys can be found on the Stripe dashboard under account settings. Today, we will be using the private test key.
 
Once the repl has been loaded, switch into the Stripe-Clojure namespaces. The key can be added simply by using the ```set-tokens!``  command with a map of your secret test key as the parameter.
```(set-tokens! {:private "sk_test_xxxxxxxxxxxxxxxx"})``

### Adding a customer to Stripe

The Stripe-Clojure library makes it simple to add a customer to Stripe. Customers can be created with many different types of data however we will be initializing our test customer with only an email. The customer is created using a POST request which can be done through the ```create`` command. The ```create`` command accepts a map with a route and also a map of parameters related to the object. In this case our request will look like:
```(create {:Customers {:email "stripe-test-man@test.com"}})"``

If the previous step was done correctly, Stripe should return a customer map:
```{:default_source nil, :description nil, :email "stripe-test-man@test.com", :delinquent false, :account_balance 0, :sources {:object "list", :data [], :has_more false, :total_count 0, :url "/v1/customers/cus_9RQwyEK0fHnSkO/sources"}, :created 1477414020, :subscriptions {:object "list", :data [], :has_more false, :total_count 0, :url "/v1/customers/cus_9RQwyEK0fHnSkO/subscriptions"}, :currency nil, :id "cus_9RQwyEK0fHnSkO", :livemode false, :shipping nil, :discount nil, :metadata {}, :object "customer"}``

In other cases, different parameters can be used with the parameter map for example a default source could be added on customer creation but that will be done in the next step.

### Adding a default payment source
Payment sources can be added using either a map of credit card details or a tokenized form of the details. In this example we will create a test token using Stripe's token generator. A token simply represents a single credit card and can only be used on one customer. In order to add the token to the customer, we can use the same command as we did previously. In the case the route will be the same however we will just include a different parameter ```:source``. We also need to include the Stripe customer ID of the customer we want to add the source to. 
```(create {:customers {:customer_id "cus_9RQwyEK0fHnSkO" :source "tok_198YCVJ3Zx6FNnHuZAMlkkX6"}})``

### Starting a Subscriptions
The previous two steps were necessary to begin charging customers on a monthly basis. 
