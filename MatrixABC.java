import java.util.Hashtable;

public class MatrixABC extends Matrix{
    public static final boolean matrixA=true;
    public static final boolean matrixB=false;
    public static final float PrimerCuarto=0;
    public static final float SegundoCuarto=1;
    public static final float TercerCuarto=2;
    public static final float CuartoCuarto=3;

    public MatrixABC(boolean matrixType, int N){
        super(N);

        if(matrixType) //Matrix A
            for(int i=0; i<N ;i++){
                for(int j=0; j<N ;j++){
                    this.matrixArray[i][j]=i+2*j;
                }
            }
        else //If Matrix B
            for(int i=0; i<N ;i++){
                for(int j=0; j<N ;j++){
                    this.matrixArray[i][j]=3*i-j;
                }
            }
    }

    private MatrixABC(float[][] arrayMatrix){
        super(arrayMatrix);
    }

    
    public Matrix SeparaMatriz(float TipoCuarto) throws Exception{
        if(TipoCuarto<0 && TipoCuarto>4)
            throw new Exception("Wrong TipoCuarto value. Expected: (0-3)",new Throwable("TipoCuarto: "+TipoCuarto));
        float CuartoMatrizArray[][] = new float[rows/4][columns];
        int i=0;
        int cuarto=rows/4;
        int type=0;

        while(type!=TipoCuarto){
            i+=rows/4;
            cuarto+=rows/4;
            type++;
        }
        //We force that last cuarto has all the columns
        if(TipoCuarto==3)
            cuarto=rows;

        for(int row=0 ; i<cuarto ; i++){
            for(int j=0; j<columns ; j++){
                CuartoMatrizArray[row][j]=this.matrixArray[i][j];
            }
            row++;
        }
        return new Matrix(CuartoMatrizArray);
    }

    public float getChecksum(){
        float checksum=0;

        for(int i=0; i<this.rows; i++){
            for(int j=0; j<this.columns; j++){
                checksum+=this.matrixArray[i][j];
            }
        }

        return checksum;
    }

    //Function equivalent to "acomoda_matriz"
    public static MatrixABC AcomodaMatriz(Hashtable<Float,Matrix> partialCs){
        int N=partialCs.get(1).columns*4;
        float matrixArrayC[][] = new float[N][N];

        int actualRow=0;
        int actualColumn=0;
        for(int Ci=1; Ci<=16 ; Ci++){
            float[][] arrayCi=partialCs.get(Ci).matrixArray;

            for(int i = 0; i < N/4 ; i++)
                for(int j = 0; j < N/4 ; j++)
                    matrixArrayC[i+actualRow][j+actualColumn] = arrayCi[i][j];

            actualColumn+=N/4;
            if(Ci%4==0){
                actualColumn=0;
                actualRow+=N/4;
            }
        }
        
        return new MatrixABC(matrixArrayC);
    }

}








