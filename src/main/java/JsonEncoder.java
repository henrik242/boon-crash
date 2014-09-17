import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.hash.HashCode;
import groovy.lang.GString;
import org.boon.json.JsonSerializer;
import org.boon.json.JsonSerializerFactory;
import org.boon.json.serializers.JsonSerializerInternal;
import org.boon.json.serializers.impl.AbstractCustomObjectSerializer;
import org.boon.primitive.CharBuf;

public final class JsonEncoder {

    private JsonEncoder() {}

    public static String toJson(Object obj) {
        return createJsonSerializer().serialize(obj).toString();
    }

    private static JsonSerializer createJsonSerializer() {
        JsonSerializerFactory jsonSerializerFactory = new JsonSerializerFactory()
                .includeNulls()
                .setEncodeStrings(true)
                .addTypeSerializer(GString.class, new StringSerializer())
                .addTypeSerializer(AbstractList.class, new ListSerializer())  // e.g. groovyx.gpars.extra166y.ParallelArray$AsList
                .addTypeSerializer(AbstractMap.class, new MapSerializer())    // e.g. groovy.json.internal.LazyMap
                .addTypeSerializer(ImmutableMap.class, new MapSerializer())  // e.g. com.google.common.collect.RegularImmutableMap
                .addTypeSerializer(HashCode.class, new StringSerializer());
        return jsonSerializerFactory.create();
    }

    private static final class MapSerializer extends AbstractCustomObjectSerializer<Map> {

        public MapSerializer() {
            super(Map.class);
        }

        @Override
        public void serializeObject(JsonSerializerInternal serializer, Map instance, CharBuf builder) {
            serializer.serializeMap(instance, builder);
        }
    }

    private static final class ListSerializer extends AbstractCustomObjectSerializer<Collection> {

        public ListSerializer() {
            super(Collection.class);
        }

        @Override
        public void serializeObject(JsonSerializerInternal serializer, Collection instance, CharBuf builder) {
            serializer.serializeCollection(instance, builder);
        }
    }

    private static final class StringSerializer extends AbstractCustomObjectSerializer<Object> {

        public StringSerializer() {
            super(Object.class);
        }

        @Override
        public void serializeObject(JsonSerializerInternal serializer, Object instance, CharBuf builder) {
            serializer.serializeString(instance.toString(), builder);
        }
    }
}

