package io.udaptor.core.util;

import java.io.File;

public class Util {

    public static String getFileExtension(final File file) {
        Assert.notNull(file, " file cannot be null");
        final String[] array = file.getName().split("\\.");
        return array[array.length - 1];

    }


}
