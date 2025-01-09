//ListaOrdenada
package TrabalhoFinal;

public class ListaOrdenada extends Lista
{
    /* Insere elemento na lista na posi��o adequada, mantendo-a ordenada. */
    public void insere(Elo novo)
    {
        Elo p, q;
        Elo ant = null;

        q = new Elo(novo.chave, novo.dado);

        for (p = prim; ((p != null) && (p.chave < novo.chave)); p = p.prox)
            ant = p;

        if (ant == null) //p vale prim, sendo prim null ou depois do q
            prim = q;
        else
            ant.prox = q;

        //q.prox = p;

        if(p != null && p.chave == novo.chave)
            q.prox = p.prox; //se o p for nao nulo e de mesma chave, ele é "apagado" da lista, sendo seu sucessor apontado por q
        else
            q.prox = p; //se o p for null ou tiver chave diferente, ele continua na lista, sendo apontado por q
    }

    /* Remove da lista o primeiro elemento com valor igual a �elem". Ret. true se removeu. */
    public boolean remove(int chave)
    {
        Elo p;
        Elo ant = null; /* refer�ncia para anterior */

        for(p = prim; ((p != null) && (p.chave < chave)); p = p.prox)
            ant = p;

        /* Se p � null ou p.dado != elem, ent�o n�o encontrou elemento. */
        if ((p == null) || (p.chave != chave))
            return false;

        if (p == prim)
            prim = prim.prox; /* Remove elemento do in�cio. */
        else
            ant.prox = p.prox;  /* Remove elemento do meio. */

        /* Remove a �ltima refer�ncia para o elo a ser removido. Dessa forma,
         * o Garbage Collector ir� liberar essa mem�ria. */
        p = null;

        return true;
    }

    public Integer busca(int chave)
    {
        Elo p;

        for(p = prim; p != null; p = p.prox)
        {
            if(p.chave == chave) return p.dado;
            if(p.chave > chave) return null;

        }

        return null;
    }

    /* M�todo para intersecao de conjuntos. Calcula intersecao do conjunto com cj2 e retorna novo conjunto com a intersecao.
     * Usa fato de conjuntos estarem ordenados e percorre as listas em paralelo. */
    public int calculaElementosComuns(ListaOrdenada lista2)
    {
        Elo p1 = prim, p2 = lista2.prim;
        int total = 0;

        while ( (p1 != null) && (p2 != null) )
        {
            if (p1.chave < p2.chave)
            {
                p1 = p1.prox;
            }
            else if(p2.chave < p1.chave)
            {
                p2 = p2.prox;
            }
            else
            {
                total++;

                p1 = p1.prox;
                p2 = p2.prox;
            }
        }

        return total;
    }
}