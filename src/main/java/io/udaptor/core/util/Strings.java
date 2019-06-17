package io.udaptor.core.util;

import java.util.Objects;

public class Strings {

    private Strings(){}

    /*
     * Returns false if the String does not have text after trimming
     * */
    public static boolean hasText(final String text){
        if(Objects.isNull(text)){
            return false;
        }
        for(final Character ch : text.toCharArray()){
            if(!Character.isWhitespace(ch)){
                return true;
            }
        }
        return false;
    }


}
