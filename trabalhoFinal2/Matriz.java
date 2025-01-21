//Matriz
package trabalhoFinal2;

import java.util.Random;
/**/import trabalhoFinal2.MatrizEncadeada.Elo;

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

    public Matriz(int nLinhas, int nColunas, float esparsialidade) { //esparcialidade recebe um valor entre 0 e 1 
        this.nLinhas = nLinhas;
        this.nColunas = nColunas;
        
        int nElementos = nLinhas * nColunas;
        int nElementosNulos = (int) (nElementos * esparsialidade);
        int nElementosNaoNulos =  nElementos = nElementosNulos;

        Random randomizador = new Random();
        for (int l = 0; l < nLinhas; l++) 
            for (int c = 0; c < nColunas; c++) {
                if(nElementosNulos != 0 && (nElementosNaoNulos == 0 || randomizador.nextInt(100) < 60)) {
                    nElementosNulos--;                
                    matriz[l][c] = 0;
                }
                else {
                    nElementosNaoNulos--;
                    matriz[l][c] = randomizador.nextInt(99) + 1;
                }
            }
    }

    /**/public Matriz(int nLinhas, int nColunas, MatrizEncadeada matrizEncadeada) {
        this.nLinhas = nLinhas;
        this.nColunas = nColunas;

        for(int l = 0; l < nLinhas; l++){
            Elo p = matrizEncadeada.linhas[l]/*.prim*/;
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

    public Matriz(int nLinhas, int nColunas, int[][] matriz) {
        this.nLinhas = nLinhas;
        this.nColunas = nColunas;
        this.matriz = matriz;
    }

    //1
    public boolean insere(int linha, int coluna, int valor){
        if((linha < 0 || linha > nLinhas) || (coluna < 0) || coluna > nColunas) return false;

        matriz[linha][coluna] = valor;
        return true;
    }

    //2
    public boolean remove(int linha, int coluna){
        if((linha < 0 || linha > nLinhas) || (coluna < 0) || coluna > nColunas) return false;

        matriz[linha][coluna] = 0;
        return true;
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
        //matrizVazia.imprime();
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







    public void testarConstrutor(int nLinhas){
        //Random randomizador = new Random();
        Long t0, t1, tF;
        Matriz matriz = null;
        
        t0 = System.currentTimeMillis();		
        for (int i = 0; i < 10; i++) 
            matriz = new Matriz(nLinhas, nLinhas, 0.6f);
        t1 = System.currentTimeMillis();		
        tF = (t1 - t0) / 10;
        System.out.println("Construtor " + nLinhas+"x"+nLinhas+ "   " + tF);
    }

    public void testarInsere(int nLinhas){
        Random randomizador = new Random();
        Long t0, t1, tF;
        Matriz matriz = new Matriz(nLinhas, nLinhas, 0.6f);
        
        t0 = System.currentTimeMillis();		
        for (int i = 0; i < 10; i++) 
            matriz.insere(randomizador.nextInt(nLinhas - 1), randomizador.nextInt(nColunas - 1), randomizador.nextInt(99) + 1);
        t1 = System.currentTimeMillis();		
        tF = (t1 - t0) / 10;
        System.out.println("Insere " + nLinhas+"x"+nLinhas+ "   " + tF);
    }

    public void testarRemove(int nLinhas){
        Random randomizador = new Random();
        Long t0, t1, tF;
        Matriz matriz = new Matriz(nLinhas, nLinhas, 0.6f);
        
        t0 = System.currentTimeMillis();		
        for (int i = 0; i < 10; i++) 
            matriz.remove(randomizador.nextInt(nLinhas - 1), randomizador.nextInt(nLinhas - 1));
        t1 = System.currentTimeMillis();		
        tF = (t1 - t0) / 10;
        System.out.println("Remove " + nLinhas+"x"+nLinhas+ "   " + tF);
    }

    public void testarBusca(int nLinhas){
        Random randomizador = new Random();
        Long t0, t1, tF;
        Matriz matriz = new Matriz(nLinhas, nLinhas, 0.6f);
        
        t0 = System.currentTimeMillis();		
        for (int i = 0; i < 10; i++) 
            matriz.busca(randomizador.nextInt(nLinhas - 1), randomizador.nextInt(nLinhas - 1));
        t1 = System.currentTimeMillis();		
        tF = (t1 - t0) / 10;
        System.out.println("Busca " + nLinhas+"x"+nLinhas+ "   " + tF);
    }

    public void testarConstrutorVazia(int nLinhas){
        //Random randomizador = new Random();
        Long t0, t1, tF;
        Matriz matriz = null;
        
        t0 = System.currentTimeMillis();		
        for (int i = 0; i < 10; i++) 
            matriz = new Matriz(nLinhas, nLinhas);
        t1 = System.currentTimeMillis();		
        tF = (t1 - t0) / 10;
        System.out.println("Construtor vazia " + nLinhas+"x"+nLinhas+ "   " + tF);
    }

    public void testarIsVazia(int nLinhas){
        //Random randomizador = new Random();
        Long t0, t1, tF;
        Matriz matriz = new Matriz(nLinhas, nLinhas, 0.6f);

        t0 = System.currentTimeMillis();		
        for (int i = 0; i < 10; i++) 
            matriz.isVazia();
        t1 = System.currentTimeMillis();		
        tF = (t1 - t0) / 10;
        System.out.println("isVazia " + nLinhas+"x"+nLinhas+ "   " + tF);
    }

    public void testarIsDiagonal(int nLinhas){
        //Random randomizador = new Random();
        Long t0, t1, tF;
        Matriz matriz = new Matriz(nLinhas, nLinhas, 0.6f);

        t0 = System.currentTimeMillis();		
        for (int i = 0; i < 10; i++) 
            matriz.isDiagonal();
        t1 = System.currentTimeMillis();		
        tF = (t1 - t0) / 10;
        System.out.println("isDiagonal " + nLinhas+"x"+nLinhas+ "   " + tF);
    }

    public void testarIsColuna(int nLinhas){
        //Random randomizador = new Random();
        Long t0, t1, tF;
        Matriz matriz = new Matriz(nLinhas, nLinhas, 0.6f);

        t0 = System.currentTimeMillis();		
        for (int i = 0; i < 10; i++) 
            matriz.isColuna();
        t1 = System.currentTimeMillis();		
        tF = (t1 - t0) / 10;
        System.out.println("isColuna " + nLinhas+"x"+nLinhas+ "   " + tF);
    }

    public void testarIsTriangularInferior(int nLinhas){
        //Random randomizador = new Random();
        Long t0, t1, tF;
        Matriz matriz = new Matriz(nLinhas, nLinhas, 0.6f);

        t0 = System.currentTimeMillis();		
        for (int i = 0; i < 10; i++) 
            matriz.isTriangularInferior();
        t1 = System.currentTimeMillis();		
        tF = (t1 - t0) / 10;
        System.out.println("isTriangInf " + nLinhas+"x"+nLinhas+ "   " + tF);
    }

    public void testarIsTriangularSuperior(int nLinhas){
        //Random randomizador = new Random();
        Long t0, t1, tF;
        Matriz matriz = new Matriz(nLinhas, nLinhas, 0.6f);

        t0 = System.currentTimeMillis();		
        for (int i = 0; i < 10; i++) 
            matriz.isTriangularSuperior();
        t1 = System.currentTimeMillis();		
        tF = (t1 - t0) / 10;
        System.out.println("isTriangSup " + nLinhas+"x"+nLinhas+ "   " + tF);
    }

    public void testarIsSimetrica(int nLinhas){
        //Random randomizador = new Random();
        Long t0, t1, tF;
        Matriz matriz = new Matriz(nLinhas, nLinhas, 0.6f);

        t0 = System.currentTimeMillis();		
        for (int i = 0; i < 10; i++) 
            matriz.isSimetrica();
        t1 = System.currentTimeMillis();		
        tF = (t1 - t0) / 10;
        System.out.println("isSimetrica " + nLinhas+"x"+nLinhas+ "   " + tF);
    }

    public void testarObterSoma(int nLinhas){
        //Random randomizador = new Random();
        Long t0, t1, tF;
        Matriz matriz = new Matriz(nLinhas, nLinhas, 0.6f);
        Matriz matrizAux = new Matriz(nLinhas, nLinhas, 0.6f);

        t0 = System.currentTimeMillis();		
        for (int i = 0; i < 10; i++) 
            matriz.obterSoma(matrizAux);
        t1 = System.currentTimeMillis();		
        tF = (t1 - t0) / 10;
        System.out.println("Soma " + nLinhas+"x"+nLinhas+ "   " + tF);
    }

    public void testarObterProduto(int nLinhas){
        //Random randomizador = new Random();
        Long t0, t1, tF;
        Matriz matriz = new Matriz(nLinhas, nLinhas, 0.6f);
        Matriz matrizAux = new Matriz(nLinhas, nLinhas, 0.6f);

        t0 = System.currentTimeMillis();		
        for (int i = 0; i < 10; i++) 
            matriz.obterProduto(matrizAux);
        t1 = System.currentTimeMillis();		
        tF = (t1 - t0) / 10;
        System.out.println("Produto " + nLinhas+"x"+nLinhas+ "   " + tF);
    }

    public void testarObterTransposta(int nLinhas){
        //Random randomizador = new Random();
        Long t0, t1, tF;
        Matriz matriz = new Matriz(nLinhas, nLinhas, 0.6f);
        
        t0 = System.currentTimeMillis();		
        for (int i = 0; i < 10; i++) 
            matriz.obterTransposta();
        t1 = System.currentTimeMillis();		
        tF = (t1 - t0) / 10;
        System.out.println("Transposta " + nLinhas+"x"+nLinhas+ "   " + tF);
    }
}
    
