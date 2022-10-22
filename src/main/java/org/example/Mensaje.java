package org.example;

public class Mensaje {

   private Header cabecera;

    public Mensaje(int tipo) {
        if(tipo==1){
            this.cabecera= new Header(tipo); //construye la cabecera de un mensaje respuesta
        }else{
            this.cabecera= new Header(); //construye la cabecera de un mensaje de tipo query
        }
    }


}
