import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

/**
 * @author Albert Vega Camacho
 */
public class Cliente {
    public static void main(String [] args){
        VentanaCliente Ventana1 = new VentanaCliente();
    }
}

/**
 * Esta clase es la encargada de manejar los atibutos de la ventana del cliente.
 */
class VentanaCliente extends JFrame {
    public VentanaCliente() {

        setBounds(500, 300, 600, 300); 
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //DETENER EL PROGRAMA CUANDO SE CIERRA LA VENTANA

        LaminaMarcoCliente Lamina1 = new LaminaMarcoCliente();
        
        add(Lamina1);

        setVisible(true); //HACER LA VENTA VISIBLE SIEMPRE VA AL FINAL
    }
}

/**
 * Esta clase se encarga de gestionar los elementos dentro de la ventana creada
 */
class LaminaMarcoCliente extends JPanel {
    public LaminaMarcoCliente() {
        JLabel textotitulo = new JLabel("INDIQUE LOS VALORES DEL PRODUCTO"); 
        add(textotitulo);

        JLabel textoprecio = new JLabel("Precio"); 
        add(textoprecio);
        CampoTextoPrecio = new JTextField(5);
        add(CampoTextoPrecio);

        JLabel textopeso = new JLabel("Peso"); 
        add(textopeso);
        CampoTextoPeso = new JTextField(5);
        add(CampoTextoPeso);

        JLabel textoimpuesto = new JLabel("Impuesto"); 
        add(textoimpuesto);
        CampoTextoImpuesto = new JTextField(5);
        add(CampoTextoImpuesto);

        Boton1 = new JButton("ENVIAR");

        add(Boton1);

        Campo_de_texto = new JTextArea(12,20);

        add(Campo_de_texto);
        
    }
    private JTextField CampoTextoPrecio;

    private JTextField CampoTextoPeso;

    private JTextField CampoTextoImpuesto;

    private JButton Boton1;

    private JTextArea Campo_de_texto;

    private class EnviaTexto implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try {
                Socket Socketcliente1 = new Socket("192.168.0.15", 878);

                Socketcliente1.close();

            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                System.out.println(e1.getMessage());
            }
        }
    }
