import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

public class MainForm {

    private JPanel MainPanel;
    private JPanel collapsePanel;
    private JTextField name;
    private JTextField patronymic;
    private JTextField surname;
    private JButton collapseButton;
    private JLabel label1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JPanel expandPanel;
    private JTextField FIO;
    private JButton expand;
    private static String fio;
    private static String stringName;
    private static String stringSurname;
    private static String stringPatronymic;

    public MainForm() {

        expandPanel.setVisible(false);
        collapseButton.addActionListener(new Action() {
            @Override
            public Object getValue(String key) { return null; }

            @Override
            public void putValue(String key, Object value) { }

            @Override
            public void setEnabled(boolean b) { }

            @Override
            public boolean isEnabled() { return false; }

            @Override
            public void addPropertyChangeListener(PropertyChangeListener listener) { }

            @Override
            public void removePropertyChangeListener(PropertyChangeListener listener) { }

            @Override
            public void actionPerformed(ActionEvent e) {
                if(surname.getText().length() != 0 && name.getText().length() != 0 && patronymic.getText().length() == 0){
                    fio = surname.getText() + " " + name.getText();
                    JOptionPane.showMessageDialog(
                            MainPanel,
                            fio,
                            "OK",
                            JOptionPane.PLAIN_MESSAGE);
                    collapsePanel.setVisible(false);
                    FIO.setText(fio);
                    expandPanel.setVisible(true);
                }else if(surname.getText().length() != 0 && name.getText().length() != 0 && patronymic.getText().length() != 0) {
                    fio = surname.getText() + " " + name.getText() + " " + patronymic.getText();
                    JOptionPane.showMessageDialog(
                            MainPanel,
                            fio,
                            "OK",
                            JOptionPane.PLAIN_MESSAGE);
                    collapsePanel.setVisible(false);
                    FIO.setText(fio);
                    collapseButton.setVisible(false);
                    expandPanel.setVisible(true);
                }
                else if(surname.getText().length() == 0){
                    JOptionPane.showMessageDialog(
                            MainPanel,
                            "Введите фамилию!",
                            "Error Surname",
                            JOptionPane.ERROR_MESSAGE
                    );
                }else if(name.getText().length() == 0) {
                    JOptionPane.showMessageDialog(
                            MainPanel,
                            "Введите имя!",
                            "Error Name",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
        expand.addActionListener(new Action() {

            @Override
            public Object getValue(String key) { return null; }

            @Override
            public void putValue(String key, Object value) { }

            @Override
            public void setEnabled(boolean b) { }

            @Override
            public boolean isEnabled() { return false; }

            @Override
            public void addPropertyChangeListener(PropertyChangeListener listener) { }

            @Override
            public void removePropertyChangeListener(PropertyChangeListener listener) { }

            @Override
            public void actionPerformed(ActionEvent e) {
                String allText = FIO.getText();
                if(allText.length() != 0){
                    String[] fio = allText.split(" ");
                    if(fio.length == 3){
                        stringSurname = fio[0];
                        stringName = fio[1];
                        stringPatronymic = fio[2];
                        sendMessage();
                    }else if(fio.length == 2){
                        stringSurname = fio[0];
                        stringName = fio[1];
                        stringPatronymic = "";
                        JOptionPane.showMessageDialog(
                                MainPanel,
                                "Заполните данные!",
                                "Error data",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    collapseButton.setVisible(true);
                }
            }
        });
    }

    public JPanel getMainPanel(){ return MainPanel; }

    public void sendMessage(){
        JOptionPane.showMessageDialog(
                MainPanel,
                stringSurname + " " + stringName + " " + stringPatronymic,
                "OK",
                JOptionPane.PLAIN_MESSAGE);
        expandPanel.setVisible(false);
        collapsePanel.setVisible(true);
        surname.setText(stringSurname);
        name.setText(stringName);
        patronymic.setText(stringPatronymic);
    }
}
