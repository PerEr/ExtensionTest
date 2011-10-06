package qa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class WidgetNameDialog extends JDialog {

    public WidgetNameDialog(JFrame frame, String s) {
        super(frame, false);
        java.awt.Container container = getContentPane();
        container.setLayout(new GridLayout(3,1));
        container.add(new JLabel("Enter widgetname and its properties"));
        container.add(new JTextField("clock,color=00ff00"));
        final JButton loadButton = new JButton("Load");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onClick();
            }
            
        });
        container.add(loadButton);
        pack();
        setLocationRelativeTo(frame);
        setVisible(true);
    }

    private void onClick() {
        setVisible(false);
    }
}