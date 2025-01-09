//Elo
package TrabalhoFinal;

public class Elo
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

    public Elo(int chave, int dado, Elo prox)
    {
        this.chave = chave;
        this.dado = dado;
        this.prox = prox;
    }

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
