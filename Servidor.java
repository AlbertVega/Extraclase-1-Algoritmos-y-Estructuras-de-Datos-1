import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

/**
 * @author Albert Vega Camacho
 */
public class Servidor {
    public static void main(String [] args) {
        VentanaServidor Ventana2 = new VentanaServidor();
    }
}

/**
 * Esta clase implementa los elementos de la ventana del servidor
 */
class VentanaServidor extends JFrame implements Runnable {
    public VentanaServidor() {

        setBounds(500, 300, 300, 300);

        JPanel Lamina2 = new JPanel();

        Lamina2.setLayout(new BorderLayout());

        AreaTexto = new JTextArea();

        Lamina2.add(AreaTexto,BorderLayout.CENTER);

        add(Lamina2);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
        
        Thread Hilo1 = new Thread(this);

        Hilo1.start();
        }
    
        /**
         * Esta clase gestiona los datos que llegaron al servidor del cliente
         */
    public void run() {  
        try {
            ServerSocket Socket_de_Servidor = new ServerSocket(878);

            String Precio, Peso, Impuesto;

            Paquetes paquete_recibido = new Paquetes();

            while(true){

                Socket SocketAceptador = Socket_de_Servidor.accept();

                ObjectInputStream datos_entrada = new ObjectInputStream(SocketAceptador.getInputStream());

                paquete_recibido = (Paquetes) datos_entrada.readObject();  
                
                Precio = paquete_recibido.getPrecio();
                Peso = paquete_recibido.getPeso();
                Impuesto = paquete_recibido.getImpuesto();

                AreaTexto.append("Precio del producto = "+ Precio+"\n"+"Peso del producto = "+Peso+"\n"+"Impuesto agregado = "+Impuesto+"\n");

                Socket Mensajero = new Socket("192.168.0.15", 9090);

                ObjectOutputStream datos_reenviados = new ObjectOutputStream(Mensajero.getOutputStream());

                datos_reenviados.writeObject(paquete_recibido);

                Mensajero.close();

                SocketAceptador.close();
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    public JTextArea AreaTexto;
}
