boon-crash
==========

Build with `./gradlew clean build`

Addresses https://github.com/RichardHightower/boon/issues/232

It seems that support for null values in a map is unsupported. Other JSON libraries
like groovy's internal lib and Gson, will happily create the null value in Json. I am 
guessing that JsonSerializerFactory().includeNulls() should turn that support on, but I might be wrong...

If I serialize the following map, I only get {"key":"somevalue"} back:

<code><pre>
 Map map = [ key: "somevalue", otherkey: null ]
</code></pre>
