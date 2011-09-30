package app.config;

import common.config.ResourceLoader;

import javax.script.*;
import java.io.IOException;

public class ScriptedConfig {

    public ScriptedConfig(String scriptName, ScriptServices scriptServices) throws ScriptException, IOException {

        ScriptEngineManager factory = new ScriptEngineManager();

        String[] tuple = scriptName.split("\\.");
        assert tuple.length == 2;

        ScriptEngine engine = factory.getEngineByExtension(tuple[1]);
        engine.getContext().setAttribute("app", scriptServices, ScriptContext.ENGINE_SCOPE);

        String script = ResourceLoader.loadResource(scriptName);
        engine.eval(script);


    }

}
