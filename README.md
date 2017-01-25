# site-generator

A Clojure library designed to ... well, that part is up to you.

## Run Locally

You must generate a `src/site_generator/env.clj` file and insert your AWS credentials into it (this allows for S3 image upload to work). The file should look like the following:

```clojure
(ns site-generator.env)

(def cred {:access-key "YOUR AWS ACCESS KEY", :secret-key "YOUR AWS SECRET KEY"})
```
Then in your terminal, run:
`cd frontend`
` bin/set_env.sh [STAGE]` (Either dev, staging, or production)
`lein ring server-headless`

The site can be reached in your browser at http://localhost:3000/

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

Deployment is very simple. We use the `bin/deploy.sh` script:

```
bin/deploy.sh {STAGE}
```

e.g. `bin/deploy.sh staging`

where STAGE is one of production or staging.

`staging` deploys to `staging.epxlabs.com`
`production` deploys to `www.epxlabs.com`

What happens when we run `bin/deploy.sh`?

1. Verify the stage is one of `staging` or `production`
2. Switch the correct stage config to `src/site_generator/active_config.clj`
3. Build the site and export to `resources/exported_site` with `lein export-site`
4. Upload the exported site and related assets to the corresponding website S3 bucket

What happens when we run `lein export-site`?

1. Leiningen runs `lein run -m site-generator.core/export`
2. `export` hydrates templates and exports the site and bundled assets to `resources/exported_site`

Super manual deploy (not recommended):

1. In emacs, navigate to `src/site-generator/core.clj`
2. Use `C-c M-j` to boot a repl
3. Use `C-c M-n` to switch to the namespace of the core file
4. Use `C-c C-k` to compile
5. Run the `(export)` in your repl
6. In your terminal, run `./deploy.sh {STAGE}` (note this will basically repeat the above 5 steps)

This will deploy to staging.epxlabs.com

## Editing HTML

To edit HTML files and see the changes immediately without rebooting your local server, navigate to `src/site-generator/snippets.clj` and use `M-x touch`

## Adding a Blog Post

Blog posts should be in markdown format and should be located in `resources/partials/blog-posts`. They should use the format `YEAR-MONTH-DATE-blog-title.markdown`.


Additionally, you must add your blog_post to the blogs vector in `src/site_generator/snippets.clj` using the following format:

```clojure
{:title "Your title"
 :date "Today's date"
 :author "Your name"
 :file-path "resources/partials/blog-posts/your-blog.markdown"
 :display-image "your-image.jpg"}
```

To add images to your blog post, place them in `resources/public/blog_images` and use the following markdown syntax:
`![ALT-TEXT]~*FILE NAME*~`

Alt text should be whatever you want the name of the image to be when it is hovered over, and file name should be the exact name of the image file.

NOTE: Both file names in the blog and in the display-image key in the map should just be the name of the file itself without the path. So, "your-image.jpg" instead of "resources/public/blog_images/your_image.jpg".

## License

Copyright © 2016 EPX Labs, Inc.
