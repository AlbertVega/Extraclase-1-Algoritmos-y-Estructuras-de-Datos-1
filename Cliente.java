import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.*;

/**
 * @author Albert Vega Camacho
 */
public class Cliente {
    
    /** 
     * @param args
     */
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
class LaminaMarcoCliente extends JPanel implements Runnable{
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

        EnviaTexto EventoObtenerTexto = new EnviaTexto();

        Boton1.addActionListener(EventoObtenerTexto);

        add(Boton1);

        Campo_de_texto = new JTextArea(12,20);

        add(Campo_de_texto);
        
        Thread Hilo2 = new Thread(this);

        Hilo2.start();
    }
    private JTextField CampoTextoPrecio;

    private JTextField CampoTextoPeso;

    private JTextField CampoTextoImpuesto;

    private JButton Boton1;

    private JTextArea Campo_de_texto;

    private class EnviaTexto implements ActionListener{
        /**
         * @param ActionEvent 
         * Esta clase implementa lo que debe ocurrir cuando el parámetro (Evento:"presionar el botón") se le sea dado 
         */
        public void actionPerformed(ActionEvent e){
            try {
                Socket Socketcliente1 = new Socket("192.168.0.15", 878);
                
                Paquetes datos = new Paquetes();

                datos.setPrecio(CampoTextoPrecio.getText());

                datos.setPeso(CampoTextoPeso.getText());
                
                datos.setImpuesto(CampoTextoImpuesto.getText());

                ObjectOutputStream paquete_de_datos = new ObjectOutputStream(Socketcliente1.getOutputStream());

                paquete_de_datos.writeObject(datos);

                Socketcliente1.close();
            
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                System.out.println(e1.getMessage());
            }
        }
    }

    /**
     * Esta clase gestiona la información que llegó al cliente del servidor
     */
    public void run() {
        try{
            ServerSocket Socket_de_cliente = new ServerSocket(9090);
            
            Paquetes datos_recibidos_de_servidor = new Paquetes();

            while(true){

                Socket SocketAceptadorCliente = Socket_de_cliente.accept();

                ObjectInputStream datos_de_entrada_cliente = new ObjectInputStream(SocketAceptadorCliente.getInputStream());

                datos_recibidos_de_servidor = (Paquetes) datos_de_entrada_cliente.readObject();

                String PrecioTemporal = datos_recibidos_de_servidor.getPrecio();
                int preciofinal = Integer.parseInt(PrecioTemporal);

                String PesoTemporal = datos_recibidos_de_servidor.getPeso();
                int pesofinal = Integer.parseInt(PesoTemporal);

                String ImpuestoTemporal = datos_recibidos_de_servidor.getImpuesto();
                int impuestofinal = Integer.parseInt(ImpuestoTemporal);

                Double MontoTemporal = ((preciofinal * impuestofinal)/100 ) + (pesofinal * (0.15));

                String MontoFinal = String.valueOf(MontoTemporal);

                Campo_de_texto.append("El monto final del producto es de " + MontoFinal+ "\n");
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}

/**
 * Esta clase gestiona los datos que deben ser transportados (Precio, Peso e Impuesto)
 */
class Paquetes implements Serializable {
    private String precio, peso, impuesto;

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }
}
