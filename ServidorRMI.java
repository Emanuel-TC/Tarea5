
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServidorRMI extends UnicastRemoteObject implements MatrixInterface{

    public ServidorRMI() throws RemoteException{
        super();
    }

    public static void main(String[] args) throws Exception{
        String url = "rmi://localhost/matrix";
        ServidorRMI obj = new ServidorRMI();
        Naming.rebind(url, obj);
        System.out.println("Servidor de Multiplicaci\u00F3n de Matrices Listo...");
    }

    //*Remote metho
    @Override
    public Matrix MultiplicaMatrizPR(Matrix m1, Matrix m2) throws RemoteException {
        float[][] a1 = m1.matrixArray;
        float[][] a2 = m2.matrixArray;

        float[][] temp = new float[m1.matrixArray.length][m2.matrixArray.length];

        for (int i = 0; i < m1.rows; i++)
            for (int j = 0; j < m2.rows; j++)
                for (int k = 0; k < a1[0].length; k++)
                    temp[i][j] += a1[i][k] * a2[j][k];

        return new Matrix(temp);
    }

}