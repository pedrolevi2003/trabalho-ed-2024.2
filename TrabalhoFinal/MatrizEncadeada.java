//MatrizEncadeada
package TrabalhoFinal;
public class MatrizEncadeada {
    int nLinhas;
    int nColunas;
    ListaOrdenada[] linhas = new ListaOrdenada[nLinhas];

    public MatrizEncadeada(int nLinhas, int nColunas) {
        this.nLinhas = nLinhas;
        this.nColunas = nColunas;
        //this.linhas = new ListaOrdenada[nLinhas];
    }

    public MatrizEncadeada(int nLinhas, int nColunas, int[][] matriz) {
        this.nLinhas = nLinhas;
        this.nColunas = nColunas;

        for (int l = 0; l < nLinhas; l++)
            for(int c = nColunas; c > 0; c--){
                int valor = matriz[l][c];
                if(valor != 0){
                    Elo p = new Elo(c, valor);
                    p.prox = linhas[l].prim;
                    linhas[l].prim = p;
                }
            }
    }

    public MatrizEncadeada(int nLinhas, int nColunas, ListaOrdenada[] linhas) {
        this.nLinhas = nLinhas;
        this.nColunas = nColunas;
        this.linhas= linhas;
    }



    //1
    public void insere(int linha, int coluna, int valor){
        if((linha < 0 || linha > nLinhas) || (coluna < 0 || coluna > nColunas)) return;

        linhas[linha].insere(new Elo(coluna, valor)); //insere(Elo novo) de ListaOrdenada
    }

    //2
    public void remove(int linha, int coluna){
        if((linha < 0 || linha > nLinhas) || (coluna < 0 || coluna > nColunas)) return;

        linhas[linha].remove(coluna); //remove(int chave) de ListaOrdenada
    }

    //3
    public Integer busca(int linha, int coluna){
        if((linha < 0 || linha > nLinhas) || (coluna < 0 || coluna > nColunas)) return null;

        return linhas[linha].busca(coluna); //busca(int chave) de ListaOrdenada
    }

    //4
    public void imprime(){
        for(int l = 0; l < nLinhas; l++)  {
            String impressaoLinha = "";
            Elo p = linhas[l].prim;
            for (int c = 0; c < nColunas; c++) {
                if(p == null || c != p.chave)
                    impressaoLinha += "0 ";
                else{
                    impressaoLinha += p.dado + " ";
                    p = p.prox;
                }
            }
            System.out.println(impressaoLinha);
        }
    }

    //5
    public MatrizEncadeada criarVazia(){
        MatrizEncadeada matrizVazia = new MatrizEncadeada(nLinhas, nColunas);
        matrizVazia.imprime();
        return matrizVazia;
    }

    //6
    public boolean isVazia(){
        for (int l = 0; l < nLinhas; l++)
            if(linhas[l].prim != null) return false;
        return true;
    }

    //7
    public boolean isDiagonal(){
        for (int l = 0; l < nLinhas; l++)
            if(linhas[l].prim.chave != l) return false;
        return true;
    }

    //8
    public boolean isLinha(){
        boolean is1LinhaComElemento = false;

        for (int l = 0; l < nLinhas; l++) {
            if(linhas[l].prim != null)
                if(!is1LinhaComElemento)
                    is1LinhaComElemento = true;
                else
                    return false;
        }
        return is1LinhaComElemento;
    }

    //9
    public boolean isColuna(){
        // return obterTransposta().isLinha();
        int colunaComElemento = -1;

        for (int l = 0; l < nLinhas; l++) {
            Elo colunaAnalisada = linhas[l].prim;
            if(colunaAnalisada != null) {
                if(colunaAnalisada.prox == null && (colunaComElemento == -1 || colunaComElemento == colunaAnalisada.chave))
                    colunaComElemento = colunaAnalisada.chave;
                else
                    return false;
            }
        }
        return colunaComElemento != -1;
    }

    //10
    public boolean istriangularInferior(){
        boolean isNaoVazia = false;

        for (int l = 0; l < nLinhas; l++) {
            Elo p = linhas[l].prim;
            while(p != null){
                if(p.chave > l) return false; //para ser triangular inferior, tem-se que c <= l
                p = p.prox;
                isNaoVazia = true;
            }
        }
        return isNaoVazia;
    }

    //11
    public boolean istriangularSuperior(){
        boolean isNaoVazia = false;

        for (int l = 0; l < nLinhas; l++) {
            Elo p = linhas[l].prim;
            while(p != null){
                if(p.chave < l) return false; //para ser triangular superior, tem-se que c >= l
                p = p.prox;
                isNaoVazia = true;
            }
        }
        return isNaoVazia;
    }

    //12
    public boolean isSimetrica(MatrizEncadeada matrizB){
        return isSimilar(obterTransposta());
    }
    private boolean isSimilar(MatrizEncadeada matrizB){
        if(nLinhas != matrizB.nLinhas || nColunas != matrizB.nColunas) return false;

        for (int l = 0; l < nLinhas; l++) {
            Elo pA = linhas[l].prim;
            Elo pB = matrizB.linhas[l].prim;
            while(pA != null && pB != null){
                if(pA == null || pB == null) return false;
                if(pA.chave != pB.chave || pA.dado != pB.dado) return false;
                pA = pA.prox;
                pB = pB.prox;
            }
        }
        return true;
    }

    //13
    public MatrizEncadeada obterSoma(MatrizEncadeada matrizB){
        if(nLinhas != matrizB.nLinhas || nColunas != matrizB.nColunas) return null;

        MatrizEncadeada matrizSoma = new MatrizEncadeada(nLinhas, nColunas);
        for (int l = 0; l < nLinhas; l++) {
            matrizSoma.linhas[l].prim = obterSomaLinha(linhas[l].prim, matrizB.linhas[l].prim);
        }
        return matrizSoma;
    }
    private Elo obterSomaLinha(Elo eloA, Elo eloB){
        if(eloA == null && eloB == null) return null;
        Elo p = null;

        if(eloA == null) { p = new Elo(eloB.chave, eloB.dado, obterSomaLinha(null, eloB.prox)); }
        if(eloB == null) { p = new Elo(eloA.chave, eloA.dado, obterSomaLinha(eloA.prox, null)); }

        if(eloA.chave < eloB.chave) { p = new Elo(eloA.chave, eloA.dado, obterSomaLinha(eloA.prox, eloB)); }
        if(eloA.chave == eloB.chave) { p = new Elo(eloA.chave, eloA.dado + eloB.dado, obterSomaLinha(eloA.prox, eloB.prox)); }
        if(eloA.chave > eloB.chave) { p = new Elo(eloB.chave, eloB.dado, obterSomaLinha(eloA, eloB.prox)); }

        return p;
    }

    //14
    public MatrizEncadeada obterProduto(MatrizEncadeada matrizB){
        if(nColunas != matrizB.nLinhas) return null;

        MatrizEncadeada matrizBTransposta = matrizB.obterTransposta();
        MatrizEncadeada matrizProduto = new MatrizEncadeada(nLinhas, nLinhas);

        for(int l = 0; l < nLinhas; l++) //percorre-se as nLinhas de A
            for(int bLT = nLinhas; bLT > 0; bLT--){ //para cada nLinha de A compara-se a uma nColuna de B (que tem o mesmo total que as nLinhas de A)
                Elo eloA = linhas[l].prim; //tera posicao Alx, sendo x = prim
                Elo eloBT = matrizBTransposta.linhas[bLT].prim; //tera posicao Bxlt, sendo x = prim de bT
                int valorProduto = 0; //Alx por Bxlt, Alx por Bxlt+1, Alx por Bxlt+2, ... Alx por Bxlt+nLinhas, Al+1x por Bxlt, Al+1x por Bxlt, ... Al+nLinhasx por Bxlt+nLinhas,
                while(eloA != null || eloBT != null){ //produto = cA * lB sendo cA = lB => produto = cA * ctB sendo cA = ctB
                    if(eloA.chave <= eloBT.chave) eloA = eloA.prox;
                    if(eloA.chave == eloBT.chave) valorProduto += eloA.dado * eloBT.dado;
                    if(eloA.chave >= eloBT.chave) eloBT = eloBT.prox;
                }
                if(valorProduto != 0) {
                    Elo p = new Elo(bLT, valorProduto);
                    p.prox = matrizProduto.linhas[l].prim;
                    matrizProduto.linhas[l].prim = p;
                }
            }

        return matrizProduto;
    }

    //15
    public MatrizEncadeada obterTransposta(){
        MatrizEncadeada matrizCopia = new MatrizEncadeada(nLinhas, nColunas, linhas);
        MatrizEncadeada matrizTransposta = new MatrizEncadeada(nColunas, nLinhas);

        for(int lT = 0; lT < nColunas; lT++)
            for(int l = nLinhas; l > 0; l--){
                Elo primLCopia = matrizCopia.linhas[l].prim;
                if(primLCopia != null && primLCopia.chave == l){ //Lt(k, x) = x =Apos percorrer e adicionar todos os c = x> Lt(k, x + 1) = x + 1;    ; Lt(k, x) = x + 1 NÃO EXISTE;    ;Lt(k, x + 1) = x =Apos percorrer e adicionar todos os c = x> Lt(k, x + 1) = x +1         Considerando que lT = x: Se c vale x; adiciona esse c e o progride ao c = x+1 e vai ao proximo c. Se c vale x + 1, nao o progride . Se todos os c forem analisados progride-se ao lT = x + 1
                    Elo p = new Elo(l, primLCopia.dado);
                    p.prox = matrizTransposta.linhas[lT].prim;
                    matrizTransposta.linhas[lT].prim = p;

                    matrizCopia.linhas[l].prim = matrizCopia.linhas[l].prim.prox;
                }
            }
        return matrizTransposta;
    }
    //public MatrizEncadeada obterTransposta(){
    //    MatrizEncadeada matrizTransposta = new MatrizEncadeada(nColunas, nLinhas);
    //
    //    for (int lT = 0; lT < nColunas; lT++)
    //        // Iterar pelas linhas da matriz original
    //        for (int l = nLinhas; l > 0; l--) {
    //            // Percorrer a lista encadeada da linha l na matriz original
    //            Elo eloChecado = linhas[l].prim;
    //            while (eloChecado != null) {
    //                // Verificar se a chave do Elo corresponde à coluna que estamos processando
    //                if (eloChecado.chave == lT) {
    //                    // Criar um novo Elo na matriz transposta para a posição correta
    //                    Elo p = new Elo(l, eloChecado.dado);
    //                   p.prox = matrizTransposta.linhas[lT].prim;
    //                    matrizTransposta.linhas[lT].prim = p;
    //                }
    //                eloChecado = eloChecado.prox;
    //            }
    //        }
    //    return matrizTransposta;
    //}
}
