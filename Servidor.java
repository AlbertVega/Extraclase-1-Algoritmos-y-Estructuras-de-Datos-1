import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class Servidor {
    public static void main(String [] args) {
        VentanaServidor Ventana2 = new VentanaServidor();
    }
}

class VentanaServidor extends JFrame {
    public VentanaServidor() {

        setBounds(500, 300, 300, 300);

        JPanel Lamina2 = new JPanel();

        Lamina2.setLayout(new BorderLayout());

        AreaTexto = new JTextArea();

        Lamina2.add(AreaTexto,BorderLayout.CENTER);

        add(Lamina2);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }
    public JTextArea AreaTexto;
} 