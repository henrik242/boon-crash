import spock.lang.Specification

class JsonEncoderTest extends Specification {

    def "should serialize GString correctly"() {
      when:
        String value = 'hello'
        String actualJson = JsonEncoder.toJson([key: "AGString $value"])

      then:
        '{"key":"AGString hello"}' == actualJson
    }
}
