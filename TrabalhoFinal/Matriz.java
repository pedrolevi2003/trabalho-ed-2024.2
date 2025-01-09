//Matriz
package TrabalhoFinal;

public class Matriz {
    int nLinhas;
    int nColunas;
    int[][] matriz = new int[nLinhas][nColunas];

    public Matriz(int nLinhas, int nColunas) {
        this.nLinhas = nLinhas;
        this.nColunas = nColunas;

        matriz = new int[nLinhas][nColunas];

        for (int l = 0; l < nLinhas; l++)
            for (int c = 0; c < nColunas; c++)
                matriz[l][c] = 0;
    }

    public Matriz(int nLinhas, int nColunas, int[][] matriz) {
        this.nLinhas = nLinhas;
        this.nColunas = nColunas;
        this.matriz = matriz;
    }



    public Matriz(int nLinhas, int nColunas, MatrizEncadeada matrizEncadeada) {
        this.nLinhas = nLinhas;
        this.nColunas = nColunas;

        for(int l = 0; l < nLinhas; l++){
            Elo p = matrizEncadeada.linhas[l].prim;
            for(int c = 0; c < nColunas; c++) {
                if(p == null || p.chave != c)
                    matriz[l][c] = 0;

                else { //só ocorre p for não nulo e tiver a mesma chave que a coluna sendo analisada
                    matriz[l][c] = p.dado;
                    p = p.prox;
                }
            }
        }
    }

    //1
    public void inserir(int linha, int coluna, int valor){
        if((linha < 0 || linha > nLinhas) || (coluna < 0) || coluna > nColunas) return;

        matriz[linha][coluna] = valor;
    }

    //2
    public void remover(int linha, int coluna){
        if((linha < 0 || linha > nLinhas) || (coluna < 0) || coluna > nColunas) return;

        matriz[linha][coluna] = 0;
    }

    //3
    public Integer busca(int linha, int coluna){
        if((linha < 0 || linha > nLinhas) || (coluna < 0) || coluna > nColunas) return null;

        return matriz[linha][coluna];
    }

    //4
    public void imprime(){
        for (int l = 0; l < nLinhas; l++) {
            String impressaoLinha = "";
            for (int c = 0; c < nColunas; c++)
                impressaoLinha += matriz[l][c] + " ";
            System.out.println(impressaoLinha);
        }
    }

    //5
    public Matriz criarVazia(){
        Matriz matrizVazia = new Matriz(nLinhas, nColunas);
        matrizVazia.imprime();
        return matrizVazia;
    }

    //6
    public boolean isVazia() {
        for (int l = 0; l < nLinhas; l++)
            for (int c = 0; c < nColunas; c++)
                if(matriz[l][c] != 0) return false;
        return true;
    }

    //7
    public boolean isDiagonal(){
        boolean isDiagonalComElemento = false;

        for (int l = 0; l < nLinhas; l++)
            for (int c = 0; c < nColunas; c++)
                if(matriz[l][c] != 0){
                    if(l == c) isDiagonalComElemento = true;
                    else return false;
                }
        return isDiagonalComElemento;
    }

    //8
    public boolean isLinha(){
        boolean is1LinhaComElemento = false;

        for (int l = 0; l < nLinhas; l++)
            for (int c = 0; c < nColunas; c++)
                if(matriz[l][c] != 0) {
                    if(!is1LinhaComElemento){
                        is1LinhaComElemento = true;
                        c = 0;
                        l++;
                    }
                    else
                        return false;
                }
        return is1LinhaComElemento;
    }

    //9
    public boolean isColuna(){
        boolean is1ColunaComElemento = false;

        for (int c = 0; c < nColunas; c++)
            for (int l = 0; l < nLinhas; l++) {
                if(matriz[l][c] != 0) {
                    if(!is1ColunaComElemento) {
                        is1ColunaComElemento = true;
                        l = 0;
                        c++;
                    }
                    else
                        return false;
                }
            }
        return is1ColunaComElemento;
    }

    //10
    public boolean isTriangularInferior(){
        boolean isElementosabaixoDiagonal = false;

        for (int l = 0; l < nLinhas; l++)
            for (int c = 0; c < nColunas; c++) {
                if(matriz[l][c] != 0){
                    if(l >= c){
                        isElementosabaixoDiagonal = true;
                    }
                    else
                        return false;
                }
            }
        return isElementosabaixoDiagonal;
    }

    //11
    public boolean isTriangularSuperior(){
        boolean isElementosacimaDiagonal = false;

        for (int l = 0; l < nLinhas; l++)
            for (int c = 0; c < nColunas; c++) {
                if(matriz[l][c] != 0){
                    if(l <= c){
                        isElementosacimaDiagonal = true;
                    }
                    else
                        return false;
                }
            }
        return isElementosacimaDiagonal;
    }

    //12
    public boolean isSimetrica(){
        for (int l = 0; l < nLinhas; l++)
            for (int c = 0; c < nColunas; c++)
                if(matriz[l][c] != matriz[c][l]) return false;
        return true;
    }

    //13
    public Matriz obterSoma(Matriz matrizB){
        if(nLinhas != matrizB.nLinhas || nColunas != matrizB.nColunas) return null;

        int[][] matrizSoma = new int[nLinhas][nColunas];
        for (int l = 0; l < nLinhas; l++)
            for (int c = 0; c < nColunas; c++)
                matrizSoma[l][c] = matriz[l][c] + matrizB.matriz[l][c];
        return new Matriz(nLinhas, nColunas, matrizSoma);
    }

    //14
    public Matriz obterProduto(Matriz matrizB) {
        if(nColunas != matrizB.nLinhas) return null;

        int[][] matrizProduto = new int[nLinhas][matrizB.nColunas];
        for (int l1 = 0; l1 < nLinhas; l1++)
            for (int c2 = 0; c2 < matrizB.nColunas; c2++)
                for (int lc = 0; lc < matrizB.nColunas; lc++)
                    matrizProduto[l1][c2] += matriz[l1][lc] * matrizB.matriz[lc][c2];
        return new Matriz(nColunas, nLinhas, matrizProduto);
    }

    //15
    public Matriz obterTransposta() {
        int[][] matrizTransposta = new int[nColunas][nLinhas];

        for (int l = 0; l < nLinhas; l++)
            for (int c = 0; c < nColunas; c++)
                matrizTransposta[l][c] = matriz[c][l];
        return new Matriz(nColunas, nLinhas, matrizTransposta);
    }
}
