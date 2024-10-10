package ca.purification.inventory.model.serialization;

import ca.purification.inventory.model.ModelId;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * The {@code ModelIdAdapter} class provides custom serialization and deserialization for {@link ModelId} objects
 * to and from JSON using the Gson library.
 *
 * <p>This adapter converts {@link ModelId} objects to a JSON string and parses such strings back into
 * {@link ModelId} instances, ensuring that {@code ModelId} values are properly handled during the JSON
 * serialization process.</p>
 *
 * <p>It is particularly useful for scenarios where {@link ModelId} fields need to be serialized in a standardized
 * format within JSON documents.</p>
 *
 * @see ModelId
 * @see JsonSerializer
 * @see JsonDeserializer
 */
public class ModelIdAdapter implements JsonSerializer<ModelId>, JsonDeserializer<ModelId> {
    @Override
    public ModelId deserialize(JsonElement jsonElement, Type type,
                               JsonDeserializationContext deserializationContext) throws JsonParseException {
        return new ModelId(jsonElement.getAsString());
    }

    @Override
    public JsonElement serialize(ModelId modelId, Type type,
                                 JsonSerializationContext serializationContext) {
        return new JsonPrimitive(modelId.getValue());
    }
}
