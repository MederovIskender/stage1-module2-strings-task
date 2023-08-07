package com.epam.mjc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StringSplitter {

    /**
     * Splits given string applying all delimeters to it. Keeps order of result substrings as in source string.
     *
     * @param source source string
     * @param delimiters collection of delimiter strings
     * @return List of substrings
     */
    public List<String> splitByDelimiters(String source, Collection<String> delimiters) {
        if (source == null || delimiters == null) {
            throw new IllegalArgumentException("Source string and delimiters collection must not be null.");
        }

        List<String> result = new ArrayList<>();
        int startIndex = 0;

        for (int i = 0; i < source.length(); i++) {
            for (String delimiter : delimiters) {
                if (source.startsWith(delimiter, i)) {
                    String substring = source.substring(startIndex, i);
                    if (!substring.isEmpty()) {
                        result.add(substring);
                    }
                    startIndex = i + delimiter.length();
                    i = startIndex - 1; // Move the outer loop index back
                    break; // Move to the next character after the delimiter
                }
            }
        }

        // Add the remaining substring after the last delimiter (or the whole string if no delimiters were found)
        if (startIndex < source.length()) {
            String lastSubstring = source.substring(startIndex);
            result.add(lastSubstring);
        }

        return result;
    }
}
