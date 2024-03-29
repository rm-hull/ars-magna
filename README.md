# ![logo](https://raw.githubusercontent.com/rm-hull/ars-magna/master/logo.jpg) Ars Magna (Anagram Solver)

[![Coverage Status](https://coveralls.io/repos/rm-hull/ars-magna/badge.svg?branch=master)](https://coveralls.io/r/rm-hull/ars-magna?branch=master)
[![Dependencies Status](https://versions.deps.co/rm-hull/ars-magna/status.svg)](https://versions.deps.co/rm-hull/ars-magna)
[![Maintenance](https://img.shields.io/maintenance/yes/2023.svg?maxAge=2592000)]()

A multi-word anagram solver in Clojure, based on the article
**"Anagrams and Pangrams"** from _The Armchair Universe_, by A.K. Dewdney.

## Pre-requisites

You will need [Leiningen](https://github.com/technomancy/leiningen) 2.8.1 or above installed.

## Building

To build and start the service locally, run:

    $ cd ars-magna
    $ lein deps
    $ lein test
    $ lein ring server-headless

To build and run a standalone jar:

    $ lein ring uberjar
    $ java -jar target/ars-magna-0.2.1-standalone.jar

In both instances, the webapp starts on http://localhost:3000. See the curl
examples below for usage.

### Docker image

A docker image is available as [richardhull/ars-magna](https://github.com/rm-hull/ars-magna/pkgs/container/ars-magna),
and can be downloaded and started with:

    $ docker pull ghcr.io/rm-hull/ars-magna:main
    $ docker run --name ars-magna -d -p 3000:3000 ghcr.io/rm-hull/ars-magna:main

## Example API usage

### Multi-word anagrams

Using all the letters to find multi-word anagrams, from a Clojure REPL:

```clojure
(use 'ars-magna.dict)
(use 'ars-magna.solver)

(let [dict (load-word-list :en-GB)
      index (partition-by-word-length dict)]
  (sort
    (multi-word index "compute" 3)))
; ("come put" "compute" "cote ump" "cut mope" "cut poem"
;  "cute mop" "met coup" "mote cup" "mute cop" "tome cup")
```

or querying the web service for the word 'compute':

    $ curl -s http://localhost:3000/multi-word/compute | jq .

returns the same anagrams:

```json
[
  "come put",
  "compute",
  "cote ump",
  "cut mope",
  "cut poem",
  "cute mop",
  "met coup",
  "mote cup",
  "mute cop",
  "tome cup"
]
```

### Longest word anagrams

Find the longest single words from the given word, without necessarily using
all the letters - at the REPL:

```clojure
(use 'ars-magna.dict)
(use 'ars-magna.solver)

(let [dict (load-word-list :en-GB)
      index (partition-by-letters dict)]
  (sort-by
    (juxt (comp - count) identity)
      (longest index "compute" 4)))
; ("compute" "comet" "coupe" "tempo" "come"
;  "cope" "cote" "coup" "cute" "mope" "mote"
;  "mute" "poem" "poet" "pout" "temp" "tome")
```

_(the `(sort-by (juxt (comp - count) identity) ...)` returns the words
sorted by longest first, then alphabetically)_

or querying the web service for the word 'compute':

    $ curl -s http://localhost:3000/longest/compute | jq .

returns the same anagrams:

```json
[
  "compute",
  "comet",
  "coupe",
  "tempo",
  "come",
  "cope",
  "cote",
  "coup",
  "cute",
  "mope",
  "mote",
  "mute",
  "poem",
  "poet",
  "pout",
  "temp",
  "tome"
]
```

### Wildcards

Find the single words from the given wildcard (use `?` or `.` for a single
character or `*` for a sequence) - at the REPL:

```clojure
(use 'ars-magna.dict)
(use 'ars-magna.solver)

(->
  (load-word-list :en-GB)
  (wildcard "c..p..e")
  sort)
; ("compare" "compete" "compile"
;  "compose" "compote" "compute"
;  "coppice" "cowpoke" "cripple"
```

or querying the web service for the word 'compute':

    $ curl -s http://localhost:3000/wildcard/c..p..e | jq .

returns the same words:

```json
[
  "compare",
  "compete",
  "compile",
  "compose",
  "compote",
  "compute",
  "coppice",
  "cowpoke",
  "cripple"
]
```

## References

- https://en.wikipedia.org/wiki/Letter_frequency#Relative_frequencies_of_letters_in_the_English_language

## License

The MIT License (MIT)

Copyright (c) 2016 Richard Hull

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
