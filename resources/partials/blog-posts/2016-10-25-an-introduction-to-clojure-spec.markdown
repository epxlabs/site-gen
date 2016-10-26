# An Introduction to Clojure.Spec
##### By Alex Martin

[Clojure.spec](http://clojure.org/about/spec) is a great tool for adding some tests and validation to your clojure project. I recently used it in a project that required validating data as it came into the database. 

To begin, you obviously require clojure.spec in one of your clj files.

```clojure
(ns your-file.core
    (:require [clojure.spec :as s])
```

You now have access to all the methods that this library provides to you.
To begin with, you need to defining specs by associating them with a keyword and a validation, like so:

```clojure
(s/def ::first-name string?)

```

Many times, you will want a more specific validation, such as one that requires regex. You'd do this by first defining the regex separately, and then calling it using an anonymous function as the second argument in a spec definition.

```clojure
(def id-regex #"^\d{6}$")
(s/def ::id-type #(re-matches id-regex %))
```

What happens if you want multiple validations for one spec? You can use the **and** function provided by Clojure.spec.

```clojure
(def time-regex #"^([0-9]|1[012]):[0-5][0-9] ?((a|p)m|(A|P)M)$")
(s/def ::time-type (s/and string? #(re-matches time-regex %)))
```

This spec will only return true if both validations are required. Of course, **or** can be used if you only need for one of several validations to true.

```clojure
(s/def ::day (s/or "1" "2" "3" "4" "5" "6" "7"))
```

Another, cleaner way of writing the above spec would be to pass in a set as the second argument. The spec would return true if the entry were included in the set, and false otherwise.

```clojure
(s/def ::day #{"1" "2" "3" "4" "5" "6" "7"})
```

You have several options for methods to test whether an entry would pass a validation or not. The best is **valid?**, which will return true or false depending on whether the entry passes.

```clojure
(s/def ::first-name string?)
(s/valid? ::first-name "Alex")
=> true
(s/valid? ::first-name 3)
=> false
```

Another option is **conform**, which will return the entry itself if it meets the validation, and false otherwise.

```clojure
(s/def ::day #{"1" "2" "3" "4" "5" "6" "7"})
(s/conform ::day "1")
=> "1"
(s/conform ::day "Monday")
=> false
```
