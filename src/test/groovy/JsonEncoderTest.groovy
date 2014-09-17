import java.nio.charset.Charset

import com.google.common.collect.ImmutableMap
import com.google.common.hash.HashCode
import com.google.common.hash.Hasher
import groovy.json.internal.LazyMap
import groovyx.gpars.extra166y.ParallelArray
import spock.lang.Specification

import static JsonEncoder.toJson
import static com.google.common.hash.Hashing.md5

class JsonEncoderTest extends Specification {

    def "should serialize GString correctly"() {
      given:
        String value = 'hello'
        def map = [key: "AGString $value"]

      when:
        String actualJson = toJson(map)

      then:
        map.key.getClass().simpleName == "GStringImpl"
        '{"key":"AGString hello"}' == actualJson
    }

    def "should serialize groovy LazyMap correctly"() {
      given:
        LazyMap map = new LazyMap()
        map.putAll([
            somekey: "somevalue",
            otherKey: [
                "otherValue",
                "thirdValue"
            ]
        ])

      when:
        String result = toJson(map)

      then:
        map.getClass().simpleName == "LazyMap"
        result == '{"otherKey":["otherValue","thirdValue"],"somekey":"somevalue"}'
    }

    def "should serialize guava ImmutableMap"() {
      given:
        def map = ImmutableMap.of("key", "ho", "key2", "pong")

      when:
        String result = toJson(map)

      then:
        map.getClass().simpleName == "RegularImmutableMap"
        result == '{"key":"ho","key2":"pong"}'
    }

    def "should serialize gpars ParallelArray.AsList"() {
      given:
        def list = ["hei", "sann", "du"]
        def aslist = ParallelArray.createFromCopy(list.size(), list.toArray(), ParallelArray.defaultExecutor()).asList()

      when:
        String result = toJson(aslist)

      then:
        aslist.getClass().simpleName == "AsList"
        result == '["hei","sann","du"]'
    }

    def "should serialize maps with null values"() {
      given:
        Map map = [ key: "sometext", nullkey: null ]

      when:
        String result = toJson(map)

      then:
        result == '{"key":"sometext","nullkey":null}'
    }

    def "should encode non-ascii values"() {
      given:
        String stuff = "heiæøå"

      when:
        String result = toJson(stuff)

      then:
        result == '"hei\\\\u00E6\\\\u00F8\\\\u00E5"'
    }

    def "should serialize HashCode correctly"() {
      given:
        Hasher hasher = md5().newHasher()
        hasher.putString("heisann", Charset.defaultCharset())
        HashCode hash = hasher.hash()

      when:
        String actualJson = toJson(hash)

      then:
        hash.getClass().simpleName == "BytesHashCode"
        actualJson == '"86c7c929d73e1d91c268c9f18d121212"'
    }
}
