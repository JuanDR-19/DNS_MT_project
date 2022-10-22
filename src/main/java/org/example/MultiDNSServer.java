package org.example;

import org.example.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiDNSServer {
    private static ServerSocket ServerSocket;
    private static int PORT = 53;
    private static ArrayList<ClientHandler> ListaClientes= new ArrayList<>();
    private static ExecutorService pool= Executors.newFixedThreadPool(6); //para manejar 6 solicitudes

    public static void main(String[] args){

        try{
            ArrayList<String> Addresses= new ArrayList<>();
            ArrayList<String> Names= new ArrayList<>();
            FileManager FM= new FileManager();
            FM.MASTER(Addresses,Names); //ya se leen todas los registros del arreglo de texto
            System.out.println("INICIANDO EL SERVIDOR EN EL PUERTO "+PORT);
            Socket socket;
            ServerSocket= new ServerSocket(PORT);
            System.out.println("LEYENDO EN PUERTO "+ServerSocket.getLocalPort());
            while(true){
                try{
                    socket= ServerSocket.accept();
                    ClientHandler manejadorClientes= new ClientHandler(socket);
                    ListaClientes.add(manejadorClientes);
                    pool.execute(manejadorClientes);
                }catch(Exception sockete){
                    System.err.println("Se encontró un fallo al momento de abrir el socket de recepcion del usuario");
                    sockete.printStackTrace();
                }
            }


        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
