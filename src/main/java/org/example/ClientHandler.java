package org.example;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ClientHandler extends Thread implements Runnable {
    private Socket cliente;
    private DatagramPacket input;
    private DatagramSocket SocketUDP;
    private static final int UDP_SIZE=1024;

    private static final int PUERTO_ENVIO=53;
    public ClientHandler(Socket socket) throws SocketException {
        cliente=socket;
        try{
            SocketUDP= new DatagramSocket(PUERTO_ENVIO);
        }catch(Exception e){
            System.out.println("Fallo al abrir el socket, revisar el stack trace: ");
            e.printStackTrace();
        }

    }

    public void run(){
        String mensaje="";
        System.out.println("INICIO DE LECTURA POR EL SOCKET EN PUERTO "+SocketUDP.getLocalPort());
        do{

            byte[] in = new byte[UDP_SIZE];
            DatagramPacket indp = new DatagramPacket(in, UDP_SIZE);
            System.out.println("Esperando envio de solicitud...");
            try {
                SocketUDP.receive(indp);
                System.out.println("Se recibió un datagrama");
                mensaje =new String(indp.getData(),0,indp.getLength());
                System.out.println("Mensaje recibido por procesar :"+ mensaje);
                //aca se mostraria el mensaje que llega por datagrama
                this.procesarMensaje(indp);
            } catch (IOException e) {
                System.out.println("Error! al leer el datagrama enviado: ");
                throw new RuntimeException(e);
            }
        }while(!mensaje.equals("Quit"));

    }

    public void procesarMensaje(DatagramPacket indp){
        //TODO
        String mensaje =new String(indp.getData(),0,indp.getLength());
        System.out.println("Mensaje recibido por procesar :"+ mensaje); //revisar desde consola la estructura del paquete enviado
        /*
            *PASOS A SEGUIR
                *recuperar de ese string el nombre que se deea buscar
                * recuperar los datos que se quieren obtener (como el header y los demás flags del paquete)
                * comparar el string de nombre encontrado con los registrados en la memoria
                * llamar a GetIP
                * crear un tipo Mensaje con los datos recuperados del id
                * enviar por el socket cliente el paquete creado
                    *DatagramPacket datagramaEnviar = new DatagramPacket(respuesta.getBytes(), respuesta.length(), datagramaRecibido.getAddress(), datagramaRecibido.getPort());
                    * SocketUDP.send(datagramaEnviar);
         */

    }

    public String GetIP(String nombre, ArrayList<String> Names, ArrayList<String> Addresses){
        Iterator iter = Names.iterator();
        int i=0;
        boolean encontrada=false;
        while (iter.hasNext()){
            if(nombre.equals(iter)){
                encontrada=true;
                return Addresses.get(i); //esta parte falta comprobarla
            }
            i++;
        }
        if(!encontrada){
            return "Quit";
        }
        return null;
    }

}
