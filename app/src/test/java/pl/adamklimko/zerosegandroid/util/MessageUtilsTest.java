package pl.adamklimko.zerosegandroid.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class MessageUtilsTest {
    @Test
    public void containsPolishCharacters() throws Exception {
        String textPolish = "Witaj młodzieńcze.";
        assertEquals(true, MessageUtils.containsPolishCharacters(textPolish));

        String textWithout = "siema";
        assertEquals(false, MessageUtils.containsPolishCharacters(textWithout));
    }

    @Test
    public void normalizePolishCharacters() throws Exception {
        String textPolish = "Właśnie piszę ten test";
        String expected = "Wlasnie pisze ten test";
        assertEquals(expected, MessageUtils.normalizePolishCharacters(textPolish));
    }

}