
# Getting Started with [stripe-clojure](https://github.com/prachetasp/stripe-clojure)

## Introduction

Stripe is a leading provider of payment and subscription management solutions for businesses around the world. The most notable feature of Stripe is their nearly immaculate implementation of a REST API. Developers can simply access the API or build applications that leverage the API to automate away business pains such as recurring customer billing (subscriptions) or manipulating payment information.

[stripe-clojure](https://github.com/prachetasp/stripe-clojure) is a Clojure library which is designed to implement the Stripe API in a functional manner. The library handles API calls to Stripe which allows developers to interact with the API in a clean and simple to use functional environment. This article will cover the basics of the [stripe-clojure](https://github.com/prachetasp/stripe-clojure) library along with a few examples to get help you get started accepting Stripe payments. The tutorial will be levarage emacs-live with a cider REPL (EPX Labs will soon be open sourcing our emacs-live + add-ons development environment affectionately named the "labkit").

[stripe-clojure](https://github.com/prachetasp/stripe-clojure) was developed with a focus on slim dependencies. This makes [stripe-clojure](https://github.com/prachetasp/stripe-clojure) ideal for use on *Serverless*, AWS Lambda, IBM OpenWhisk, etc. systems.

*Let's dive in..*

## Stripe API Basics

*********************

### The Customer object

The "Customer object" is the primitive of every transaction that occurs through Stripe. Customer "objects" contain all of the information relevant to a customer and are required for charges and subscriptions. The [stripe-clojure](https://github.com/prachetasp/stripe-clojure) library uses a functional implementation of the customer object. Namely, an edn map:

```clojure
{:default_source "card_198YCVJ3Zx6FNnHuyeItfoJu",
 :description nil,
 :email "stripe-test-man@test.com",
 :delinquent false,
 :account_balance 0,
 :sources
 {:object "list",
  :data
  [{:address_line1_check nil,
    :address_state nil,
    :dynamic_last4 nil,
    :address_zip_check nil,
    :tokenization_method nil,
    :exp_year 2017,
    :name nil,
    :cvc_check "pass",
    :last4 "4242",
    :brand "Visa",
    :customer "cus_9RQwyEK0fHnSkO",
    :address_country nil,
    :funding "credit",
    :address_line2 nil,
    :id "card_198YCVJ3Zx6FNnHuyeItfoJu",
    :address_zip nil,
    :address_line1 nil,
    :exp_month 12,
    :country "US",
    :metadata {},
    :object "card",
    :fingerprint "mRNahc5gc8EgfkI5",
    :address_city nil}],
  :has_more false,
  :total_count 1,
  :url "/v1/customers/cus_9RQwyEK0fHnSkO/sources"},
 :created 1477414020,
 :subscriptions
 {:object "list",
  :data
  [{:canceled_at nil,
    :application_fee_percent nil,
    :start 1477453490,
    :created 1477453490,
    :current_period_end 1480131890,
    :trial_end nil,
    :customer "cus_9RQwyEK0fHnSkO",
    :ended_at nil,
    :status "active",
    :id "sub_9RbY6wnlhYeBdZ",
    :cancel_at_period_end false,
    :livemode false,
    :quantity 1,
    :trial_start nil,
    :discount nil,
    :plan
    {:amount 999,
     :name "stripe-clojure",
     :created 1477453200,
     :currency "usd",
     :interval_count 1,
     :id "stripe-clojure",
     :trial_period_days nil,
     :interval "month",
     :livemode false,
     :metadata {},
     :object "plan",
     :statement_descriptor nil},
    :current_period_start 1477453490,
    :metadata {},
    :object "subscription",
    :tax_percent nil}],
  :has_more false,
  :total_count 1,
  :url "/v1/customers/cus_9RQwyEK0fHnSkO/subscriptions"},
 :currency "usd",
 :id "cus_9RQwyEK0fHnSkO",
 :livemode false,
 :shipping nil,
 :discount nil,
 :metadata {},
 :object "customer"}
```
*********************

### Subscriptions

Subscriptions are used to bill customers a set amount every month. 
Subscriptions are optional. A customer may have multiple concurrent subscriptions.
Here is the subscription block from the a customer object map:

```clojure
 :subscriptions
 {:object "list",
  :data
  [{:canceled_at nil,
    :application_fee_percent nil,
    :start 1477453490,
    :created 1477453490,
    :current_period_end 1480131890,
    :trial_end nil,
    :customer "cus_9RQwyEK0fHnSkO",
    :ended_at nil,
    :status "active",
    :id "sub_9RbY6wnlhYeBdZ",
    :cancel_at_period_end false,
    :livemode false,
    :quantity 1,
    :trial_start nil,
    :discount nil,
    :plan
    {:amount 999,
     :name "stripe-clojure",
     :created 1477453200,
     :currency "usd",
     :interval_count 1,
     :id "stripe-clojure",
     :trial_period_days nil,
     :interval "month",
     :livemode false,
     :metadata {},
     :object "plan",
     :statement_descriptor nil},
    :current_period_start 1477453490,
    :metadata {},
    :object "subscription",
    :tax_percent nil}],
  :has_more false,
  :total_count 1,
  :url "/v1/customers/cus_9RQwyEK0fHnSkO/subscriptions"}
```

*********************

### Plans

Plans are the implementation of your business logic. You set up plans to let Stripe know exactly how, when, why, who, what you want to bill.


*********************

### Sources

Customer payment information is stored securely by Stripe as a source. When creating a source for a customer it is not necessary to hold any payment details thereby removing PCI compliance concerns. You must communicate with Stripe using tokenized payment details so sensitive information nevertraverses your infrastructure.

Here is an example of a credit card source (remember, sources can be either credit cards or bank accounts):

```clojure
 :sources
 {:object "list",
  :data
  [{:address_line1_check nil,
    :address_state nil,
    :dynamic_last4 nil,
    :address_zip_check nil,
    :tokenization_method nil,
    :exp_year 2017,
    :name nil,
    :cvc_check "pass",
    :last4 "4242",
    :brand "Visa",
    :customer "cus_9RQwyEK0fHnSkO",
    :address_country nil,
    :funding "credit",
    :address_line2 nil,
    :id "card_198YCVJ3Zx6FNnHuyeItfoJu",
    :address_zip nil,
    :address_line1 nil,
    :exp_month 12,
    :country "US",
    :metadata {},
    :object "card",
    :fingerprint "mRNahc5gc8EgfkI5",
    :address_city nil}],
  :has_more false,
  :total_count 1,
  :url "/v1/customers/cus_9RQwyEK0fHnSkO/sources"}
```

*********************

## Getting started with the library

Today we will show the basic process of creating a test customer using the [stripe-clojure](https://github.com/prachetasp/stripe-clojure) library. We will also run through the process of creating a subscription and processing payments. When testing the library it is important to use the Test API key so that no real payments are processed. 

*********************

### Preparing to communicate with the Stripe API

In order to make changes to your Stripe account using the API you must have permission. Stripe has different keys that entitle different permissions. Your API keys can be found on the Stripe dashboard under account settings. For this example we will be using the "private test key".

The API key can be added simply by calling the `set-tokens!` function with a map of your secret test key as the parameter.

```clojure
(set-tokens! {:private "sk_test_xxxxxxxxxxxxxxxx"})
```

*********************

### Adding a customer to Stripe

The [stripe-clojure](https://github.com/prachetasp/stripe-clojure) library makes it simple to add a customer to Stripe. Customers can be created with many different types of data however we will be initializing our test customer with only an email. The customer is created using a POST request which can be sent via the `create` function. The `create` function is a generic HTTP POST request that can be used to create anything from customers to subscriptions and plans. The `create` function accepts a map with a resource (`:customers`, `:subscriptions`, etc.) and a map of parameters related to that resource. In this case (creating a customer) our request will look like:

```clojure
(create {:customers {:email "stripe-test-man@test.com"}})
```

If the previous step was done correctly, Stripe should return a customer map:

```clojure
{:default_source nil,
 :description nil,
 :email "stripe-test-man@test.com",
 :delinquent false,
 :account_balance 0,
 :sources
 {:object "list",
  :data [],
  :has_more false,
  :total_count 0,
  :url "/v1/customers/cus_9RQwyEK0fHnSkO/sources"},
 :created 1477414020,
 :subscriptions
 {:object "list",
  :data [],
  :has_more false,
  :total_count 0,
  :url "/v1/customers/cus_9RQwyEK0fHnSkO/subscriptions"},
 :currency nil,
 :id "cus_9RQwyEK0fHnSkO",
 :livemode false,
 :shipping nil,
 :discount nil,
 :metadata {},
 :object "customer"}
```

*note: The parameter map may contain any fields that stripe allows for that given resource, for example for a list of fields available on the customer resource [click here](https://stripe.com/docs/api)

*********************

### Adding a default payment source

Payment sources should be added using a tokenized form of the payment details. In this example we will create a test token using Stripe's token generator. A token simply represents a single source (credit cards or bank accounts) and can only be associated with one customer. In order to add the token to the customer we use the same create function we used previously. In this case the route will be the same however we will include an additional key value pair, `:source`. We also need to include the Stripe customer ID of the customer we want to add the source to. 

Create function:

```clojure
(create {:customers {:customer_id "cus_9RQwyEK0fHnSkO" :source "tok_198YCVJ3Zx6FNnHuZAMlkkX6"}})
```

*********************

### Adding a Plan 

A plan is required for subscription billing.
You may create a plan for your customers using the `create` function.
Let's make some plans (id's need to be unique across all plans):

*Gold Plan*

```clojure
(create {:plans {:id "gold" :amount 9999 :currency "usd" :interval "month" :name "Gold Plan"}})
```

*Pink Plan*

```clojure
(create {:plans {:id "pink" :amount 15999 :currency "usd" :interval "month" :name "Pink Plan"}})
```

*stripe-clojure*

```clojure
(create {:plans {:id "stripe-clojure" :amount 999 :currency "usd" :interval "month" :name "Stripe Clojure"}})
```

*********************

### Starting a Subscription

The previous three steps are necessary to begin charging a customer on a monthly basis. 
For our purposes we are trying to subscribe a customer to a plan named "stripe-clojure" that charges $9.99 and bills monthly. 
Let's create a customer and assign them to the stripe-clojure plan:

```clojure
(create {:customers {:customer_id "cus_9RQwyEK0fHnSkO" :plan "stripe-clojure"}})
```
This returns a success response with the updated customer object to show that the request worked.

```clojure
{:default_source "card_198YCVJ3Zx6FNnHuyeItfoJu",
 :description nil,
 :email "stripe-test-man@test.com",
 :delinquent false,
 :account_balance 0,
 :sources
 {:object "list",
  :data
  [{:address_line1_check nil,
    :address_state nil,
    :dynamic_last4 nil,
    :address_zip_check nil,
    :tokenization_method nil,
    :exp_year 2017,
    :name nil,
    :cvc_check "pass",
    :last4 "4242",
    :brand "Visa",
    :customer "cus_9RQwyEK0fHnSkO",
    :address_country nil,
    :funding "credit",
    :address_line2 nil,
    :id "card_198YCVJ3Zx6FNnHuyeItfoJu",
    :address_zip nil,
    :address_line1 nil,
    :exp_month 12,
    :country "US",
    :metadata {},
    :object "card",
    :fingerprint "mRNahc5gc8EgfkI5",
    :address_city nil}],
  :has_more false,
  :total_count 1,
  :url "/v1/customers/cus_9RQwyEK0fHnSkO/sources"},
 :created 1477414020,
 :subscriptions
 {:object "list",
  :data
  [{:canceled_at nil,
    :application_fee_percent nil,
    :start 1477453490,
    :created 1477453490,
    :current_period_end 1480131890,
    :trial_end nil,
    :customer "cus_9RQwyEK0fHnSkO",
    :ended_at nil,
    :status "active",
    :id "sub_9RbY6wnlhYeBdZ",
    :cancel_at_period_end false,
    :livemode false,
    :quantity 1,
    :trial_start nil,
    :discount nil,
    :plan
    {:amount 999,
     :name "stripe-clojure",
     :created 1477453200,
     :currency "usd",
     :interval_count 1,
     :id "stripe-clojure",
     :trial_period_days nil,
     :interval "month",
     :livemode false,
     :metadata {},
     :object "plan",
     :statement_descriptor nil},
    :current_period_start 1477453490,
    :metadata {},
    :object "subscription",
    :tax_percent nil}],
  :has_more false,
  :total_count 1,
  :url "/v1/customers/cus_9RQwyEK0fHnSkO/subscriptions"},
 :currency "usd",
 :id "cus_9RQwyEK0fHnSkO",
 :livemode false,
 :shipping nil,
 :discount nil,
 :metadata {},
 :object "customer"}
```

*********************

### Taking it a step further

The [stripe-clojure](https://github.com/prachetasp/stripe-clojure) library lays the foundation for a Stripe disruption in a functional environment. It contains all of the tools needed to build complex payment and customer management systems and is a great fit for building a serverless solution to payment processing. If you would like to know more about going Serverless contact EPX Labs to find out how you could improve application reliability while cutting costs. #webscale!

*********************


