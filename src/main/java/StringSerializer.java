import org.boon.json.serializers.JsonSerializerInternal;
import org.boon.json.serializers.impl.AbstractCustomObjectSerializer;
import org.boon.primitive.CharBuf;

public final class StringSerializer extends AbstractCustomObjectSerializer<Object> {

    public StringSerializer() {
        super(Object.class);
    }

    @Override
    public void serializeObject(JsonSerializerInternal serializer, Object instance, CharBuf builder) {
        serializer.serializeString(instance.toString(), builder);
    }
}