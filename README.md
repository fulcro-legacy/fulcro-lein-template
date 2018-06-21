# fulcro

A Leiningen template for Fulcro.

## Usage

```
lein new fulcro project-name
```

## Options

- `demo` Include demo code
- `nodemo` Do not include demo code
- `figwheel` Use figwheel instead of shadow-cljs for Clojurescript

If you don't specify demo/nodemo, you will be asked.

## Emacs Cider Integration

If you plan on using cider with emacs and shadow-cljs, for an integrated repl, then you will have to add cider as a dependency. You have to put it in the cljs profile in project.clj.

```
[cider/cider-nrepl "X.X.X"]
```

## License

Copyright © 2017 Fulcrologic

Distributed under the MIT Public License.
