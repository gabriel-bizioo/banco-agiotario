package visao;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.ArrayList;

import controle.JogoController;
import controle.SomController;
import controle.TabuleiroController;
import modelo.Casa;
import modelo.Jogador;
import modelo.Tabuleiro;

public class Principal {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = criarFramePrincipal();
            PainelMenu painelMenu = new PainelMenu(frame, () -> iniciarJogo(frame));
            frame.add(painelMenu);
            frame.setVisible(true);
        });
    }

    // Configura a janela principal
    private static JFrame criarFramePrincipal() {
        JFrame frame = new JFrame("Banco Imobiliário");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        return frame;
    }

    // Inicia o jogo após o menu
    private static void iniciarJogo(JFrame frame) {
        // Configuração inicial do som
        SomController somController = new SomController();
        somController.pararSom();
        somController.tocarSom("recursos/sons/ambiente.wav", true);

        // Configuração do tabuleiro e controladores
        Tabuleiro tabuleiro = new Tabuleiro();
        TabuleiroController tabuleiroController = new TabuleiroController(tabuleiro);
        ArrayList<Jogador> jogadores = criarJogadores();
        tabuleiroController.setJogadores(jogadores);
        JogoController jogoController = new JogoController(tabuleiroController);

        // Criação dos painéis do jogo
        PainelTabuleiro painelTabuleiro = new PainelTabuleiro(jogoController);
        PainelJogador painelJogador = new PainelJogador(jogadores, new ArrayList<>(Arrays.asList(tabuleiroController.getCasas())));
        PainelDescricaoCasa painelDescricaoCasa = new PainelDescricaoCasa("recursos/imagens/painel/fundo_casas.png");

        JPanel painelDireito = criarPainelDireito(painelJogador, painelDescricaoCasa);

        // Adiciona interação para cliques no tabuleiro
        painelTabuleiro.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int index = tabuleiroController.getCasaIndex(e.getX(), e.getY());
                if (index >= 0) {
                    Casa casa = tabuleiroController.getCasa(index);
                    if (casa != null) {
                        painelDescricaoCasa.atualizarInformacoes(casa, index);
                    }
                }
            }
        });

        // Atualização da interface após jogadas
        jogoController.setAtualizarInterfaceCallback(() -> {
            painelJogador.atualizarPainel(); // Atualiza tabela de jogadores
            int index = jogoController.getJogadorAtual().getCasaAtual();
            painelDescricaoCasa.atualizarInformacoes(tabuleiroController.getCasa(index), index);
        });

        // Configuração do frame principal
        configurarFramePrincipal(frame, painelTabuleiro, painelDireito);
    }

    // Configura o painel direito com informações
    private static JPanel criarPainelDireito(PainelJogador painelJogador, PainelDescricaoCasa painelDescricaoCasa) {
        JPanel painelDireito = new JPanel(new BorderLayout());
        painelDireito.setPreferredSize(new Dimension(960, 1080));
        painelDireito.add(painelJogador, BorderLayout.NORTH);
        painelDireito.add(painelDescricaoCasa, BorderLayout.SOUTH);
        return painelDireito;
    }

    // Adiciona os painéis ao frame
    private static void configurarFramePrincipal(JFrame frame, JPanel painelTabuleiro, JPanel painelDireito) {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());
        frame.add(painelTabuleiro, BorderLayout.CENTER);
        frame.add(painelDireito, BorderLayout.EAST);
        frame.revalidate();
        frame.repaint();
    }

    // Cria os jogadores para o jogo
    private static ArrayList<Jogador> criarJogadores() {
        ArrayList<Jogador> jogadores = new ArrayList<>();
        String[] cores = { "Vermelho", "Verde", "Azul", "Amarelo" };
        Color[] coresRGB = { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW };

        for (int i = 0; i < cores.length; i++) {
            String nome = JOptionPane.showInputDialog(null, "Digite o nome do jogador " + (i + 1) + " (" + cores[i] + "):");
            if (nome == null || nome.trim().isEmpty()) {
                nome = cores[i];
            }
            jogadores.add(new Jogador(nome, coresRGB[i]));
        }

        return jogadores;
    }
}
