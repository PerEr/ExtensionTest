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

bottom.add('add_widget');
bottom.add('clock');
bottom.add('connection.status', 'host=127.0.0.1,port=12003');
bottom.add('applet', 'classname=my.applet,codebase=http://abc.se');


