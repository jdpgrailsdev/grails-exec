/* Copyright 2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.grails.exec;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Utility methods for converting between script names used on the command line
 * and their class names, and vice versa.
 */
public class NameUtils {

    /**
     * Converts the name of command given a script name, e.g. RunApp to run-app.
     *
     * @param scriptName The name of the script (e.g. RunApp)
     * @return The command name representation (e.g. run-app).
     */
    public static String toCommandName(String scriptName) {
        if (scriptName == null) {
            throw new IllegalArgumentException("'scriptName' cannot be null");
        } else {
            return toNaturalName(scriptName).replaceAll("\\s", "-").toLowerCase();
        }
    }

    /**
     * Converts a command name to the name of the script, e.g. run-app to RunApp.
     *
     * @param commandName The name of the command (e.g. run-app)
     * @return The name of the script file (e.g. RunApp).
     */
    public static String toScriptName(String commandName) {
        if (commandName == null) {
            throw new IllegalArgumentException("'commandName' cannot be null");
        } else if (commandName.trim().length() == 0) {
            throw new IllegalArgumentException("'commandName' cannot be blank");
        }

        if (commandName.indexOf('-') > -1) {
            StringBuilder buf = new StringBuilder();
            String[] tokens = commandName.split("-");
            for (String token : tokens) {
                if (token == null || token.length() == 0) continue;
                buf.append(token.substring(0, 1).toUpperCase())
                   .append(token.substring(1));
            }
            return buf.toString();
        }

        return commandName.substring(0,1).toUpperCase() + commandName.substring(1);
    }

    /**
     * Converts a property name into its natural language equivalent eg ('firstName' becomes 'First Name')
     * @param name The property name to convert
     * @return The converted property name
     */
    private static String toNaturalName(String name) {
        List<String> words = new ArrayList<String>();
        int i = 0;
        char[] chars = name.toCharArray();
        for (int j = 0; j < chars.length; j++) {
            char c = chars[j];
            String w;
            if (i >= words.size()) {
                w = "";
                words.add(i, w);
            }
            else {
                w = words.get(i);
            }

            if (Character.isLowerCase(c) || Character.isDigit(c)) {
                if (Character.isLowerCase(c) && w.length() == 0) {
                    c = Character.toUpperCase(c);
                }
                else if (w.length() > 1 && Character.isUpperCase(w.charAt(w.length() - 1))) {
                    w = "";
                    words.add(++i,w);
                }

                words.set(i, w + c);
            }
            else if (Character.isUpperCase(c)) {
                if ((i == 0 && w.length() == 0) || Character.isUpperCase(w.charAt(w.length() - 1))) {
                    words.set(i, w + c);
                }
                else {
                    words.add(++i, String.valueOf(c));
                }
            }
        }

        StringBuilder buf = new StringBuilder();
        for (Iterator<String> j = words.iterator(); j.hasNext();) {
            String word = j.next();
            buf.append(word);
            if (j.hasNext()) {
                buf.append(' ');
            }
        }
        return buf.toString();
    }
}