package modelo;

public class CartaSorteAzar {
    private String mensagem;
    private int valor;
    private boolean positiva;

    public CartaSorteAzar(String mensagem, int valor, boolean positiva) {
        this.mensagem = mensagem;
        this.valor = valor;
        this.positiva = positiva;
    }

    public String getMensagem() {
        return mensagem;
    }

    public int getValor() {
        return valor;
    }

    public boolean isPositiva() {
        return positiva;
    }
}
