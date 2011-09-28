package app.config;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Config {

    public Config() throws IOException {
        ClassLoader ld = ClassLoader.getSystemClassLoader();
        InputStream is = ld.getResourceAsStream("config.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while (reader.ready()) {
            String line = reader.readLine();
            String[] tuple = line.split(" ");
            if (tuple.length == 2) {
                if (tuple[0].equals("plugin"))
                    plugins.add(tuple[1]);
                if (tuple[0].equals("widget"))
                    widgets.add(tuple[1]);
            }
        }
    }

    public String[] plugins() {
        return (String[])plugins.toArray(new String[plugins.size()]);
    }

    public String[] widgets() {
        return (String[])widgets.toArray(new String[widgets.size()]);
    }

    private final List<String> plugins = new LinkedList<String>();
    private final List<String> widgets = new LinkedList<String>();
}
