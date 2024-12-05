package modelo;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Casa {
    private String nome;
    private String tipo;
    private String corDono;
    private int id;
    private int nivel;
    private int valor;
    private boolean comprada;

    @JsonIgnore
    private Jogador dono;

    public Casa() {

    }

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
        this.corDono = dono.getCor();
        this.comprada = true;
    }

    public String getCorDono() {
        return this.corDono;
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
