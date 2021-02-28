package com.my.TaskManagerServlet.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validators {

    private static final String INVALID_SYMBOLS = "\" has invalid symbols";
    private static final String EMPTY = "\" cannot be empty";
    private static final String FIELD = "field \"";
    private static final String LONG = "\" is too long";
    private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";


    /**
     *
     * @param email e-mail
     * @return error message or null if validation was successful
     */

    public static String validateEMail(String email) {
        if (email == null) {
            return FIELD + "e-mail" + EMPTY;
        } else if (email.length() > 100) {
            return FIELD + "e-mail" + LONG;
        }
        Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) return null;
        return FIELD + "e-mail" + INVALID_SYMBOLS;
    }
    /**
     *
     * @param field value of the input field
     * @param fieldName name of the input field
     * @param length maximum length of the variable in database
     * @return error message or null if validation was successful
     */
    public static String validateFields(String field, String fieldName,
                                        int length) {

        if (field.length() == 0) {
            return FIELD + fieldName + EMPTY;
        } else if (field.length() > length) {
            return FIELD + fieldName + LONG;
        }

        Pattern p = Pattern.compile("[À-ßà-ÿA-Za-z\\d]+");
        Matcher m = p.matcher(field);

        if (!m.find()) {
            return FIELD + fieldName + INVALID_SYMBOLS;
        }
        if (m.group().length() != field.length()) {
            return FIELD + fieldName + INVALID_SYMBOLS;
        }

        return null;
    }

    /**
     * Validator for 'test name' 'question name' 'answer name' input fields
     * @param field value of the input field
     * @param fieldName name of the input field
     * @param length maximum length of the variable in database
     * @return error message or null if validation was successful
     */

    public static String validateSentences(String field, String fieldName,
                                           int length) {

        if (field.length() == 0) {
            return FIELD + fieldName + EMPTY;
        } else if (field.length() > length) {
            return FIELD + fieldName + LONG;
        }

        Pattern p = Pattern.compile("[À-ßà-ÿA-Za-z\\d\\s\\W]+");
        Matcher m = p.matcher(field);

        if (!m.find()) {
            return FIELD + fieldName + INVALID_SYMBOLS;
        }
        if (m.group().length() != field.length()) {
            return FIELD + fieldName + INVALID_SYMBOLS;
        }

        p = Pattern.compile("[ ]+");
        m = p.matcher(field);

        if (m.find() && m.group().length() == field.length()) {

            return FIELD + fieldName + INVALID_SYMBOLS;

        }

        return null;
    }

    /**
     * validator for input fields that must contain only latin symbols
     * @param field value of the field
     * @param fieldName name of the field
     * @param length maximum length of the variable in database
     * @return error message or null if validation was successful
     */

}