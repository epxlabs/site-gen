# Node 7 async/await and promises from scratch

Node 7 has been released and, with it, support for async/await.  [The support is behind the --harmony flag](http://node.green), so to run this example we'll use :

```javascript
node --harmony index
```

Async/await and promises allow you to write clean asynchronous code with minimal effort.  There are potential pitfalls but, ultimately, using promises and async/await for managing concurrency leads to terse, readable code.

Let's begin by showing how vanilla node callbacks can be transformed to be promise and async/await friendly.

We'll construct a promise from the [`request`](https://www.npmjs.com/package/request) NPM library.  Let's write a function that takes a URL, downloads a web page, and returns the body (or error) in a promise.

```javascript
const request = require('request');
const requestAsync = url => {
    return new Promise((resolve, reject) => {
        request(url, (error, response, body) => {
            if (!error && response.statusCode == 200) {
                resolve(body);
            }else{
                reject(error);
            }
        });
    });
};
```

Promises are constructed from a function that takes a `resolve` and `reject` parameter.  The only stipulation is that the function must *eventually* call resolve or reject.

Let's try calling this function.  By awaiting it, we'll unwrap the value of the body from the promise.

```javascript
(async function() {
    let response = await requestAsync("http://epxlabs.com");
    console.log("Page length of EPX Labs", response.length);
}());
```

And that's it!  The value proposition of async/await might not yet be clear.  We'll cover more of that in subsequent posts when we go over things like promisification, exception handling, performance gotchas, and running multiple promises concurrently.
