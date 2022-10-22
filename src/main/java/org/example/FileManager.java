package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class FileManager {

    public FileManager() {

    }

    public void MASTER(ArrayList<String> Addresses, ArrayList<String> Names, ArrayList<String> Tipos, ArrayList<String> Groups) {
        File arch = null;
        FileReader fr = null;
        BufferedReader br = null;
        StringTokenizer tokenizer;
        String ip;
        String Nombre;
        //obteniendo las ips del archivo maestro
        //asumiendo el archivo maestro como un documento .txt DISCUTIR CON EL GRUPO
        try {
            arch = new File(""); //define location
            fr = new FileReader(arch);
            br = new BufferedReader(fr);
            String linea;
            while ((linea = br.readLine()) != null){
                tokenizer=new StringTokenizer(linea,"\t");
                //TOKEN (\t) (ENDL = \n) (EOF= br.readLine()) == null )
                Nombre= tokenizer.nextToken();
                String tipo= tokenizer.nextToken(); //tipo A
                String grupo=tokenizer.nextToken(); //grupo IN (internet)
                ip= tokenizer.nextToken(); //casteando a inetadress

                /*
                ESTRUCTURA DEL ARCHIVO POR TRABAJAR:
                    NAME  A  IN   IP(\n)
                    .
                    .
                    .
                    (EOF)
                 */
                Tipos.add(tipo);
                Groups.add(grupo);
                Addresses.add(ip);
                Names.add(Nombre);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
