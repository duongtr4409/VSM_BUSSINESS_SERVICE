package com.vsm.business.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public interface IBaseFileUtils {
    OutputStream writeTextToFile(Map<String, Object> props, InputStream inputStream) throws IOException;
}
