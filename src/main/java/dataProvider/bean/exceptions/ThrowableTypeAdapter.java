package dataProvider.bean.exceptions;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Field;

public class ThrowableTypeAdapter extends TypeAdapter<Throwable> {
    @Override
    public void write(JsonWriter out, Throwable throwable) throws IOException {
        Field detailMessageField = null;
        try {
            detailMessageField = Throwable.class.getDeclaredField("detailMessage");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        detailMessageField.setAccessible(true);
        out.beginObject();
        out.name("message").value(throwable.getMessage());
        // Other fields you want to serialize from the Throwable object
        out.endObject();
    }

    @Override
    public Throwable read(JsonReader in) throws IOException {
        in.beginObject();
        String message = null;
        while (in.hasNext()) {
            String name = in.nextName();
            if (name.equals("message")) {
                message = in.nextString();
            } else {
                in.skipValue();
            }
        }
        in.endObject();
        return new Throwable(message);
    }
}
