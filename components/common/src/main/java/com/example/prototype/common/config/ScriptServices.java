package com.example.prototype.common.config;

import com.example.prototype.api.widget.WidgetArea;

/**
 * The callback interface exposed to the javascript configuration file.
 */
public interface ScriptServices {

    /**
     * Get hold of a named layout area
     *
     * @param str The name of the layout area
     * @return A layout area that can be populated with widgets.
     */
    WidgetArea getLayout(String str);
}
