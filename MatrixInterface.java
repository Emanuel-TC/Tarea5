import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MatrixInterface extends Remote{
    //Remote method to multiply matrixes
    public Matrix MultiplicaMatrizPR(Matrix m1, Matrix m2) throws RemoteException;
}