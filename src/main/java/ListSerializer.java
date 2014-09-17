import java.util.Collection;

import org.boon.json.serializers.JsonSerializerInternal;
import org.boon.json.serializers.impl.AbstractCustomObjectSerializer;
import org.boon.primitive.CharBuf;

public final class ListSerializer extends AbstractCustomObjectSerializer<Collection> {

    public ListSerializer() {
        super(Collection.class);
    }

    @Override
    public void serializeObject(JsonSerializerInternal serializer, Collection instance, CharBuf builder) {
        serializer.serializeCollection(instance, builder);
    }
}
