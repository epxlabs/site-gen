# An Introduction to Clojure.Spec
##### By Alex Martin

[Clojure.spec](http://clojure.org/about/spec) is a great tool for adding some tests and validation to your clojure project. 

To begin require clojure.spec in your clj namespace.

```clojure
(ns your-file.core
    (:require [clojure.spec :as s])
```

To begin let's define specs by associating them with a keyword and a validation, like so:

```clojure
(s/def ::first-name string?)

```

Many times, you will want a more specific validation such as one that requires regex. You can accomplish this (non-clojure best practices) by first defining the regex separately and then calling it using an anonymous function as the second argument in a spec definition.

```clojure
(def id-regex #"^\d{6}$")
(s/def ::id-type #(re-matches id-regex %))
```

What happens if you want multiple validations for one spec? You can use the `and` function provided by Clojure.spec.

```clojure
(def time-regex #"^([0-9]|1[012]):[0-5][0-9] ?((a|p)m|(A|P)M)$")
(s/def ::time-type (s/and string? #(re-matches time-regex %)))
```

This spec will only return true if both validations are required. Of course, `or` can be used if you only require one of several validations to be true.

```clojure
(s/def ::day (s/or "1" "2" "3" "4" "5" "6" "7"))
```

Another, cleaner way of writing the above spec would be to pass in a set as the second argument. The spec would return true if the entry was included in the set, and false otherwise.

```clojure
(s/def ::day #{"1" "2" "3" "4" "5" "6" "7"})
```

You have several options for testing whether an  entry passes validation. The best is `valid?`, which returns true or false.

```clojure
(s/def ::first-name string?)
(s/valid? ::first-name "Alex")
=> true
(s/valid? ::first-name 3)
=> false
```

Another option is `conform`, which returns the validated data if true and false otherwise.

```clojure
(s/def ::day #{"1" "2" "3" "4" "5" "6" "7"})
(s/conform ::day "1")
=> "1"
(s/conform ::day "Monday")
=> false
```

Once you've defined a set of specs that you want to use to test a given object you then can create an **entity map** for that object. We use the `keys` method to define all specs that the data should be validated for. You may specify which ones are optional or required with `:req` and `:opt`.

```clojure
(s/def ::person (s/keys :req [::first-name ::last-name ::email]
                        :opt [::phone]))
```

You can also pass maps to either `conform` or `valid?` to find out whether or not your data is valid. It's that easy!

```clojure
(s/valid? ::person (::first-name "Alex" ::last-name "Martin" ::email "amartin@epxlabs.com")
=> true
(s/conform ::person (::first-name "Alex" ::email "amartin@epxlabs.com")
=> false
```

