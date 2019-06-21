# district0x/async-helpers

[![Build Status](https://travis-ci.org/district0x/async-helpers.svg?branch=master)](https://travis-ci.org/district0x/async-helpers)

Clojurescript library with functions aiding in handling asynchronous events.
This library currently utilises [timbre](https://github.com/ptaoussanis/timbre) as a logging library.
You shoud use it in conjunction with the [district-server-logging](https://github.com/district0x/district-server-logging) and [district-ui-logging](https://github.com/district0x/district-ui-logging) modules which setup various logging appenders.

## Installation
Add into your project.clj: <br>
[![Clojars Project](https://img.shields.io/clojars/v/district0x/async-helpers.svg)](https://clojars.org/district0x/async-helpers)

Include `[district.shared.async-helpers]` in your CLJS file. <br>
<br>

## district.shared.async-helpers
**`promise->`** <br>
* Takes a JS Promise as a first argument and a sequence of function calls to be chained. Takes care of error handling. 

Example:

```clojure
(promise-> (js/Promise.resolve :one)
           #(prn "RESOLVED %)
           #(js/Promise.resolve :two)
           #(prn "RESOLVED %))
```

For rationale and more use-cases see this [blog post](https://www.blog.nodrama.io/clojurescript-chaining-js-promises-previous-value/).

**`promise->chan`**
* Takes a JS Promise as its argument and returns a `core.async` [channel](https://clojuredocs.org/clojure.core.async/chan) with the value it resolves to.

**`promise?`**
* Predicate function, returns true if its argument is an instance of a JS Promise.

**`defer`**
* Macro which takes a body and executes it in the next iteration of the nodejs Event Loop.

## Library dependencies

### [district-server-logging](https://github.com/district0x/district-server-logging)

`error-handling` requires a configured `district-server-logging` and/or `district-ui-logging` modules to function correctly.
Depending on where the code is run and how the logging is configured your errors will be reported to the browser, node.js console, log file, [sentry](https://sentry.io) etc. <br>

For possible configuration options please see [district-server-logging](https://github.com/district0x/district-server-logging) and [district-ui-logging](https://github.com/district0x/district-ui-logging).
