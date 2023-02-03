package mx.ipn.escom.pizarron.crud.util;

import static mx.ipn.escom.pizarron.crud.util.Constants.BUSINESS_EXCEPTION;

public class ExceptionMessage {

    private ExceptionMessage(){}

    public static String setMessage(String message){
        if(message.length() > 100) return BUSINESS_EXCEPTION;
        else return message;
    }
}
