import java.nio.charset.Charset

import com.google.common.hash.HashCode
import com.google.common.hash.Hasher
import spock.lang.Specification

import static com.google.common.hash.Hashing.md5

class JsonEncoderTest extends Specification {

    def "should serialize GString correctly"() {
      when:
        String value = 'hello'
        String actualJson = JsonEncoder.toJson([key: "AGString $value"])

      then:
        '{"key":"AGString hello"}' == actualJson
    }

    /**
     * Fails since the created HashCode is a BytesHashCode, which is a private class.
     * JsonSerializerFactory().addTypeSerializer() requires the implementation class as
     * the first argument, but that's unavailable for us...
     */
    def "should serialize HashCode correctly"() {
      given:
        Hasher hasher = md5().newHasher()
        hasher.putString("heisann", Charset.defaultCharset())
        HashCode hash = hasher.hash()

      when:
        String actualJson = JsonEncoder.toJson(hash)

      then:
        actualJson instanceof String
        actualJson.size() == 34
    }
}
