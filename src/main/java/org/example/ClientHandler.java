package org.example;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ClientHandler extends Thread implements Runnable {
    private Socket cliente;
    private DatagramPacket input;
    private DatagramSocket SocketUDP;
    private static final int UDP_SIZE = 1024;

    private static final int PUERTO_ENVIO = 53;

    private static ArrayList<String> Addresses = new ArrayList<>();
    private static ArrayList<String> Names = new ArrayList<>();
    private static ArrayList<String> Tipos = new ArrayList<>();
    private static ArrayList<String> Groups = new ArrayList<>();
    private static final InetAddress IPJAVERIANADNS;

    static {
        try {
            IPJAVERIANADNS = InetAddress.getByName("10.10.1.200");
        } catch (UnknownHostException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public ClientHandler(Socket socket, ArrayList<String> Addresses1, ArrayList<String> Names1,
                         ArrayList<String> Tipos1, ArrayList<String> Groups1) throws SocketException {
        cliente = socket;
        try {
            SocketUDP = new DatagramSocket(PUERTO_ENVIO);
        } catch (Exception e) {
            System.out.println("Fallo al abrir el socket, revisar el stack trace: ");
            e.printStackTrace();
        }
        Addresses = Addresses1;
        Names = Names1;
        Tipos = Tipos1;
        Groups = Groups1;
    }

    public void run() {
        String mensaje = "";
        System.out.println(
                "INICIO DE LECTURA POR EL SOCKET EN PUERTO " + SocketUDP.getLocalPort() + " " + cliente.getLocalPort());
        do {

            byte[] in = new byte[UDP_SIZE];
            DatagramPacket indp = new DatagramPacket(in, UDP_SIZE);
            System.out.println("Esperando envio de solicitud...");
            try {
                SocketUDP.receive(indp);
                System.out.println("Se recibió un datagrama");
                mensaje = new String(indp.getData(), 0, indp.getLength());
                // aca se mostraria el mensaje que llega por datagrama
                this.procesarMensaje(indp);
            } catch (IOException e) {
                System.out.println("Error! al leer el datagrama enviado: ");
                throw new RuntimeException(e);
            }
        } while (!mensaje.equals("Quit"));
        if (mensaje.equals("Quit")) {
            System.err.println(
                    "No se encontro la direccion para el dominio solicitado (ni en el servidor instaurado ni en el servidor de la javeriana)");
        }
    }

    public void procesarMensaje(DatagramPacket indp) throws IOException {

        // https://levelup.gitconnected.com/dns-response-in-java-a6298e3cc7d9
            //codigo adaptado del enlace anterior
            //adaptacion de la implementación del datainputstream y sus funciones

        /*
         * PASOS A SEGUIR
         * recuperar de ese string el nombre que se deea buscar
         * recuperar los datos que se quieren obtener (como el header y los demás flags
         * del paquete)
         * comparar el string de nombre encontrado con los registrados en la memoria
         * String ip= ...
         * String nombre= ...
         * String ipencontrada = GetIP(nombre);
         * crear un tipo Mensaje con los datos recuperados del id
         * enviar por el socket cliente el paquete creado
         * DatagramPacket datagramaEnviar = new DatagramPacket(respuesta.getBytes(),
         * respuesta.length(), datagramaRecibido.getAddress(),
         * datagramaRecibido.getPort());
         * SocketUDP.send(datagramaEnviar);
         */

        String mensaje = new String(indp.getData(), 0, indp.getLength());
        byte[] queries = mensaje.getBytes();
        DataInputStream query = new DataInputStream(new ByteArrayInputStream(queries));

        short Id = query.readShort(); // se usa short por lo que id es un espacio de 16 bits que se necesitan
        short flags = query.readByte();

        // PRIMARIOS EN EL ENCABEZADO
        int Qr = (flags & 0x10000000) >>> 7;
        int opCode = (flags & 0x01111000) >>> 3;
        int aa = (flags & 0x00000100) >>> 2;
        int tc = (flags & 0x00000010) >>> 1;
        int rd = flags & 0x00000001;

        // RESTANTES ENCABEZADO
        int Ra = (flags & 0x10000000) >>> 7;
        int z = (flags & 0x01110000) >>> 4;
        int Rcode = flags & 0x00001111;

        // RESTO DEL ENCABEZADO
        short QDCount = query.readShort();
        short ANCount = query.readShort();
        short NSCount = query.readShort();
        short ARCount = query.readShort();


        Header queryRecibida = new Header(1, Id, z, Ra, rd, tc, aa, Qr,opCode,QDCount,ANCount,NSCount,ARCount); //LUEGO SE PUEDE PASAR ESTA CABECERA COMO PARAMETRO
        String impresion= queryRecibida.toString();
        System.out.println(impresion);

        String QName = "";
        int recLen;
        while ((recLen = query.readByte()) > 0) {
            byte[] record = new byte[recLen];
            for (int i = 0; i < recLen; i++) {
                record[i] = query.readByte();
            }
            QName = new String(record, StandardCharsets.UTF_8);
        }
        short QTYPE = query.readShort();
        short QCLASS = query.readShort();
        System.out.println("Record: " + QName);
        System.out.println("Record Type: " + String.format("%s", QTYPE));
        System.out.println("Class: " + String.format("%s", QCLASS));
        short QType = query.readShort();
        short QClass = query.readShort();
        if(QName!=null){
            String ipencontrada= GetIP(QName);
        }else{
            System.err.println("No se cuenta con un nombre");
        }


    }

    public String GetIP(String nombre) {
        Iterator iter = Names.iterator();
        int i = 0;
        boolean encontrada = false;
        while (iter.hasNext()) {
            if (nombre.equals(iter)) {
                return Addresses.get(i); // esta parte falta comprobarla
            }
            i++;
        }
        if (!encontrada) {
            not_found(nombre);
        }
        return null;
    }

    private void not_found(String nom_no_encontrado) {

        // se supone que esta función entra cuando no se encuentre dentro del archivo
        // maestro
        DatagramPacket Mensaje = new DatagramPacket(nom_no_encontrado.getBytes(), nom_no_encontrado.length(),
                IPJAVERIANADNS, 53); // en teoria el puerto desde el rfc es el puerto 53
        try {
            this.SocketUDP.send(Mensaje);
            byte[] in = new byte[UDP_SIZE];
            DatagramPacket indp = new DatagramPacket(in, UDP_SIZE);
            System.out.println("Esperando envio de solicitud...");
                SocketUDP.receive(indp);
                System.out.println("Se recibió un datagrama");
                this.SocketUDP.send(indp);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Noo se pudo enviar el mensaje al DNS superior");
        }
    }

}
