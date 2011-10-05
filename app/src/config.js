app.register('log.LogPlugin');
app.register('shapes.SimpleShapesPlugin');
app.register('clock.ClockPlugin');
app.register('connection.monitor.ConnectionMonitorPlugin');
app.register('connection.widget.ConnectionWidgetPlugin');

app.loadAll();

var top = app.getLayout("top");

top.add('connection.status', 'host=127.0.0.1,port=12003');
top.add('clock','color=00ff00');
top.add('clock','color=00ffff');
top.add('clock','color=ffff00');
top.add('circle', 'color=ff0000');
top.add('circle', 'color=00ff00');
top.add('connection.status');
top.add('square', 'color=55ff33');

var right = app.getLayout("right");

right.add('clock');
right.add('circle', 'color=ff0000');
right.add('square', 'color=bbff33');
right.add('circle', 'color=00ff00');
right.add('square', 'color=55ccff');

var bottom = app.getLayout("bottom");

bottom.add('clock');
bottom.add('connection.status', 'host=127.0.0.1,port=12003');
bottom.add('circle', 'color=ff0000');
bottom.add('square', 'color=55ff33');
bottom.add('connection.status', 'host=127.0.0.1,port=12003');
bottom.add('circle', 'color=00ff00');


