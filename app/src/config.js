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
top.add('circle');
top.add('clock');
top.add('square');
top.add('connection.status');

