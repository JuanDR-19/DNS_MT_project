package org.example;

public class Header {
    private int tipo=0;
    private int id= 0000000000000000; //este id debe de ser recibido en el paquete de datagramas
    private int z=0;
    private int opCode=0;
    private int RD=0;
    private int TC=0;
    private int AA=0;
    private int QR=0;
    private int RA = 0;
    private short qdcount= 0;
    private short ancount= 0;
    private short nscount= 0;
    private short arcount= 0;

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

    public Header(int tipo, int id1, int z1, int RA1, int RD1, int TC1, int AA1, int Qr1, int opCode1, short qdcount1 ,short ancount1 , short nscount1, short arcount1) {
        this.tipo = tipo;
        this.id = id1;
        this.z = z1;
        this.opCode =opCode1;
        this.RD = RD1;
        this.TC = TC1;
        this.AA = AA1;
        this.QR =Qr1;
        this.RA= RA1;
        this.qdcount=qdcount1;
        this.ancount= ancount1;
        this.nscount= nscount1;
        this.arcount= arcount1;
    }

    @Override
    public String toString() {
        return "Header{" +
                "tipo=" + tipo +
                ", id=" + id +
                ", z=" + z +
                ", opCode=" + opCode +
                ", RD=" + RD +
                ", TC=" + TC +
                ", AA=" + AA +
                ", QR=" + QR +
                ", RA=" + RA +
                ", qdcount=" + qdcount +
                ", ancount=" + ancount +
                ", nscount=" + nscount +
                ", arcount=" + arcount +
                '}';
    }
}