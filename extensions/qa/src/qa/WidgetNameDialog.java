package qa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class WidgetNameDialog extends JDialog {

    public WidgetNameDialog(JFrame frame, final WidgetChoiceListener listener) {
        super(frame, false);
        this.listener = listener;

        // Populate GUI
        java.awt.Container container = getContentPane();
        container.setLayout(new GridLayout(3,1));

        container.add(new JLabel("Enter widgetname and its properties"));

        textField = new JTextField("clock,color=00ff00");
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onDone();
            }
        });
        container.add(textField);

        final JButton loadButton = new JButton("Load");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (actionEvent.getSource() == loadButton) {
                    onDone();
                }
            }
            
        });
        container.add(loadButton);

        pack();
        setLocationRelativeTo(frame);
        setVisible(true);
    }

    private void onDone() {
        listener.onChoice(textField.getText().trim());
        dispose();
    }

    private WidgetChoiceListener listener;
    private final JTextField textField;

}