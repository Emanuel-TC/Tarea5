import java.io.Serializable;

public class Matrix implements Serializable{
    protected float[][] matrixArray;
    protected int rows;
    protected int columns;

    public Matrix(int rows, int columns){
        this.matrixArray= new float[rows][columns];
        this.rows=rows;
        this.columns=columns;
    }

    protected Matrix(float[][] matrixArray){
        this.matrixArray= matrixArray;
        this.rows= matrixArray.length;
        this.columns= matrixArray[0].length;
    }

    public Matrix(int N){
        this.matrixArray= new float[N][N];
        this.rows=N;
        this.columns=N;
    }

    public void transposeSquareMatrix() throws Exception{
        if(rows!=columns)
            throw new Exception("La matriz no es una matriz cuadrada", new Throwable("Filas: "+rows+" | Columnass: "+columns));

        for (int i = 0; i < rows; i++){
            for (int j = 0; j < i; j++){
                float x = this.matrixArray[i][j];
                this.matrixArray[i][j] = this.matrixArray[j][i];
                this.matrixArray[j][i] = x;
            }
        }
    }

    @Override
    public String toString(){
        String temp="";
        for(int i=0; i<rows ; i++){
            for(int j=0; j<columns; j++){
                temp+=this.matrixArray[i][j]+"\t";
            }
            temp+="\n";
        }
        return temp;
    }
}
