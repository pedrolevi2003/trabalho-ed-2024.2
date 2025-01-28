//MatrizEncadeada
package trabalhoFinal2;
import java.util.Random;

import trabalhoFinal.ListaOrdenada;
public class MatrizEncadeada {
    int nLinhas;
    int nColunas;
    Elo[] linhas/* = new Elo[nLinhas]*/;
	//linhas[0] = LISTA{1, 2, 3, 5, null}  //lista.prim
	//linhas[1] = LISTA{2, 3, 7, null}

	//linhas[0] = {1}    -> 2 -> 3 -> 5 -> null   //linhas[0].prox
	//linhas[1] = {2}    -> 3 -> 7 -> null

	

    protected class Elo
	{
		protected int dado;
        	protected int chave;
		protected Elo prox;
		
		public Elo()
		{
			prox = null;
		}
        
        //
		public Elo(int dado)
		{
			this.dado = dado;
			prox = null;
		}
        //

        public Elo(int chave, int dado)
		{
            		this.chave = chave;
			this.dado = dado;
			prox = null;
		}

		//public Elo(int chave, int dado, Elo prox)
		//{
            	//this.chave = chave;
		//	this.dado = dado;
		//	this.prox = prox;
		//}

        //public Elo(Elo copia){
        //    this.chave = copia.chave;
		//	this.dado = copia.dado;
		//	prox = null;
        //}
		
		//public Elo(int elem, Elo proxElem)
		//{
		//	dado = elem;
		//	prox = proxElem;
		//}
	}

    public MatrizEncadeada(int nLinhas, int nColunas) {
        this.nLinhas = nLinhas;
        this.nColunas = nColunas;
        /**/this.linhas = new Elo[nLinhas];
	for(int i = 0; i < nLinhas; i++)
		linhas[i] = null;
    }

    public MatrizEncadeada(int nLinhas, int nColunas, float esparsialidade) { //esparcialidade recebe um valor entre 0 e 1 
        this.nLinhas = nLinhas;
        this.nColunas = nColunas;
        linhas = new Elo[nLinhas];


        
        int nElementos = nLinhas * nColunas;
        int nElementosNulos = (int) (nElementos * esparsialidade);
        int nElementosNaoNulos = nElementos - nElementosNulos;

        Random randomizador = new Random();
        for (int l = 0; l < nLinhas; l++) 
            for (int c = nColunas - 1; c >= 0; c--) {
                if(nElementosNulos != 0 && (nElementosNaoNulos == 0 || randomizador.nextInt(100) < (esparsialidade * 100))) 
                    nElementosNulos--;                
                else {
                    nElementosNaoNulos--;
                    Elo p = new Elo(c, (randomizador.nextInt(2) == 0? -1:1) * (randomizador.nextInt(99) + 1));
                    p.prox = linhas[l]/*.prim*/;
                    linhas[l]/*.prim*/ = p;
                }
            }
    }

    public MatrizEncadeada(int nLinhas, int nColunas, int[][] matriz) {
        this.nLinhas = nLinhas;
        this.nColunas = nColunas;
        
        for (int l = 0; l < nLinhas; l++) 
            for(int c = nColunas - 1; c >= 0; c--){
                int valor = matriz[l][c];
                if(valor != 0){
                    Elo p = new Elo(c, valor);
                    p.prox = linhas[l];
                    linhas[l] = p;
                }
            }                    
    }

    public MatrizEncadeada(int nLinhas, int nColunas, Elo[] linhas) {
        this.nLinhas = nLinhas;
        this.nColunas = nColunas;
        this.linhas = new Elo[nLinhas];
        //this.linhas = linhas;
        for (int l = 0; l < linhas.length; l++) 
            this.linhas[l] = linhas[l];
    }



    //1
    public boolean insere(int linha, int coluna, int valor){
        if((linha < 0 || linha > nLinhas - 1) || (coluna < 0 || coluna > nColunas - 1)) return false;

        if(valor == 0) return remove(linha, coluna);

        //linhas[linha].insere(new Elo(coluna, valor)); //insere(Elo novo) de ListaOrdenada
        inseridor(new Elo(coluna, valor), linha);
        return true;
    }
    private void inseridor(Elo novo, int l)
	{
		Elo p, q;
		Elo ant = null;
		
		q = new Elo(novo.chave, novo.dado);
		
		for (p = linhas[l]; ((p != null) && (p.chave < novo.chave)); p = p.prox)
			ant = p;
		
		if (ant == null) //p vale prim, sendo prim null ou depois do q
			linhas[l] = q;
		else
			ant.prox = q;
		
		//q.prox = p;

        if(p != null && p.chave == novo.chave)
            q.prox = p.prox; //se o p for nao nulo e de mesma chave, ele é "apagado" da lista, sendo seu sucessor apontado por q
        else
            q.prox = p; //se o p for null ou tiver chave diferente, ele continua na lista, sendo apontado por q
	}




    //2
    public boolean remove(int linha, int coluna){
        if((linha < 0 || linha > nLinhas - 1) || (coluna < 0 || coluna > nColunas - 1)) return false;

        //linhas[linha].remove(coluna); //remove(int chave) de ListaOrdenada
        removedor(coluna, linha);
	return true; //se o elemento ja valia 0, ele continua sendo 0 e diz-se que a remocao foi um sucesso
    }
    private boolean removedor(int chave, int l)
	{
		Elo p;
		Elo ant = null; /* refer�ncia para anterior */
		
		for(p = linhas[l]; ((p != null) && (p.chave < chave)); p = p.prox)
			ant = p;
		
		/* Se p � null ou p.dado != elem, ent�o n�o encontrou elemento. */
		if ((p == null) || (p.chave != chave))
			return false;
		
		if (p == linhas[l])
        linhas[l] = linhas[l].prox; /* Remove elemento do in�cio. */
		else
			ant.prox = p.prox;  /* Remove elemento do meio. */
		
		/* Remove a �ltima refer�ncia para o elo a ser removido. Dessa forma,
		 * o Garbage Collector ir� liberar essa mem�ria. */
		p = null;
		
		return true;
	}

    //3
    public Integer busca(int linha, int coluna){
        if((linha < 0 || linha > nLinhas - 1) || (coluna < 0 || coluna > nColunas - 1)) return null;

        //return linhas[linha].busca(coluna); //busca(int chave) de ListaOrdenada
        return buscador(coluna, linha);
    }
    private Integer buscador(int chave, int l)
	{
		Elo p;
		
		for(p = linhas[l]; p != null; p = p.prox)
		{
			if(p.chave == chave) return p.dado;
			if(p.chave > chave) return 0;

		}
		
		return 0;
	}

    //4
    public void imprime(){ 
        for(int l = 0; l < nLinhas; l++)  {
            String impressaoLinha = "";
            Elo p = linhas[l]/*.prim*/;
		

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
        //matrizVazia.imprime();
        return matrizVazia;
    }

    //6
    public boolean isVazia(){
        for (int l = 0; l < nLinhas; l++) 
            if(linhas[l]/*.prim*/ != null) return false;            
        return true;
    }
	
	//7
    public boolean isDiagonal(){
	boolean is1DiagonalComElemento = false;
	for (int l = 0; l < nLinhas; l++){
		if(linhas[l]/*.prim*/ != null){
			if(linhas[l]/*prim*/.prox == null && linhas[l]/*.prim*/.chave == l) is1DiagonalComElemento = true;
			else return false;
		}
	}
	return is1DiagonalComElemento;
    }
	//1, 2, 3
	//0, 0, 0
	//0, 0, 0

	//0, 0, 0
	//0, 0, 6
	//0, 0, 0

	//null
	//null
	//6, null
	//null

    //8
    public boolean isLinha(){
        boolean is1LinhaComElemento = false;
        
        for (int l = 0; l < nLinhas; l++) {
            if(linhas[l]/*.prim*/ != null)
                if(!is1LinhaComElemento)
                    is1LinhaComElemento = true;
                else
                    return false; 
        }
        return is1LinhaComElemento;
    }
	//1- Ver se a linha possui apenas um elemento (prim.prox == null)
	//2- Armazenar  a coluna do primeiro elemento não nulo e compará-la com a dos demais
//int c = prim.coluna
	//0, 2, 0
	//0, 5, 0
	//0, 8, 0

	//null
	//null
	//null
	//null
	//null

    //9
    public boolean isColuna(){
        // return obterTransposta().isLinha();
        int colunaComElemento = -1;

        for (int l = 0; l < nLinhas; l++) {
            Elo colunaAnalisada = linhas[l]/*.prim*/;
            if(colunaAnalisada != null) {
                if(colunaAnalisada.prox == null && (colunaComElemento == -1 || colunaComElemento == colunaAnalisada.chave))
                    colunaComElemento = colunaAnalisada.chave;
                else 
                    return false;                
            }            
        }
        return colunaComElemento != -1;
    }
	//diagonal: linha = coluna
	//inferior: linha >= coluna ---- ! linha < coluna

	
	//1, 0, 0
	//0, 2, 0
	//0, 0, 9
	
	//1
	//5, null
	//9
	
    
    //10
    public boolean isTriangularInferior(){
        boolean isNaoVazia = false;

        for (int l = 0; l < nLinhas; l++) {
            Elo p = linhas[l]/*.prim*/;
            while(p != null){                
                if(p.chave > l) return false; //para ser triangular inferior, tem-se que c <= l
                if(p.chave < l) isNaoVazia = true;
		p = p.prox;
		
            }
        }
        return isNaoVazia;
    }

    //11
    public boolean isTriangularSuperior(){
        boolean isNaoVazia = false;

        for (int l = 0; l < nLinhas; l++) {
            Elo p = linhas[l]/*.prim*/;
		if(p != null)
		{
			if(p.chave < l) return false;
			if(p.chave > l || (p.chave == l && p.prox != null)) isNaoVazia = true;
		}
	}
	    return isNaoVazia;
    }

	// 1, 2, 3, null
	//4, 5, 6, null
	//7, 8, 9, null

	// 1, 2, 3, null
	//4, 5, 6, null
	//7, 8, 10, null

    //12
    public boolean isSimetrica(){
        return isSimilar(obterTransposta());
    }
    private boolean isSimilar(MatrizEncadeada matrizB){
        if(nLinhas != matrizB.nLinhas || nColunas != matrizB.nColunas) return false;
        
        for (int l = 0; l < nLinhas; l++) {
            Elo pA = linhas[l]/*.prim*/;
            Elo pB = matrizB.linhas[l]/*.prim*/;
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
            matrizSoma.linhas[l]/*.prim*/ = obterSomaLinha(linhas[l]/*.prim*/, matrizB.linhas[l]/*.prim*/);
        }
        return matrizSoma;        
    }
    private Elo obterSomaLinha(Elo eloA, Elo eloB){
        Elo p = null; 
        if(eloA == null && eloB == null) {return null;}

        if(eloA == null) { p = new Elo(eloB.chave, eloB.dado); p.prox = obterSomaLinha(null, eloB.prox); return p; } 
	    
        if(eloB == null) { p = new Elo(eloA.chave, eloA.dado); p.prox = obterSomaLinha(eloA.prox, null); return p; } 
	
		    

        if(eloA.chave < eloB.chave) { p = new Elo(eloA.chave, eloA.dado); p.prox = obterSomaLinha(eloA.prox, eloB); return p; }
        if(eloA.chave == eloB.chave) {
		    if(eloA.dado + eloB.dado != 0) { p = new Elo(eloA.chave/* || eloB.chave*/, eloA.dado + eloB.dado); p.prox = obterSomaLinha(eloA.prox, eloB.prox); return p; }
		    else { return obterSomaLinha(eloA.prox, eloB.prox); }
	    } 
        if(eloA.chave > eloB.chave) { p = new Elo(eloB.chave, eloB.dado); p.prox = obterSomaLinha(eloA, eloB.prox); return p; }

        return p;
    }

    //14 
    public MatrizEncadeada obterProduto(MatrizEncadeada matrizB){
        if(nColunas != matrizB.nLinhas) return null;

        MatrizEncadeada matrizBTransposta = matrizB.obterTransposta();
        MatrizEncadeada matrizProduto = new MatrizEncadeada(nLinhas, matrizB.nColunas);
        
        for(int l = 0; l < nLinhas; l++) //percorre-se as nLinhas de A
            for(int bLT = nLinhas - 1; bLT >= 0; bLT--){ //para cada nLinha de A compara-se a uma nColuna de B (que tem o mesmo total que as nLinhas de A)
                Elo eloA = linhas[l]; // ter posição Alx
                Elo eloBT = matrizBTransposta.linhas[bLT]; // ter posição Bxlt

                int valorProduto = 0; // Acumulando o produto escalar

                while (eloA != null && eloBT != null) { //produto = cA * lB sendo cA = lB => produto = cA * ctB sendo cA = ctB
                if (eloA.chave == eloBT.chave) { // Se as chaves forem iguais, adiciona o produto dos dados                
                    valorProduto += eloA.dado * eloBT.dado;
                    eloA = eloA.prox; // Avança em eloA
                    eloBT = eloBT.prox; // Avança em eloBT
                } else if (eloA.chave < eloBT.chave) { // Se chave de eloA for menor, avança em eloA                    
                    eloA = eloA.prox;
                } else { // Se chave de eloBT for menor, avança em eloBT                    
                    eloBT = eloBT.prox;
    }
}
                if(valorProduto != 0) {
                    Elo p = new Elo(bLT, valorProduto);
                    p.prox = matrizProduto.linhas[l]/*.prim*/;
                    matrizProduto.linhas[l]/*.prim*/ = p;
                }
            }
        
        return matrizProduto;
    }
	//Original convencional
	//1, 2, 3
	//0, 5, 6
	//7, 8, 9

	//Transposta convencional
	//1, 0, 7
	//2, 5, 8
	//3, 6, 9

	// Para adicionar na linha 1 da trasposta 
	//1- Pega o primeiro elemento da linha 1 da original
	//2- Verifica se esse elemento tem sua coluna = 1
	//3- Se tiver coluna = 1; adiciona esse elo na linha 1 transposta E apaga esse primeiro elemento da original(linhas[1] = linhas[1].prox)
	//4- repete-se essa etapa na linha 2
	
	//Original encadeada
	//3, null
	//6, null
	//7, 8, 9, null	

	//Transposta encadeada
	//null
	//null
	//null
	
	//1, 3, null
	//4, 5, 6, null
	//7, 8, 9, null

	//2, 3, null
	//4, o, 6, null
	//7,8, 9, null

	//null
	//null
	//null

	//1
	//1 -> 2 -> 3 -> null
	//3 -> null

    //15
    public MatrizEncadeada obterTransposta(){ 
        MatrizEncadeada matrizCopia = new MatrizEncadeada(nLinhas, nColunas, linhas);
        MatrizEncadeada matrizTransposta = new MatrizEncadeada(nColunas, nLinhas);
    
        for(int lT = 0; lT < matrizTransposta.nLinhas; lT++)
            for(int l = nLinhas - 1; l >= 0; l--){
                Elo primLCopia = matrizCopia.linhas[l]/*.prim*/;
                if(primLCopia != null && primLCopia.chave == lT/**/){ //Lt(k, x) = x =Apos percorrer e adicionar todos os c = x> Lt(k, x + 1) = x + 1;    ; Lt(k, x) = x + 1 NÃO EXISTE;    ;Lt(k, x + 1) = x =Apos percorrer e adicionar todos os c = x> Lt(k, x + 1) = x +1         Considerando que lT = x: Se c vale x; adiciona esse c e o progride ao c = x+1 e vai ao proximo c. Se c vale x + 1, nao o progride . Se todos os c forem analisados progride-se ao lT = x + 1
                    Elo p = new Elo(l, primLCopia.dado);
                    p.prox = matrizTransposta.linhas[lT]/*.prim*/;
                    matrizTransposta.linhas[lT]/*.prim*/ = p;
    
                    matrizCopia.linhas[l]/*.prim*/ = matrizCopia.linhas[l]/*.prim*/.prox;
                }
            }
        return matrizTransposta;        
    }

    public void testarConstrutor(int nLinhas){
        //Random randomizador = new Random();
        Long t0, t1, tF;
        MatrizEncadeada matrizEncadeada = null;
        
        t0 = System.currentTimeMillis();		
        for (int i = 0; i < 10; i++) 
            matrizEncadeada = new MatrizEncadeada(nLinhas, nLinhas, 0.6f);
        t1 = System.currentTimeMillis();		
        tF = (t1 - t0) / 10;
        System.out.println("Construtor " + nLinhas+"x"+nLinhas+ "   " + tF);
    }

    public void testarInsere(int nLinhas){
        Random randomizador = new Random();
	int linhaDesejada = randomizador.nextInt(nLinhas - 1);
	int colunaDesejada = randomizador.nextInt(nLinhas - 1);
	int valorInserido = randomizador.nextInt(99) + 1;
	    
        Long t0, t1, tF;
        MatrizEncadeada matrizEncadeada = new MatrizEncadeada(nLinhas, nLinhas, 0.6f);
        
        t0 = System.currentTimeMillis();		
        for (int i = 0; i < 10; i++) 
            matrizEncadeada.insere(linhaDesejada, colunaDesejada, valorInserido);
        t1 = System.currentTimeMillis();		
        tF = (t1 - t0) / 10;
        System.out.println("Insere " + nLinhas+"x"+nLinhas+ "   " + tF);
    }

    public void testarRemove(int nLinhas){
        Random randomizador = new Random();
	int linhaDesejada = randomizador.nextInt(nLinhas - 1);
	int colunaDesejada = randomizador.nextInt(nLinhas - 1);
	    
        Long t0, t1, tF;
        MatrizEncadeada matrizEncadeada = new MatrizEncadeada(nLinhas, nLinhas, 0.6f);
        
        t0 = System.currentTimeMillis();		
        for (int i = 0; i < 10; i++) 
            matrizEncadeada.remove(linhaDesejada, colunaDesejada);
        t1 = System.currentTimeMillis();		
        tF = (t1 - t0) / 10;
        System.out.println("Remove " + nLinhas+"x"+nLinhas+ "   " + tF);
    }

    public void testarBusca(int nLinhas){
        Random randomizador = new Random();
	int linhaDesejada = randomizador.nextInt(nLinhas - 1);
	int colunaDesejada = randomizador.nextInt(nLinhas - 1);
	    
        Long t0, t1, tF;
        MatrizEncadeada matrizEncadeada = new MatrizEncadeada(nLinhas, nLinhas, 0.6f);
        
        t0 = System.currentTimeMillis();		
        for (int i = 0; i < 10; i++) 
            matrizEncadeada.busca(linhaDesejada, colunaDesejada);
        t1 = System.currentTimeMillis();		
        tF = (t1 - t0) / 10;
        System.out.println("Busca " + nLinhas+"x"+nLinhas+ "   " + tF);
    }

    public void testarConstrutorVazia(int nLinhas){
        //Random randomizador = new Random();
        Long t0, t1, tF;
        MatrizEncadeada matrizEncadeada = null;
        
        t0 = System.currentTimeMillis();		
        for (int i = 0; i < 10; i++) 
            matrizEncadeada = new MatrizEncadeada(nLinhas, nLinhas);
        t1 = System.currentTimeMillis();		
        tF = (t1 - t0) / 10;
        System.out.println("Construtor vazia " + nLinhas+"x"+nLinhas+ "   " + tF);
    }

    public void testarIsVazia(int nLinhas){
        //Random randomizador = new Random();
        Long t0, t1, tF;
        MatrizEncadeada matrizEncadeada = new MatrizEncadeada(nLinhas, nLinhas, 0.6f);

        t0 = System.currentTimeMillis();		
        for (int i = 0; i < 10; i++) 
            matrizEncadeada.isVazia();
        t1 = System.currentTimeMillis();		
        tF = (t1 - t0) / 10;
        System.out.println("isVazia " + nLinhas+"x"+nLinhas+ "   " + tF);
    }

    public void testarIsDiagonal(int nLinhas){
        //Random randomizador = new Random();
        Long t0, t1, tF;
        MatrizEncadeada matrizEncadeada = new MatrizEncadeada(nLinhas, nLinhas, 0.6f);

        t0 = System.currentTimeMillis();		
        for (int i = 0; i < 10; i++) 
            matrizEncadeada.isDiagonal();
        t1 = System.currentTimeMillis();		
        tF = (t1 - t0) / 10;
        System.out.println("isDiagonal " + nLinhas+"x"+nLinhas+ "   " + tF);
    }

    public void testarIsColuna(int nLinhas){
        //Random randomizador = new Random();
        Long t0, t1, tF;
        MatrizEncadeada matrizEncadeada = new MatrizEncadeada(nLinhas, nLinhas, 0.6f);

        t0 = System.currentTimeMillis();		
        for (int i = 0; i < 10; i++) 
            matrizEncadeada.isColuna();
        t1 = System.currentTimeMillis();		
        tF = (t1 - t0) / 10;
        System.out.println("isColuna " + nLinhas+"x"+nLinhas+ "   " + tF);
    }

    public void testarIsTriangularInferior(int nLinhas){
        //Random randomizador = new Random();
        Long t0, t1, tF;
        MatrizEncadeada matrizEncadeada = new MatrizEncadeada(nLinhas, nLinhas, 0.6f);

        t0 = System.currentTimeMillis();		
        for (int i = 0; i < 10; i++) 
            matrizEncadeada.isTriangularInferior();
        t1 = System.currentTimeMillis();		
        tF = (t1 - t0) / 10;
        System.out.println("isTriangInf " + nLinhas+"x"+nLinhas+ "   " + tF);
    }

    public void testarIsTriangularSuperior(int nLinhas){
        //Random randomizador = new Random();
        Long t0, t1, tF;
        MatrizEncadeada matrizEncadeada = new MatrizEncadeada(nLinhas, nLinhas, 0.6f);

        t0 = System.currentTimeMillis();		
        for (int i = 0; i < 10; i++) 
            matrizEncadeada.isTriangularSuperior();
        t1 = System.currentTimeMillis();		
        tF = (t1 - t0) / 10;
        System.out.println("isTriangSup " + nLinhas+"x"+nLinhas+ "   " + tF);
    }

    public void testarIsSimetrica(int nLinhas){
        //Random randomizador = new Random();
        Long t0, t1, tF;
        MatrizEncadeada matrizEncadeada = new MatrizEncadeada(nLinhas, nLinhas, 0.6f);

        t0 = System.currentTimeMillis();		
        for (int i = 0; i < 10; i++) 
            matrizEncadeada.isSimetrica();
        t1 = System.currentTimeMillis();		
        tF = (t1 - t0) / 10;
        System.out.println("isSimetrica " + nLinhas+"x"+nLinhas+ "   " + tF);
    }

    public void testarObterSoma(int nLinhas){
        //Random randomizador = new Random();
        Long t0, t1, tF;
        MatrizEncadeada matrizEncadeada = new MatrizEncadeada(nLinhas, nLinhas, 0.6f);
        MatrizEncadeada matrizAux = new MatrizEncadeada(nLinhas, nLinhas, 0.6f);

        t0 = System.currentTimeMillis();		
        for (int i = 0; i < 10; i++) 
            matrizEncadeada.obterSoma(matrizAux);
        t1 = System.currentTimeMillis();		
        tF = (t1 - t0) / 10;
        System.out.println("Soma " + nLinhas+"x"+nLinhas+ "   " + tF);
    }

    public void testarObterProduto(int nLinhas){
        //Random randomizador = new Random();
        Long t0, t1, tF;
        MatrizEncadeada matrizEncadeada = new MatrizEncadeada(nLinhas, nLinhas, 0.6f);
        MatrizEncadeada matrizAux = new MatrizEncadeada(nLinhas, nLinhas, 0.6f);

        t0 = System.currentTimeMillis();		
        for (int i = 0; i < 10; i++) 
            matrizEncadeada.obterProduto(matrizAux);
        t1 = System.currentTimeMillis();		
        tF = (t1 - t0) / 10;
        System.out.println("Produto " + nLinhas+"x"+nLinhas+ "   " + tF);
    }

    public void testarObterTransposta(int nLinhas){
        //Random randomizador = new Random();
        Long t0, t1, tF;
        MatrizEncadeada matrizEncadeada = new MatrizEncadeada(nLinhas, nLinhas, 0.6f);
        

        t0 = System.currentTimeMillis();		
        for (int i = 0; i < 10; i++) 
            matrizEncadeada.obterTransposta();
        t1 = System.currentTimeMillis();		
        tF = (t1 - t0) / 10;
        System.out.println("Transposta " + nLinhas+"x"+nLinhas+ "   " + tF);
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
