package message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ConverterToJson {

    public String listToJson(List<Message> list) {
        String json;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type listType = new TypeToken<List<Message>>() {}.getType();
        return json = gson.toJson(list, listType);
    }

    public String createJson(Message message) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(message);
    }
}
