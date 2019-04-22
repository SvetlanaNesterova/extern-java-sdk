package ru.kontur.extern_api.sdk.typeadaptors;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Optional;
import ru.kontur.extern_api.sdk.GsonProvider;
import ru.kontur.extern_api.sdk.model.Docflow;
import ru.kontur.extern_api.sdk.model.DocflowType;
import ru.kontur.extern_api.sdk.model.Inventory;


public class GsonDocflowDeserializer implements JsonDeserializer{

    @Override
    public Docflow deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject obj = json.getAsJsonObject();
        DocflowType dt = deserialize(obj, "type", DocflowType.class, context);

        Docflow docflow = new Docflow();

        if(dt == DocflowType.FNS534_INVENTORY){
            docflow = new Inventory();
        }

        docflow.setType(deserialize(obj, "type", DocflowType.class, context));

        if (docflow.getType() == null) {
            docflow.setType(DocflowType.UNKNOWN);
        }

        Optional.ofNullable(docflow.getType())
                .map(DocflowType::getDescriptionType)
                .map(type -> deserialize(obj, "description", type, context))
                .ifPresent(docflow::setDescription);

        FieldNamingPolicy namingPolicy = GsonProvider.getFieldNamingPolicy();

        for (Field field : Docflow.class.getDeclaredFields()) {
            String fieldName = namingPolicy.translateName(field);
            if (Objects.equals(fieldName, "type") || Objects.equals(fieldName, "description")) {
                continue;
            }

            field.setAccessible(true);
            try {
                Object deserialized = deserialize(obj, fieldName, field.getGenericType(), context);
                field.set(docflow, deserialized);
            } catch (IllegalAccessException ignored) {
                // field.setAccessible(true) should work
            } finally {
                field.setAccessible(false);
            }
        }

        return docflow;
    }

    private static <T> T deserialize(
            JsonObject obj,
            String fieldName,
            Class<T> type,
            JsonDeserializationContext cxt) {
        return deserialize(obj, fieldName, (Type) type, cxt);
    }

    private static <T> T deserialize(
            JsonObject obj,
            String fieldName,
            Type type,
            JsonDeserializationContext cxt) {
        return cxt.deserialize(obj.get(fieldName), type);
    }

}

