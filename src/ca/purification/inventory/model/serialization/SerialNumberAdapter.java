package ca.purification.inventory.model.serialization;

import ca.purification.inventory.model.SerialNumber;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * The {@code SerialNumberAdapter} class provides custom serialization and deserialization for {@link SerialNumber}
 * objects to and from JSON using the Gson library.
 *
 * <p>This adapter converts {@link SerialNumber} objects to a JSON string and parses such strings back into
 * {@link SerialNumber} instances, ensuring that {@code SerialNumber} values are properly handled during the JSON
 * serialization process.</p>
 *
 * <p>It is intended for use in scenarios where {@link SerialNumber} fields are present in the model and need to be
 * serialized or deserialized in JSON documents.</p>
 *
 * @see SerialNumber
 * @see JsonSerializer
 * @see JsonDeserializer
 */
public class SerialNumberAdapter implements JsonSerializer<SerialNumber>, JsonDeserializer<SerialNumber> {
    @Override
    public SerialNumber deserialize(JsonElement jsonElement, Type type,
                                 JsonDeserializationContext deserializationContext) throws JsonParseException {
        return new SerialNumber(jsonElement.getAsString());
    }

    @Override
    public JsonElement serialize(SerialNumber serialNumber, Type type,
                                 JsonSerializationContext serializationContext) {
        return new JsonPrimitive(serialNumber.getValue());
    }
}
