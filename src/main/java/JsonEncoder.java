import com.google.common.hash.HashCode;
import groovy.lang.GString;
import org.boon.json.JsonSerializer;
import org.boon.json.JsonSerializerFactory;
import org.boon.json.serializers.CustomObjectSerializer;
import org.boon.json.serializers.JsonSerializerInternal;
import org.boon.primitive.CharBuf;
import org.codehaus.groovy.runtime.GStringImpl;

public final class JsonEncoder {

    private JsonEncoder() {}

    public static String toJson(Object obj) {
        return createSerializer().serialize(obj).toString();
    }

    private static JsonSerializer createSerializer() {
        JsonSerializerFactory factory = new JsonSerializerFactory()
                .includeNulls()
                .addTypeSerializer(HashCode.class, new HashCodeSerializer())
                .addTypeSerializer(GStringImpl.class, new GStringSerializer());
        return factory.create();
    }

    private static class GStringSerializer implements CustomObjectSerializer<GString> {
        @Override
        public Class<GString> type() {
            return GString.class;
        }

        @Override
        public void serializeObject(JsonSerializerInternal serializer, GString instance, CharBuf builder) {
            serializer.serializeString(instance.toString(), builder);
        }
    }

    private static class HashCodeSerializer implements CustomObjectSerializer<HashCode> {
        @Override
        public Class<HashCode> type() {
            return HashCode.class;
        }

        @Override
        public void serializeObject(JsonSerializerInternal serializer, HashCode instance, CharBuf builder) {
            serializer.serializeString(instance.toString(), builder);
        }
    }
}

