Sample use to of the widgetloader:

Press the "Discover plugins" button to discover available plugins and widgets.

The log-plugin does not publish any new widget types but replaces the logger of the app.
Check console log to see the change, look for the prefix "Info:" that does not appear until after the
log-plugin has been loaded.

Instantiate widgets.
Type the name of the widget to instantiate and press the "Instantiate" button. You may optionally pass parameters
to the plugins that will affect their apperance. Currently the only parameter supported is called "color", all
"shapes" and the com.example.prototype.clock support this property.
To bring up a green com.example.prototype.clock type "com.example.prototype.clock,color=00ff00" and press the instantiate button.

