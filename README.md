boon-crash
==========

Build with `./gradlew clean build`

Addresses https://github.com/RichardHightower/boon/issues/231

I created the following serializer:

<code><pre>
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
</code></pre>

And I try to serialize the following HashCode:

<code><pre>
    Hasher hasher = md5().newHasher()
    hasher.putString("heisann", Charset.defaultCharset())
    HashCode hash = hasher.hash()
</code></pre>

Serialization fails since JsonSerializerFactory().addTypeSerializer() requires the type implementation class as
the first argument, but that's unavailable for us: The created HashCode is actually a BytesHashCode, which is a private class, so org.boon.json.serializers.impl.CustomObjectSerializerImpl#serializeObject is unable to match the class in line 38:

<code><pre>
    final CustomObjectSerializer customObjectSerializer = overrideMap.get(Boon.cls(obj));
    if (customObjectSerializer!=null) {
         customObjectSerializer.serializeObject(jsonSerializer, obj, builder);
         return;
    }
</code></pre>
