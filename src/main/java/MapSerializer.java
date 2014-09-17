import java.util.Map;

import org.boon.json.serializers.JsonSerializerInternal;
import org.boon.json.serializers.impl.AbstractCustomObjectSerializer;
import org.boon.primitive.CharBuf;

public final class MapSerializer extends AbstractCustomObjectSerializer<Map> {

    public MapSerializer() {
        super(Map.class);
    }

    @Override
    public void serializeObject(JsonSerializerInternal serializer, Map instance, CharBuf builder) {
        serializer.serializeMap(instance, builder);
    }
}