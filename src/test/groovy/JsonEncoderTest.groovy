import spock.lang.Specification

class JsonEncoderTest extends Specification {

    def "should serialize GString correctly"() {
      when:
        String value = 'hello'
        String actualJson = JsonEncoder.toJson([key: "AGString $value"])

      then:
        '{"key":"AGString hello"}' == actualJson
    }

    /**
     * Other JSON libraries, like groovy's internal lib and Gson, will happily create the
     * null value in Json.  I am guessing that JsonSerializerFactory().includeNulls() should
     * support this, but I might be wrong...
     */
    def "should serialize maps with null values"() {
      given:
        Map map = [ key: "sometext", nullkey: null ]

      when:
        String result = JsonEncoder.toJson(map)

      then:
        result == '{"key":"sometext","nullkey":null}'
    }
}
