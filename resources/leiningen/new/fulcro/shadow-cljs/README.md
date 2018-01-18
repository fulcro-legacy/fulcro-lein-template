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
    │   │   │   ├── mutations.clj    ; server-side implementation of mutations
    │   │   │   ├── mutations.cljs   ; client-side implementation of mutations
    │   │   │   └── read.clj         ; server-side reads
    │   │   ├── client.cljs          ; file that creates the Fulcro client
    │   │   ├── server.clj           ; file that creates the web server
    │   │   ├── server_main.clj      ; production server entry point
    │   │   └── ui
    │   │       ├── components.cljc  ; Sample reusable component
    │   │       └── root.cljc        ; Main UI
    │   ├── starter
    │   │   └── browser.cljs         ; Shadow-cljs init/start/stop for hot code reload and production setup
    │   └── translations
    │       └── es.cljc              ; Generated cljs for es translations (see Makefile)
    └── test
        └── {{sanitized}}
            ├── client_test_main.cljs  ; setup for dev mode tests
            └── sample_spec.cljc       ; a sample spec in fulcro-spec
```

## Development Mode

Shadow-cljs handles the client-side development build. The  file
`src/main/starter/browser.cljs` contains the code to start and refresh
the client for hot code reload.

Running all client development builds:

```
$ shadow-cljs watch main cards test
...
shadow-cljs - HTTP server for ":main" available at http://localhost:8020
shadow-cljs - HTTP server for ":test" available at http://localhost:8021
shadow-cljs - HTTP server for ":cards" available at http://localhost:8022
...
```

This is the recommended approach on a multicore machine. The compiler
will detect which builds are affected by a change and will minimize
incremental build time.

NOTE: The server wil start a web server for all three builds (on different ports).
You typically do not need the one for main because you'll be running your
own server, but it is there in case you are only going to be writing
a client-side app that has no server API.

Running the server:

Start a clj REPL in IntelliJ, or from the command line:

```
lein run -m clojure.main
user=> (go)
...
user=> (restart) ; stop, reload server code, and go again
user=> (tools-ns/refresh) ; retry code reload if hot server reload fails
```

The URLs are:

- Client (using server): [http://localhost:3000](http://localhost:3000)
- Cards: [http://localhost:8022/cards.html](http://localhost:8022/cards.html)
- Tests: [http://localhost:8021/index.html](http://localhost:8021/index.html)

## Fulcro Inspect

The Fulcro inspect will preload on the main build. You can activate it
by pressing CTRL-F while in the application (dev mode only). If you need
a different keyboard shortcut (e.g. for Windows) see the docs on github.

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
make tests
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
