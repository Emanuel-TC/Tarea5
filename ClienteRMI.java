import java.rmi.Naming;
import java.util.Hashtable;

public class ClienteRMI {

    private static int N;
    private static MatrixABC A,B,C;
    private static float checksum;
    private static String[] nodesIP = new String[4];
    private static Hashtable<Float,Matrix> partialCMatrixes;
    private static Object lock = new Object();

    //Inner Class that represent a Thread that runs a remote method
    private static class WorkerPartialCi extends Thread{
        private MatrixInterface remote;
        private float Ci,cuartoOfA,cuartoOfB;

        public WorkerPartialCi(MatrixInterface remote, float Ci, float cuartoOfA, float cuartoOfB){
            this.remote=remote;
            this.Ci=Ci;
            this.cuartoOfA=cuartoOfA;
            this.cuartoOfB=cuartoOfB;
        }

        public void run(){
            try{
                //We get the Matrix from the remote object
                Matrix partialC=remote.MultiplicaMatrizPR(A.SeparaMatriz(cuartoOfA), B.SeparaMatriz(cuartoOfB));

                //We added it on the Hashtable
                synchronized(lock){
                    partialCMatrixes.put(Ci,partialC);
                }

            }catch(Exception e){
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }

    }

    public static void main(String[] args) throws Exception{

        //Getting dimension of matrix and nodes IP
        try {
            N=Integer.valueOf(args[0]);
            for(int i=0; i<4 ;i++){
                nodesIP[i]=args[i+1];
            }
        } catch (Exception e) {
            System.err.println("Ingrese la dimension N de las matrices y las 4 IP de los nodos servidores en orden");
            System.exit(1);
        }

        //Initializing Matrix A and B
        A= new MatrixABC(MatrixABC.matrixA, N);
        B= new MatrixABC(MatrixABC.matrixB, N);

        //Transposing Matrix B
        B.transposeSquareMatrix();

        //Initializing Hashtable to save PartialCs
        partialCMatrixes = new Hashtable<>();

        //Creating threads in order to get the Cis at the same time and not sequentially.
        WorkerPartialCi workers[] = new WorkerPartialCi[16];

       
        int Ci=1;
        for(int cuartoOfA=0; cuartoOfA<4 ; cuartoOfA++){
            MatrixInterface remote = (MatrixInterface) Naming.lookup("rmi://"+nodesIP[cuartoOfA]+"/matrix");
            //Each node will get AixB1 to AixB4
            for(int cuartoOfB=0; cuartoOfB<4 ; cuartoOfB++){
                //Remote method implemented inside the thread.
                workers[Ci-1]= new WorkerPartialCi(remote, Ci, cuartoOfA, cuartoOfB);
                workers[Ci-1].start();
                Ci++;
            }
        }

        //Barrier to wait all Ci
        for(int i=0; i<16; i++)
        workers[i].join();

        //Getting the C matrix from partialCMatrixes
        C = MatrixABC.AcomodaMatriz(partialCMatrixes);

        //Calculating checksum
        checksum = C.getChecksum();

        // Printing Checksum and Matrixes if N==16
        System.out.println("\nChecksum: "+checksum+"\n");
        if(N==16){
            System.out.println("Matriz A:\n"+A);
            System.out.println("Matriz B^T:\n"+B);
            System.out.println("Matriz C:\n"+C);
        }
    }
}
