package controle;

import modelo.CartaSorteAzar;
import modelo.Casa;
import modelo.Tabuleiro;
import modelo.Jogador;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class TabuleiroController {
    private static final int TAMANHO_CASA = 80; // Tamanho de cada casa em pixels
    private static final int MARGEM_X = 50; // Margem inicial em X
    private static final int MARGEM_Y = 120; // Margem inicial em Y

    private Tabuleiro tabuleiro;
    private ArrayList<Jogador> jogadores;
    private Point[] coordenadasCasas; // Coordenadas das casas (Array de pontos)

    public TabuleiroController(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
        this.jogadores = new ArrayList<>();
        inicializarCasas();
        inicializarCartas();
        inicializarCoordenadasCasas();
    }

    public TabuleiroController(Tabuleiro tabuleiro, ArrayList<Jogador> jogadores) {
        this.tabuleiro = tabuleiro;
        this.jogadores = jogadores;
        inicializarCartas();
        inicializarCoordenadasCasas();
    }

    public ArrayList<Casa> getCasas() {
        return tabuleiro.getCasas();
    }

    public void setCasas(ArrayList<Casa> casas) {
        tabuleiro.setCasas(casas);
    }

    public ArrayList<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(ArrayList<Jogador> jogadores) {
        this.jogadores = jogadores;
    }

    public Casa getCasa(int index) {
        if (index >= 0 && index < tabuleiro.getCasas().size()) {
            return tabuleiro.getCasas().get(index);
        }
        return null;
    }

    public Casa getCasaAt(int x, int y) {
        int index = getCasaIndex(x, y);
        if (index >= 0) {
            return getCasa(index);
        }
        return null;
    }

    public int getCasaIndex(int x, int y) {
        for (int i = 0; i < coordenadasCasas.length; i++) {
            Rectangle bounds = new Rectangle(coordenadasCasas[i].x, coordenadasCasas[i].y, TAMANHO_CASA, TAMANHO_CASA);
            if (bounds.contains(x, y)) {
                return i;
            }
        }
        return -1;
    }

    // Arrumar o for porque

    private void inicializarCoordenadasCasas() {
        coordenadasCasas = new Point[40];

        int margemX = MARGEM_X;
        int margemY = MARGEM_Y;

        // Linha inferior (casas 0 a 10)
        for (int i = 0; i <= 10; i++) {
            coordenadasCasas[i] = new Point(margemX + i * TAMANHO_CASA, margemY + 9 * TAMANHO_CASA);
        }

        // Lado direito (casas 11 a 20)
        for (int i = 1; i <= 10; i++) {
            coordenadasCasas[10 + i] = new Point(margemX + 10 * TAMANHO_CASA, margemY + (9 - i) * TAMANHO_CASA);
        }

        // Linha superior (casas 21 a 30)
        for (int i = 1; i <= 10; i++) { // Corrigido para iniciar do índice 1
            coordenadasCasas[20 + i] = new Point(margemX + (10 - i) * TAMANHO_CASA, margemY - 80);
        }

        // Lado esquerdo (casas 31 a 39)
        for (int i = 1; i <= 9; i++) {
            coordenadasCasas[30 + i] = new Point(margemX, margemY - 80 + i * TAMANHO_CASA);
        }

        // Verificação para depuração
        for (int i = 0; i < coordenadasCasas.length; i++) {
            if (coordenadasCasas[i] == null) {
                System.err.println("Erro: coordenada da casa " + i + " não inicializada!");
            } else {
                System.out.println("Casa " + i + ": " + coordenadasCasas[i]);
            }
        }
    }

    private void inicializarCasas() {
        // Criar o array de casas
        ArrayList<Casa> casas = new ArrayList<Casa>(40);

        casas.add(new Casa("Início", "especial", 0, 0));
        casas.add(new Casa("Jardim Botânico", "propriedade", 150, 1));
        casas.add(new Casa("Avenida Niemeyer", "propriedade", 100, 2));
        casas.add(new Casa("Companhia Petrolífera", "propriedade", 300, 3));
        casas.add(new Casa("Avenida Beira Mar", "propriedade", 100, 4));
        casas.add(new Casa("Avenida Juscelino Kubitschek", "propriedade", 200, 5));
        casas.add(new Casa("Sorte ou Revés", "sorteAzar", 0, 6));
        casas.add(new Casa("Rua Oscar Freire", "propriedade", 150, 7));
        casas.add(new Casa("Restituição do imposto de renda", "especial", 300, 8)); // Jogador deve receber 300 reai)s
        casas.add(new Casa("Avenida Ibirapuera", "propriedade", 120, 9));
        casas.add(new Casa("Prisão (Apenas Visita)", "especial", 0, 10));
        casas.add(new Casa("Sorte ou Revés", "sorteAzar", 0, 11));
        casas.add(new Casa("Praça da Sé", "propriedade", 150, 12));
        casas.add(new Casa("Rua da Consolação", "propriedade", 140, 13));
        casas.add(new Casa("Central de Força e Luz", "propriedade", 400, 14));
        casas.add(new Casa("Viaduto do Chá", "propriedade", 200, 15));
        casas.add(new Casa("Receita Federal", "especial", 180, 16)); // Jogador perde 180 reai)s
        casas.add(new Casa("Higienópolis", "propriedade", 300, 17));
        casas.add(new Casa("Jardins", "propriedade", 300, 18));
        casas.add(new Casa("Avenida São João", "propriedade", 200, 19));
        casas.add(new Casa("Feriado", "especial", 0, 20));
        casas.add(new Casa("Avenida Ipiranga", "propriedade", 220, 21));
        casas.add(new Casa("Companhia de água e saneamento", "propriedade", 500, 22));
        casas.add(new Casa("Companhia de Mineração", "propriedade", 600, 23));
        casas.add(new Casa("Sorte ou Revés", "sorteAzar", 0, 24));
        casas.add(new Casa("Avenida Recife", "propriedade", 350, 25));
        casas.add(new Casa("Avenida Paulista", "propriedade", 350, 26));
        casas.add(new Casa("Sorte ou Revés", "sorteAzar", 0, 27));
        casas.add(new Casa("Ponte do Guaíba", "propriedade", 250, 28));
        casas.add(new Casa("Pontocom", "propriedade", 400, 29));
        casas.add(new Casa("Vá para a Prisão", "prisao", 0, 30));
        casas.add(new Casa("Praça dos Três Poderes", "propriedade", 500, 31));
        casas.add(new Casa("Sorte ou Revés", "sorteAzar", 0, 32));
        casas.add(new Casa("Praça Castro Alves", "propriedade", 500, 33));
        casas.add(new Casa("Avenida do Contorno", "propriedade", 500, 34));
        casas.add(new Casa("Ponte Rio-Niterói", "propriedade", 600, 35));
        casas.add(new Casa("Créditos de Carbono", "propriedade", 900, 36));
        casas.add(new Casa("Barra da Tijuca", "propriedade", 500, 37));
        casas.add(new Casa("Sorte ou Revés", "sorteAzar", 0, 38));
        casas.add(new Casa("Marina da Glória", "propriedade", 800, 39));

        // Define o array de casas no tabuleiro
        tabuleiro.setCasas(casas);
    }

    // Inicializa o baralho de cartas de sorte ou azar
    private void inicializarCartas() {
        ArrayList<CartaSorteAzar> cartas = new ArrayList<>();

        // Cartas de sorte (25)
        cartas.add(new CartaSorteAzar("Você encontrou uma herança inesperada! Receba $200.", 200, true));
        cartas.add(new CartaSorteAzar("Seu investimento rendeu! Receba $300.", 300, true));
        cartas.add(new CartaSorteAzar("Você encontrou dinheiro perdido! Receba $50.", 50, true));
        cartas.add(new CartaSorteAzar("Você ganhou na loteria! Receba $500.", 500, true));
        cartas.add(new CartaSorteAzar("Você vendeu uma propriedade com lucro. Receba $400.", 400, true));
        cartas.add(new CartaSorteAzar("Um parente distante te enviou $100 de presente.", 100, true));
        cartas.add(new CartaSorteAzar("Você ganhou um prêmio em um concurso! Receba $150.", 150, true));
        cartas.add(new CartaSorteAzar("Seu salário foi adiantado! Receba $250.", 250, true));
        cartas.add(new CartaSorteAzar("Você recebeu uma devolução de impostos! Receba $300.", 300, true));
        cartas.add(new CartaSorteAzar("Uma aposta deu certo. Receba $200.", 200, true));
        cartas.add(new CartaSorteAzar("Você conseguiu vender ações por um ótimo preço! Receba $350.", 350, true));
        cartas.add(new CartaSorteAzar("Você encontrou um tesouro perdido! Receba $500.", 500, true));
        cartas.add(new CartaSorteAzar("Você recebeu um bônus inesperado do trabalho! Receba $250.", 250, true));
        cartas.add(new CartaSorteAzar("Seus amigos fizeram uma vaquinha para te ajudar. Receba $150.", 150, true));
        cartas.add(new CartaSorteAzar("Você conseguiu um desconto enorme em uma compra! Receba $100.", 100, true));
        cartas.add(new CartaSorteAzar("Você foi promovido no trabalho! Receba $400.", 400, true));
        cartas.add(new CartaSorteAzar("Você ganhou um sorteio na rádio! Receba $50.", 50, true));
        cartas.add(new CartaSorteAzar("Você recuperou um investimento antigo! Receba $300.", 300, true));
        cartas.add(new CartaSorteAzar("Você ganhou uma aposta entre amigos! Receba $100.", 100, true));
        cartas.add(new CartaSorteAzar("Um cliente satisfeito te deu uma gorjeta! Receba $200.", 200, true));
        cartas.add(new CartaSorteAzar("Você recebeu dividendos das suas ações! Receba $150.", 150, true));
        cartas.add(new CartaSorteAzar("Você fez um bom negócio no mercado! Receba $250.", 250, true));
        cartas.add(new CartaSorteAzar("Você vendeu um produto raro! Receba $300.", 300, true));
        cartas.add(new CartaSorteAzar("Você recebeu um subsídio do governo! Receba $200.", 200, true));
        cartas.add(new CartaSorteAzar("Você encontrou um colecionável valioso! Receba $350.", 350, true));

        // Cartas de azar (25)
        cartas.add(new CartaSorteAzar("Você foi roubado! Perca $200.", 200, false));
        cartas.add(new CartaSorteAzar("Você teve que pagar uma multa! Perca $150.", 150, false));
        cartas.add(new CartaSorteAzar("Seu carro quebrou! Perca $300.", 300, false));
        cartas.add(new CartaSorteAzar("Você perdeu dinheiro em um investimento ruim. Perca $400.", 400, false));
        cartas.add(new CartaSorteAzar("Você teve que pagar impostos atrasados. Perca $500.", 500, false));
        cartas.add(new CartaSorteAzar("Uma compra inesperada drenou seu saldo. Perca $250.", 250, false));
        cartas.add(new CartaSorteAzar("Seu cartão de crédito foi clonado! Perca $350.", 350, false));
        cartas.add(new CartaSorteAzar("Você teve que pagar uma dívida antiga. Perca $200.", 200, false));
        cartas.add(new CartaSorteAzar("Você perdeu uma aposta. Perca $100.", 100, false));
        cartas.add(new CartaSorteAzar("Seu negócio não deu certo. Perca $400.", 400, false));
        cartas.add(new CartaSorteAzar("Você teve que pagar por um serviço caro. Perca $300.", 300, false));
        cartas.add(new CartaSorteAzar("Você foi multado por velocidade! Perca $150.", 150, false));
        cartas.add(new CartaSorteAzar("Um acidente doméstico te causou prejuízo. Perca $250.", 250, false));
        cartas.add(new CartaSorteAzar("Você teve um gasto médico inesperado. Perca $500.", 500, false));
        cartas.add(new CartaSorteAzar("Você teve que ajudar um amigo em apuros. Perca $100.", 100, false));
        cartas.add(new CartaSorteAzar("Seu pet destruiu um objeto caro. Perca $150.", 150, false));
        cartas.add(new CartaSorteAzar("Você perdeu um item valioso. Perca $200.", 200, false));
        cartas.add(new CartaSorteAzar("Seu aluguel aumentou. Perca $50.", 50, false));
        cartas.add(new CartaSorteAzar("Você teve que pagar pela manutenção da casa. Perca $300.", 300, false));
        cartas.add(new CartaSorteAzar("Você perdeu um processo judicial. Perca $400.", 400, false));
        cartas.add(new CartaSorteAzar("Você teve que consertar o telhado da sua casa. Perca $250.", 250, false));
        cartas.add(new CartaSorteAzar("Uma despesa inesperada esvaziou seu bolso. Perca $100.", 100, false));
        cartas.add(new CartaSorteAzar("Você perdeu dinheiro em uma viagem mal planejada. Perca $200.", 200, false));
        cartas.add(new CartaSorteAzar("Você foi multado por estacionar em local proibido. Perca $50.", 50, false));

        // Embaralha as cartas e adiciona ao tabuleiro
        Collections.shuffle(cartas);
        tabuleiro.setCartas(cartas);
    }

    public Point getCoordenadaCasa(int index) {
        if (index >= 0 && index < coordenadasCasas.length) {
            return coordenadasCasas[index];
        }
        return null;
    }

    public CartaSorteAzar sortearCarta() {
        if (!tabuleiro.getCartas().isEmpty()) { // Verifica se a lista não está vazia
            return tabuleiro.getCartas().remove(0); // Remove e retorna a primeira carta
        }
        return null;
    }

    private void removerPrimeiraCarta() {
        if (!tabuleiro.getCartas().isEmpty()) {
            tabuleiro.getCartas().remove(0); // Remove a primeira carta diretamente
        }
    }
}
