app.register('log.LogPlugin');
app.register('usagereport.UsageReportPlugin');
app.register('widget.SimpleShapesPlugin');
app.register('clock.ClockPlugin');
app.register('connection.monitor.ConnectionMonitorPlugin');
app.register('connection.widget.ConnectionWidgetPlugin');

app.loadAll();

var top = app.getLayout("top");

top.add('connection.status', 'host=127.0.0.1,port=12003');
top.add('clock');
top.add('circle', 'color=ff0000');
top.add('circle', 'color=00ff00');
top.add('circle', 'color=0000ff');
top.add('clock');
top.add('square', 'color=ffff00');
top.add('square', 'color=ff88ff');
top.add('square', 'color=55ff33');
top.add('connection.status');

