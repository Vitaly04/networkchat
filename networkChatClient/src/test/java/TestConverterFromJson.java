import message.ConverterFromJson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class TestConverterFromJson {

    @Test
    public void testJsonToList() {
        ConverterFromJson converter = new ConverterFromJson();
        Date date = new Date();
        String json = "[{\"clientName\":\"вася\",\"textMessage\":\"привет\",\"time\":\"" + date.toString() +
                "\"},{\"clientName\":\"петя\",\"textMessage\":\"привет\",\"time\":\"" + date.toString() + "\"}]";
        String actualResult = "[name = вася, textMessage = привет, time = " + date.toString() + "\n" + ", " +
                "name = петя, textMessage = привет, time = " + date.toString() + "\n]";
        String expectedResult = String.valueOf(converter.jsonToList(json));
        Assertions.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testCreateMessage() {
        ConverterFromJson converter = new ConverterFromJson();
        Date date = new Date();
        String json = "{\"clientName\":\"вася\",\"textMessage\":\"привет\",\"time\":\"" + date.toString() + "\"}";
        String actualResult = "name = вася, textMessage = привет, time = " +date.toString() + "\n";
        String expectedResult = String.valueOf(converter.createMessage(json));
        Assertions.assertEquals(actualResult, expectedResult);
    }
}
