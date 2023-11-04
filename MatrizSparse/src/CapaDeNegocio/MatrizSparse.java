/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaDeNegocio;

/**
 *
 * @author fernando
 */
public class MatrizSparse {

     // paso1 Atributos de la clase
    int VFilaColumna[];//Vectorde Filas y Columnas
    float VNumerador[]; //Vector de Numerador
    float VDenominador[];//Vector de Denominador
    int Nfila;  // Numero de filas
    int Ncolumna;// Numero de Columnas
    float ElementoPredominante;
    int dim; //contador de elementos 
    int mitadF;
    int mitadC;

    //paso 2 Constructor     5         5        0
    public MatrizSparse(int Nfil, int Ncol, float ep) {
        VFilaColumna = new int[10];
        VNumerador = new float[10];
        VDenominador = new float[10];
        this.Nfila = Nfil+1;//
        this.Ncolumna = Ncol+1;//
        this.ElementoPredominante = ep;
        dim = -1;
        mitadF=Nfila/2;
        mitadC=Ncolumna/2;
    }



    //A.Set(Fila,Columna,Entero,Numerador,Denominador)
    //cargar,Insertar,Set
    //                    3          3               2              5             
    
    public void Set(int fila, int Columna, float numerador,float denominador) {
        if ((fila == mitadF) || (Columna == mitadC)) {
            int FC = (fila - 1) * Ncolumna + Columna;//(3-1)*5+3=13
            int posicion = BuscarFC(FC);//devolver -1 si no tiene datos esa posicion y su posicion caso contrario

            //--Caso 1 cuando esta vacio el vector
            if ((posicion == -1)
                    && (numerador != ElementoPredominante)
                    && (denominador != ElementoPredominante)) {  //no Vacio && 2!=0
                dim++;
                VFilaColumna[dim] = FC;
                VNumerador[dim] = numerador;//2
                VDenominador[dim]=denominador;//5

            } else {
                //--Caso 2   si existe ese 13 y esta en la posicion 0
                if (numerador != ElementoPredominante && denominador != ElementoPredominante) { // 4!=0
                    VNumerador[posicion] = numerador;
                    VDenominador[posicion]=denominador;

                } else {
                    //--Caso 3
                    if ((posicion != -1)) { //posicion 0!=-1 && 0==0
                        if (numerador == ElementoPredominante&& denominador == ElementoPredominante) {

                            for (int i = posicion; i <= dim; i++) {
                                VFilaColumna[i] = VFilaColumna[i + 1];
                                VNumerador[i] = VNumerador[i + 1];
                                VDenominador[i] = VDenominador[i + 1];

                            }

                        }
                        dim--;
                    }
                }

            }

        } else {
            System.out.println("Error:Fila y Columna Fuera de Rango");
            System.exit(1);
        }
    }
    //devolver -1 si no tiene datos esa posicion y 0 caso contrario    dim=-1  

    //sin modificar los atributos de la clase
    public void insertarEncruz(int fila, int Columna, float numerador,float denominador){
        int mitadF=Nfila/2;
        int mitadC=Ncolumna/2;
        if ((fila ==mitadF) || (Columna ==Ncolumna)) {
            Set(fila, Columna, numerador, denominador);
        }
    }
    
    public int BuscarFC(int FC) {
        int i = 0;
        while ((i <= dim) && (VFilaColumna[i] != FC)) {
            i++;
        }
        if (i <= dim) {

            return i;
        } else {
            return -1;
        }
    }
    //3           3
    //     3           3

    public String Get(int fila, int Columna) {
        int FC = (fila - 1) * Ncolumna + Columna; ////(3-1)*5+3=13
        int posicion = BuscarFC(FC);  // si    int FC = (fila - 1) * Ncolumna + Columna; ////(3-1)*5+3=13es -1 es vacia esa poscion caso contrario la posicion tiene valor
        if (posicion == -1) {
            return " "+ElementoPredominante;

        } else {

            int numero=(int) (VNumerador[posicion]/VDenominador[posicion]);
            System.out.println(numero);
            if (numero>=0) {
                return "+"+VNumerador[posicion]+"/"+VDenominador[posicion];//"+"+2+"/"+5->+2/5
            }else{
                return "-"+VNumerador[posicion]+"/"+VDenominador[posicion];  // -2/5
            }
        }

    }

    @Override
    public String toString() {
        String S = "";
        for (int i = 1; i <= Nfila; i++) {
            for (int j = 1; j <= Ncolumna; j++) {
                S = S + Get(i, j) + " ";
            }
            S = S + "\n";
        }
        return S;
    }
    /*
     public void MostrarVectoresI(){
         String S = "";
         for (int i = 0; i <= VDato.length-1; i++) {
            VFilaColumna[i]=0;
            VDato[i]=0;
          }
         
    }
    public String MostrarVectores(){
         String S = "";
         for (int i = 0; i <= VDato.length-1; i++) {
           S=S+"VFilaColumna["+VFilaColumna[i]+"]  VDato["+VDato[i]+"] \n"; 
          }
         return S;
    }*/
    public static void main(String[] args) {
        MatrizSparse A = new MatrizSparse(5, 5, 0);
      //  System.out.println("Caso1");
        A.Set(1, 3, +2,5);
     //    A.Set(1, 2, 4);
     //    A.Set(2, 3, 5);
         
        //A.MostrarVectoresI();
        System.out.println(A.toString());
       // System.out.println(A.MostrarVectores());
        //System.out.println(A.MostrarVectores());
       // System.out.println("Caso2");
       // A.Set(3, 3, 4);
       // System.out.println(A.toString());
        //System.out.println("Caso3");
        //A.Set(3, 3, 0);
        // System.out.println(A.toString());

    }

    
}
