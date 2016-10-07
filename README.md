# site-generator

A Clojure library designed to ... well, that part is up to you.

## Usage

FIXME

## Notes on Enlive

Snippets are the parts of your page. Snippets can contain other snippets.

For a static website templates define each page. They contain snippets. A template itself is basically a snippet that serializes its output. So it will return a `seq` of strings.

In larger applications templates need not define a full page and can be a part of a page.

Both snippets and templates require an HTML file on the classpath that they can operate on.

Selectors are like CSS selectors and allow you to specify the part of the HTML that you want to transform. Transformations are the functions to transform the HTML selected by the selectors.


A selector is a vector of predicates. For a logical AND within a selector put the predicates to be AND'ed into a subvector. 

So [:p (attr? :lang)] is going to match any elements with a lang attribute inside a :p element. On the other hand, [[:p (attr? :lang)]] is going to match any p with a lang attribute.

On the other hand sets are a logical OR. So [#{:div.class1 :div.class2}] match every div which has either class1 or class2. This can alternatively be written as [[:div #{:.class1 .class2}]]. Indeed you can have nested "ors" and "ands" which means nested sets and vectors.

## Deployment

To deploy to the staging site, use the following steps: 
1) In emacs, navigate to src/site-generator/core.clj
2) Use C-c M-j to boot a repl
3) Use C-c M-n to switch to the namespace of the core file
4) Use C-c C-k to compile
5) Run the export function in your repl
6) In your terminal, run "./deploy.sh staging"

This will deploy to staging.epxlabs.com

## License

Copyright © 2016 EPX Labs, Inc.
