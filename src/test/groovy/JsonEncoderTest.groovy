import java.nio.charset.Charset

import com.google.common.collect.ImmutableMap
import com.google.common.hash.HashCode
import groovy.json.internal.LazyMap
import groovyx.gpars.extra166y.ParallelArray
import org.boon.json.JsonSerializerFactory
import spock.lang.Specification

import static com.google.common.hash.Hashing.md5

class JsonEncoderTest extends Specification {

    def jsf

    def setup() {
        jsf = new JsonSerializerFactory()
    }

    def "should serialize GString correctly"() {
      given:
        def factory = jsf.addTypeSerializer(GString.class, new StringSerializer())
        def value = "hello"
        def map = [key: "AGString $value"]

      when:
        def result = toJson(factory, map)

      then:
        map.key.getClass().simpleName == "GStringImpl"
        '{"key":"AGString hello"}' == result
    }

    def "should serialize groovy LazyMap correctly"() {
      given:
        def factory = jsf.addTypeSerializer(AbstractMap.class, new MapSerializer())
        def map = new LazyMap()
        map.putAll([
            somekey: "somevalue",
            otherKey: [
                "otherValue",
                "thirdValue"
            ]
        ])

      when:
        def result = toJson(factory, map)

      then:
        map.getClass().simpleName == "LazyMap"
        result == '{"otherKey":["otherValue","thirdValue"],"somekey":"somevalue"}'
    }

    def "should serialize guava ImmutableMap"() {
      given:
        def factory = jsf.addTypeSerializer(ImmutableMap.class, new MapSerializer())
        def map = ImmutableMap.of("key", "ho", "key2", "pong")

      when:
        def result = toJson(factory, map)

      then:
        map.getClass().simpleName == "RegularImmutableMap"
        result == '{"key":"ho","key2":"pong"}'
    }

    def "should serialize gpars ParallelArray.AsList"() {
      given:
        def factory = jsf.addTypeSerializer(AbstractList.class, new ListSerializer())
        def list = ["hei", "sann", "du"]
        def aslist = ParallelArray.createFromCopy(list.size(), list.toArray(), ParallelArray.defaultExecutor()).asList()

      when:
        def result = toJson(factory, aslist)

      then:
        aslist.getClass().simpleName == "AsList"
        result == '["hei","sann","du"]'
    }

    def "should serialize maps with null values"() {
      given:
        def factory = jsf.includeNulls()
        def map = [ key: "sometext", nullkey: null ]

      when:
        def result = toJson(factory, map)

      then:
        result == '{"key":"sometext","nullkey":null}'
    }

    def "should encode non-ascii values"() {
      given:
        def factory = jsf.setEncodeStrings(true)
        def stuff = "heiæøå"

      when:
        def result = toJson(factory, stuff)

      then:
        result == '"hei\\u00E6\\u00F8\\u00E5"'
    }

    def "should serialize HashCode correctly"() {
      given:
        def factory = jsf.addTypeSerializer(HashCode.class, new StringSerializer());
        def hasher = md5().newHasher()
        hasher.putString("heisann", Charset.defaultCharset())
        def hash = hasher.hash()

      when:
        def result = toJson(factory, hash)

      then:
        hash.getClass().simpleName == "BytesHashCode"
        result == '"86c7c929d73e1d91c268c9f18d121212"'
    }

    private static String toJson(JsonSerializerFactory factory, Object obj) {
        factory.create().serialize(obj).toString()
    }
}
