package message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class ConverterFromJson {

    public List<Message> jsonToList(String json) {
        JSONParser parser = new JSONParser();
        List<Message> list = new ArrayList<>();
        JSONArray jsonArray = null;

        try {
            jsonArray = (JSONArray) parser.parse(json);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        if (jsonArray != null) {
            for (Object o : jsonArray) {
                list.add(gson.fromJson(o.toString(), Message.class));
            }
        }
        return list;
    }

    public Message createMessage(String jsonText) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(jsonText, Message.class);
    }
}
