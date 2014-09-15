import spock.lang.Specification
import groovy.json.StringEscapeUtils

class JsonEncoderTest extends Specification {

    def "should serialize GString correctly"() {
      when:
        String value = 'hello'
        String actualJson = JsonEncoder.toJson([key: "AGString $value"])

      then:
        '{"key":"AGString hello"}' == actualJson
    }

    /**
     * Other JSON libraries, like groovy's internal lib, escapes non-ascii
     * characters.  Somehow boon doesn't.
     */
    def "should encode non-ascii values"() {
      given:
        String stuff = "heiæøå"

      when:
        String result = JsonEncoder.toJson(stuff)

      then:
        result == '"hei\\\\u00E6\\\\u00F8\\\\u00E5"'
    }
}
