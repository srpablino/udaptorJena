package io.udaptor.core.util;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by Ankush on 17/07/17.
 */
public final class Assert {

    private Assert() {}

    /**
     * Validate if an Object is null.
     * @param t The parameter against which null check is performed
     * @param errorMsg The errorMsg to show in case @param t is null
     * @throws IllegalArgumentException in case @param t is null
     */
    public static <T> void notNull(final T t,final String errorMsg) {
        if (Objects.isNull(t)) {
            throw new IllegalArgumentException(errorMsg);
        }
    }

    public static <T> void nonEmptyString(final String string,final String errorMsg) {
        if (!Strings.hasText(string)) {
            throw new IllegalArgumentException(errorMsg);
        }
    }


    public static void notEmptyOrNull(final List<HashMap<String,String>> result, final String errorMsg){
        if(result == null || result.size() == 0){
            throw new IllegalArgumentException(errorMsg);
        }
    }



}
