package org.example;

import org.example.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MultiDNSServer {
    private static ServerSocket ServerSocket;
    private static int PORT = 53;


    public static void main(String[] args){

        try{
            System.out.println("INICIANDO EL SERVIDOR EN EL PUERTO "+PORT);
            ServerSocket= new ServerSocket(PORT);
            System.out.println("LEYENDO EN PUERTO "+ServerSocket.getLocalPort());
        }catch(Exception e){
            e.printStackTrace();
        }
        do {
            try{
                Socket socket= ServerSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                clientHandler.start(); //empezar a recoger los llamados del servidor DNS
            }catch(Exception big){
                System.out.println("Error de invocación de manejador de clientes: ");
                big.printStackTrace();
            }

        }while(true);
    }
}
