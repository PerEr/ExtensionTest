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
bottom.add('applet','codebase=http://profs.etsmtl.ca/mmcguffin/learn/java/01-drawingLines/,width=300,height=300,code=DrawingLines');
bottom.add('applet','codebase=http://profs.etsmtl.ca/mmcguffin/learn/java/03-color/,width=300,height=300,code=DrawingWithColor1');
//bottom.add('applet','codebase=http://profs.etsmtl.ca/mmcguffin/learn/java/08-painting/,width=300,height=300,code=Brush2');
