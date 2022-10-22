package org.example;

public class Header {
    private int tipo=0;
    private int id= 0000000000000000; //este id debe de ser recibido en el paquete de datagramas
    private int z=0;
    private int RA=0;
    private int RD=0;
    private int TC=0;
    private int AA=0;
    public Header(int tipo) { //este constructor solo se usa cuando se quiere un header de tipo 1 (respuesta)
        int mask=0;
        this.tipo=tipo | mask; //devuelve 1 si es tipo 1 (response) y 0 si es tipo 0 (query), manejandolo en forma de bits
        this.z= 000 | mask; // en teoria, si no hay errores, esto deberia ser un arreglo de 3 bits en 0
    }

    public Header(){ //este constructor solo se usa cuando se quiere un header de tipo 1 (respuesta)
        int mask=0;
        this.tipo=tipo | mask; //devuelve 1 si es tipo 1 (response) y 0 si es tipo 0 (query), manejandolo en forma de bits
        this.z= 000 | mask; // en teoria, si no hay errores, esto deberia ser un arreglo de 3 bits en 0
    }



}
