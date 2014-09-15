boon-crash
==========

Build with `./gradlew clean build`

Addresses https://github.com/RichardHightower/boon/issues/234

I am trying to get boon to escape non-ascii characters, e.g. ``abcæøå`` should become ``"abc\\u00E6\\u00F8\\u00E5"``.  Groovy's JSON library does this, for example.