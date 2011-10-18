var top = app.getLayout("top");
top.add('clock','color=00ffff');
top.add('clock','color=00ffff');

var right = app.getLayout("right");
right.add('circle', 'color=00ffff');
right.add('log');

var bottom = app.getLayout("bottom");

bottom.add('applet','codebase=http://profs.etsmtl.ca/mmcguffin/learn/java/01-drawingLines/,width=300,height=300,code=DrawingLines');
bottom.add('circle', 'color=00ffaa');
bottom.add('applet','codebase=http://profs.etsmtl.ca/mmcguffin/learn/java/03-color/,width=400,height=300,code=DrawingWithColor1');
bottom.add('applet','codebase=http://profs.etsmtl.ca/mmcguffin/learn/java/08-painting/,width=300,height=300,code=Brush2');
