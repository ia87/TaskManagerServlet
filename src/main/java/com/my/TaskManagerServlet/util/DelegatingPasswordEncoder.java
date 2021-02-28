package com.my.TaskManagerServlet.util;

public class DelegatingPasswordEncoder {
    private static final String PREFIX = "{";
    private static final String SUFFIX = "}";
    private static final String idForEncode="bcrypt";
    private final BCryptPasswordEncoder passwordEncoderForEncode;

    public DelegatingPasswordEncoder() {
        passwordEncoderForEncode = new BCryptPasswordEncoder();
    }


    public String encode(CharSequence rawPassword) {
        return "{" + idForEncode + "}" + this.passwordEncoderForEncode.encode(rawPassword);
    }

    public boolean matches(CharSequence rawPassword, String prefixEncodedPassword) {
        if (rawPassword == null && prefixEncodedPassword == null) {
            return true;
        } else {
                String encodedPassword = this.extractEncodedPassword(prefixEncodedPassword);
                return passwordEncoderForEncode.matches(rawPassword, encodedPassword);
        }
    }

    private String extractEncodedPassword(String prefixEncodedPassword) {
        int start = prefixEncodedPassword.indexOf("}");
        return prefixEncodedPassword.substring(start + 1);
    }
}
