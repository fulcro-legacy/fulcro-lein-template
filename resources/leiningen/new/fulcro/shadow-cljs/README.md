# The Project

The main project source is in `src/main`.

```
.
├── Makefile           ; i18n extract/generate and CI test running
├── i18n               ; directory for i18n build/extract/translate
│   ├── es.po          ; spanish translations
│   └── messages.pot   ; extracted strings (template)
├── karma.conf.js      ; CI Runner config
├── package.json       ; NPM modules
├── project.clj        ; Leiningen project file
├── resources
│   └── public
│       ├── cards.html    ; page for mounting dev cards
│       ├── index.html    ; main app index page
│       └── js
│           └── test
│               └── index.html ; custom test page for running tests in dev mode
├── shadow-cljs.edn    ; Shadow-cljs configuration file. CLJS builds.
└── src
    ├── cards
    │   └── {{sanitized}}
    │       ├── cards.cljs   ; Main for devcards
    │       └── intro.cljs   ; A sample devcards file
    ├── dev
    │   └── user.clj         ; Functions for running web server in development mode
    ├── main
    │   ├── config           ; configuration files for web server
    │   │   ├── defaults.edn
    │   │   ├── dev.edn
    │   │   └── prod.edn
    │   ├── {{sanitized}}
    │   │   ├── api
    │   │   │   ├── mutations.clj          ; server-side implementation of mutations
    │   │   │   ├── mutations.cljs         ; client-side implementation of mutations
    │   │   │   └── read.clj               ; server-side reads
    │   │   ├── client.cljs                ; file that creates the Fulcro client
    │   │   ├── development-preload.cljs   ; code to run in development mode before anything else
    │   │   ├── server.clj                 ; file that creates the web server
    │   │   ├── server_main.clj            ; production server entry point
    │   │   └── ui
    │   │       ├── components.cljc  ; Sample reusable component
    │   │       └── root.cljc        ; Main UI
    │   └── translations
    │       └── es.cljc              ; Generated cljs for es translations (see Makefile)
    └── test
        └── {{sanitized}}
            ├── client_test_main.cljs  ; setup for dev mode tests
            └── sample_spec.cljc       ; a sample spec in fulcro-spec
```

## Setting Up

The shadow-cljs compiler uses all cljsjs and NPM js dependencies through
NPM. If you use a library that is in cljsjs you will also have to add
it to your `package.json`.

You also cannot compile this project until you install the ones it
depends on already:

```
$ npm install
```

or if you prefer `yarn`:

```
$ yarn install
```

Adding NPM Javascript libraries is as simple as adding them to your
`package.json` file and requiring them! See the
[the Shadow-cljs User's Guide](https://shadow-cljs.github.io/docs/UsersGuide.html#_javascript)
for more information.

## Development Mode

Shadow-cljs handles the client-side development build. The file
`src/main/{{sanitized}}/client.cljs` contains the code to start and refresh
the client for hot code reload.

Running all client development builds:

```
$ npx shadow-cljs watch main cards test
...
shadow-cljs - HTTP server for ":main" available at http://localhost:8020
shadow-cljs - HTTP server for ":test" available at http://localhost:8022
shadow-cljs - HTTP server for ":cards" available at http://localhost:8023
...
```

The compiler will detect which builds are affected by a change and will minimize
incremental build time.

NOTE: The server wil start a web server for all three builds (on different ports).
You typically do not need the one for main because you'll be running your
own server, but it is there in case you are only going to be writing
a client-side app that has no server API.

The URLs for working with cards and tests are:

- Cards: [http://localhost:8023/cards.html](http://localhost:8023/cards.html)
- Tests: [http://localhost:8022/index.html](http://localhost:8022/index.html)
- Main: [http://localhost:8020/index.html](http://localhost:8020/index.html) (NO API SERVER)

See the server section below for working on the full-stack app itself.

### Client REPL

The shadow-cljs compiler starts an nREPL. It is configured to start on
port 9000 (in `shadow-cljs.edn`).

In IntelliJ, simply add a *remote* Clojure REPL configuration with
host `localhost` and port `9000`.

If you're using CIDER
see [the Shadow-cljs User's Guide](https://shadow-cljs.github.io/docs/UsersGuide.html#_cider)
for more information.

### The API Server

The shadow-cljs compiler starts a server for serving development files,
but you usually will not use it. Instead you'll start your own server
that can also serve your application's API.

Start a clj REPL in IntelliJ, or from the command line:

```bash
$ lein repl
user=> (go)
...
user=> (restart) ; stop, reload server code, and go again
user=> (tools-ns/refresh) ; retry code reload if hot server reload fails
```

The URL to work on your application is then
[http://localhost:3000](http://localhost:3000).

Hot code reload, preloads, and such are all coded into the javascript,
so serving the files from the alternate server is fine.

### Preloads

There is a preload file that is used on the development build of the
application `{{name}}.development-preload`. You can add code here that
you want to execute before the application initializes in development
mode.

### Fulcro Inspect

The Fulcro inspect will preload on the development build of the main
application and cards. You can activate it by pressing CTRL-F while in
the application. If you need a different keyboard shortcut (e.g. for
Windows) see the docs on github.

## Tests

Tests are in `src/test`

```
src/test
└── {{sanitized}}
    ├── client_test_main.cljs     entry point for dev-mode client tests
    └── sample_spec.cljs          spec runnable by client and server.
```

### Server tests:

Interacting with tests resuts via a browser (also allows test focusing, etc):

From a CLJ REPL:

```
user=> (start-server-tests) ; start a server on port 8888 showing the server tests
```

then navigate to [http://localhost:8888/fulcro-spec-server-tests.html](http://localhost:8888/fulcro-spec-server-tests.html)

If you'd instead like to see them pop up over and over again in a terminal:

```
lein test-refresh
```

### CI Tests

Use the Makefile target `tests`:

```
make test
```

You must have `npm` and Chrome installed. The tests use the `npm`
utility Karma for actually running the tests. This target will run
both client and server tests.

## Dev Cards

The source is in `src/cards`. Remember to add devcard files here, and add
a require the for new card namespace to the `cards.cljs` file.

## I18N

The i18n process is codified into the Makefile as two targets. The first extracts strings from
the source (which must build the js, and run xgettext on it, which you must
have installed, perhaps from brew):

```
make i18n-extract
```

and gives you instructions on generating translations.

The second takes the translations and generates a cljs namespace for
them:

```
make i18n-generate
```

## Standalone Runnable Jar (Production, with advanced optimized client js)

```
lein uberjar
java -jar target/{{sanitized}}.jar
```
