package pl.adamklimko.zerosegandroid.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class MessageUtilTest {
    @Test
    public void containsPolishCharacters() throws Exception {
        String textPolish = "Witaj młodzieńcze.";
        assertEquals(true, MessageUtil.containsPolishCharacters(textPolish));

        String textWithout = "siema";
        assertEquals(false, MessageUtil.containsPolishCharacters(textWithout));

        String empty = "";
        assertEquals(false, MessageUtil.containsPolishCharacters(empty));
    }

    @Test
    public void normalizePolishCharacters() throws Exception {
        String textPolish = "Właśnie piszę ten test";
        String expected = "Wlasnie pisze ten test";
        assertEquals(expected, MessageUtil.normalizePolishCharacters(textPolish));

        String text = "Ala ma kota.";
        String expected2 = "Ala ma kota.";
        assertEquals(expected2, MessageUtil.normalizePolishCharacters(text));

        String empty = "";
        assertEquals(empty, MessageUtil.normalizePolishCharacters(empty));
    }

}