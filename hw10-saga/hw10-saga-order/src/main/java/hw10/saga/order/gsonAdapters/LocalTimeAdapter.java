package hw10.saga.order.gsonAdapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalTime;

public class LocalTimeAdapter extends TypeAdapter<LocalTime> {
    @Override
    public void write(JsonWriter out, LocalTime value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        String timeString = value.toString();
        out.value(timeString);
    }
    @Override
    public LocalTime read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        String dateTimeString = in.nextString();
        return LocalTime.parse(dateTimeString);
    }
}