Mini Clojure Web App
----
I wrote (combined a bunch of many people's efforts) this mini web app server to provide a basic template for future  projects that might need to communicate over http. Based on [http-kit](http-kit.org), a super fast, lightweight async webserver. Cloj has ClojureScript WebSockets and Ajax, and functionality to talk to a Redis database. The html templating and css are all written in Clojure. 

#### Next Steps
* Implementing Friend or Sandbar and having User authentication.
* Communicating to MySql database - Might as well - maybe to save User info.


#### Up and Running

Install [Leiningen](http://leiningen.org/#install). The easiest way is with Homebrew on mac. Goodluck on Windows...

Then, from the project directory:

> lein deps
>
> lein cljsbuild once
>
> lein run

Server should be running @ http://localhost:1337

My blog post on [core.async and WebSockets](https://duanebester.telegr.am/blog_posts/clojurescript_core_async) for this project.


## License

The MIT License (MIT)

Copyright (c) 2013 Duane Bester

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
