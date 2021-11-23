import message.ConverterToJson;
import message.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestConverterToJson {
    @Test
    public void testListToJson() {
        ConverterToJson converter = new ConverterToJson();
        Message message = new Message("вася", "привет");
        Message message1 = new Message("петя", "привет");
        List<Message> list = new ArrayList<>();
        Date date = new Date();
        list.add(message);
        list.add(message1);
        String actualResult = "[{\"clientName\":\"вася\",\"textMessage\":\"привет\",\"time\":\"" + date.toString() + "\"},{\"clientName\":\"петя\",\"textMessage\":\"привет\",\"time\":\"" + date.toString() + "\"}]";
        String expectedResult = converter.listToJson(list);
        Assertions.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testCreateJson() {
        ConverterToJson converter = new ConverterToJson();
        Message message = new Message("вася", "привет");
        Date date = new Date();
        String actualResult = "{\"clientName\":\"вася\",\"textMessage\":\"привет\",\"time\":\"" + date.toString() + "\"}";
        String expectedResult = converter.createJson(message);
        Assertions.assertEquals(actualResult, expectedResult);
    }
}
