package modelo;

public class Casa {
    private String nome;
    private String tipo;
    private int valor;
    private boolean comprada;
    private Jogador dono;
    private int id;
    private int nivel;

    public Casa(String nome, String tipo, int valor, int id) {
        this.nome = nome;
        this.tipo = tipo;
        this.valor = valor;
        this.id = id;
        this.comprada = false;
        this.dono = null;
        this.nivel = 1;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public int getValor() {
        return valor;
    }

    public boolean isComprada() {
        return comprada;
    }

    public Jogador getDono() {
        return dono;
    }

    public void setDono(Jogador dono) {
        this.dono = dono;
        this.comprada = dono != null;
    }

    public int getNivel() {
        return nivel;
    }

    public void melhorarNivel() {
        if (nivel < 3) {
            nivel++;
        }
    }

    public int calcularAluguel() {
        return (valor / 10) * nivel;
    }
}    
