package common.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyBuilder {

    public static Properties fromString(String param) {
        assert param != null;

        final Properties props = new Properties();
        // Replace commas with linefeeds
        final String config = param.replace(',', '\n');
        try {
            props.load(new ByteArrayInputStream(config.getBytes()));
        } catch (IOException e) {
            // This should never happen!
            assert false;
        }
        return props;
    }
}