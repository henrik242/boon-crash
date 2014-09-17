import java.util.AbstractList;
import java.util.AbstractMap;

import com.google.common.collect.ImmutableMap;
import com.google.common.hash.HashCode;
import groovy.lang.GString;
import org.boon.json.JsonSerializer;
import org.boon.json.JsonSerializerFactory;

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
}

