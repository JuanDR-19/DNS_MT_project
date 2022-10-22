package org.example;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ClientHandler extends Thread{
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

        try{
            ArrayList<String> Addresses= new ArrayList<>();
            ArrayList<String> Names= new ArrayList<>();
            FileManager FM= new FileManager();
            FM.MASTER(Addresses,Names); //ya se leen todas los registros del arreglo de texto
        }catch(Exception e2){
            System.out.println("Error al leer el archivo, revisar el stack trace: ");
            e2.printStackTrace();
        }

    }

    public void start(){
        String mensaje="";

        do{
            byte[] in = new byte[UDP_SIZE];
            DatagramPacket indp = new DatagramPacket(in, UDP_SIZE);
            System.out.println("Esperando envio de solicitud...");
            try {
                SocketUDP.receive(indp);
                System.out.println("Se recibió un datagrama");
                this.procesarMensaje(indp);
            } catch (IOException e) {
                System.out.println("Error! al leer el datagrama enviado: ");
                throw new RuntimeException(e);
            }
            mensaje=input.toString();
        }while(!mensaje.equals("Quit"));

    }

    public void procesarMensaje(DatagramPacket indp){
        //TODO
    }


}
