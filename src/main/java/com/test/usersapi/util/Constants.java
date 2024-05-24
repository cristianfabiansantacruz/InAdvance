package com.test.usersapi.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {

    public static final String EMAIL_EXIST_ERROR_MSG = "El email ya se encuentra registrado!!";

    public static final String EMAIL_OR_PASSWORD_ARE_WRONG = "Email o contraseña incorrectos!!";

    private static final String FORMAT_DATE = "dd/MM/yyyy HH:mm:ss";

    public static String formatDate(Date date) {
        if (date == null) return "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_DATE);
        return simpleDateFormat.format(date);
    }
}
