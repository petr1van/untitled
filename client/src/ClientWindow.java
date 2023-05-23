import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ClientWindow extends JFrame implements ActionListener, TCPConnectionListener {
    private static final String IP_ADDR = "Your ip";
    private static final int PORT = 5657; //your port
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private TCPConnection connection;
    public static void main(String[] args) {


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientWindow();
            }
        });

    }
    private final JTextArea log = new JTextArea();

    private final JTextField fieldNickname = new JTextField("Your nickname");
    private final JTextComponent fieldHint = new JTextArea("Введите имя пользователя");
    private final JTextField fieldIdInput = new JTextField("Message");
    JFrame ClientWindow = new JFrame("Чат");
    private ClientWindow() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        log.setEditable(false);
        log.setLineWrap(true);
        fieldIdInput.addActionListener(this);
        add(log, BorderLayout.CENTER);
        add(fieldIdInput, BorderLayout.SOUTH);
        add(fieldNickname, BorderLayout.NORTH);
        fieldIdInput.setBackground(Color.LIGHT_GRAY);
        fieldIdInput.setBounds(5, 5,5,5);
        fieldHint.setBounds(5, 3, 500, 500);

        setVisible(true);
        try {
            connection = new TCPConnection(this, IP_ADDR, PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = fieldIdInput.getText();
        if (msg.equals("")) return;
        fieldIdInput.setText(null);
        connection.sendString(fieldNickname.getText() + ": " + msg);


    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        printMsg("Connection ready...");

    }

    @Override
    public void onReceieveString(TCPConnection tcpConnection, String value) {
        printMsg(value);

    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        printMsg("Connection close");

    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {
        printMsg("Connection exception " + e);

    }
    private synchronized void printMsg(String msg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.append(msg + "\n");
                log.setCaretPosition(log.getDocument().getLength());
            }
        });
    }
}
