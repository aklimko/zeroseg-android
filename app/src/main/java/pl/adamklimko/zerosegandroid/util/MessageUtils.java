package pl.adamklimko.zerosegandroid.util;

import java.util.HashMap;
import java.util.Map;

public class MessageUtils {
    private static final Map<Character, Character> respectiveEquivalent;

    static {
        respectiveEquivalent = new HashMap<>();
        respectiveEquivalent.put('ą', 'a');
        respectiveEquivalent.put('ć', 'c');
        respectiveEquivalent.put('ę', 'e');
        respectiveEquivalent.put('ł', 'l');
        respectiveEquivalent.put('ń', 'n');
        respectiveEquivalent.put('ó', 'o');
        respectiveEquivalent.put('ś', 's');
        respectiveEquivalent.put('ż', 'z');
        respectiveEquivalent.put('ź', 'z');
    }

    public static boolean containsPolishCharacters(String text) {
        for (char c : text.toCharArray()) {
            if (respectiveEquivalent.containsKey(c)) {
                return true;
            }
        }
        return false;
    }

    public static String normalizePolishCharacters(String text) {
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (respectiveEquivalent.containsKey(chars[i])) {
                chars[i] = respectiveEquivalent.get(chars[i]);
            }
        }
        return String.valueOf(chars);
    }
}
