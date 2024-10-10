package ca.purification.inventory.model.serialization;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The {@code LocalDateAdapter} class provides custom serialization and deserialization for {@link LocalDate} objects
 * to and from JSON using the Gson library.
 *
 * <p>This adapter converts {@link LocalDate} objects to a JSON string in the ISO-8601 format ("yyyy-MM-dd")
 * and parses such strings back into {@link LocalDate} instances.</p>
 *
 * <p>It is intended for use when dealing with JSON that needs to handle {@link LocalDate} fields, ensuring
 * proper formatting and parsing.</p>
 * 
 * @implNote LocalDate <--> JSON support
 *  Source: https://www.javaguides.net/2019/11/gson-localdatetime-localdate.html
 *  Source: https://stackoverflow.com/questions/39192945/serialize-java-8-localdate-as-yyyy-mm-dd-with-gson
 *
 * @see JsonSerializer
 * @see JsonDeserializer
 */
public class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonElement jsonElement, Type type,
                                 JsonDeserializationContext deserializationContext) throws JsonParseException {
        return LocalDate.parse(jsonElement.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @Override
    public JsonElement serialize(LocalDate localDate, Type type,
                                 JsonSerializationContext serializationContext) {
        return new JsonPrimitive(localDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }
}
