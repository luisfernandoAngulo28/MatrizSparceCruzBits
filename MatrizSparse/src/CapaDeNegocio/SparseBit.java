package CapaDeNegocio;

/**
 *
 * @author fernando
 */
public class SparseBit {

    VectorBitGen VFC;
    double VD[];// numerador
    double VD2[];//denominador
    int Nfil;
    int Ncol;
    float ep;
    int dim;
    int Nb;

    public SparseBit(int Nfil, int Ncol, float ep) {
        Nb = 1 + (int) (Math.log(Nfil * Ncol) / Math.log(2));
        VFC = new VectorBitGen(5, Nb);
        VD = new double[5];
        VD2 = new double[5];
        this.Nfil = Nfil;
        this.Ncol = Ncol;
        this.ep = (float) ep;
        this.dim = 0;
    }

    public void Set(int Fila, int Col, double numerador, double denominador) {

        if ((Fila > Nfil) || (Col > Ncol)) {
            System.out.println("Error: fila y columna fuera de rango");
            System.exit(1);
        } else {
            int FC = (Fila - 1) * Ncol + Col;// FC es el orden numerico del valor en dicha posicion
            int Posicion = Existe(FC);//devuelve -1 cuando no existe dicho elemento caso contrario devuelve la posicion
            if ((Posicion == -1) && (numerador != ep) && (denominador != ep)) {//insertar un nuevo valor
                Redimensionar();//alarga el vector de ser necesario
                dim++;
                VFC.Insertar(FC, dim + 1);
                VD[dim] = numerador;
                VD2[dim] = denominador;
            } else 
                if (numerador != ep && denominador!=ep) {///cambiar el valor existente
                    VD[Posicion] = numerador;
                     VD2[Posicion] = denominador;
                } else {//eliminar el valor porque es elemento predominante
                    for (int i = Posicion; i < dim; i++) {
                        VFC.Insertar(VFC.Sacar(i + 2), i + 1);

                        VD[i] = VD[i + 1];  
                        VD2[i] = VD2[i + 1];
                }
                dim--;
            }
        }
    }

    public String get(int Fila, int Col) {
        int FC = (Fila - 1) * Ncol + Col;
        int Posicion = Existe(FC);
        if (Posicion != -1) {
            double x=VD[Posicion]/VD2[Posicion];
            if (x>0) {
                return "+"+VD[Posicion]+"/"+VD2[Posicion];
            }else{
               return VD[Posicion]+"/"+VD2[Posicion]; 
            }
        } else {
            return " "+ep;
        }
    }

    public int Existe(int FC) {
        int i = 1;
        while ((i <= dim + 1) && (VFC.Sacar(i) != FC)) {
            i++;
        }
        if (i > dim + 1) {
            return -1;
        } else {
            return i - 1;
        }
    }

    public void Redimensionar() {
        if (dim == VD.length - 1) {
            int VFCA[] = new int[dim + 1];
            double VDA[] = new double[dim + 1];
            System.arraycopy(VD, 0, VDA, 0, VD.length);
            for (int i = 0; i <= dim; i++) {
                VFCA[i] = VFC.Sacar(i + 1);
            }
            VD = new double[dim + 11];
            VFC = new VectorBitGen(dim + 12, Nb);
            System.arraycopy(VDA, 0, VD, 0, VDA.length);
            for (int i = 0; i <= dim; i++) {
                VFC.Insertar(VFCA[i], i + 1);
            }
        }

    }
    
    public void insertaFraccionCruz(int fila,int columna,double numerador,double denominador){
        int mitadFila=(Nfil/2)+1;
        int mitadColumna=(Ncol/2)+1;
        if ((fila==mitadFila)||( columna==mitadColumna)) {
            Set(fila, columna, numerador, denominador);
        }
  
    }
    
    
    
    
    

    @Override
    public String toString() {
        String S = "";
        for (int i = 1; i <= Nfil; i++) {
            for (int j = 1; j <= Ncol; j++) {
                S = S + get(i, j) + "  ";
            }
            S = S + "\n";
        }
        return S;
    }

    public static void main(String[] args) {
        SparseBit A = new SparseBit(5, 5, 0);
        System.out.println(A);
       
        System.out.println(A);
        A.insertaFraccionCruz(3, 3, -2,3);
        A.insertaFraccionCruz(5, 3, 5,3);
        System.out.println(A);
        A.insertaFraccionCruz(1, 3, 7.5,5.7);
        System.out.println(A);

    }

}
