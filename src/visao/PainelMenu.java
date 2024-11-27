package visao;

import controle.SomController;
import controle.TabuleiroController;
import modelo.Jogador;
import modelo.Tabuleiro;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PainelMenu extends JPanel {
    private CardLayout layout;
    private JPanel telas;
    private PainelJogador painelJogador;
    private TabuleiroController tabuleiroController;
    private SomController somController;

    public PainelMenu(JFrame frame, Runnable iniciarJogoCallback) {
        setLayout(new BorderLayout());
        layout = new CardLayout();
        telas = new JPanel(layout);

        tabuleiroController = new TabuleiroController(new Tabuleiro());
        painelJogador = new PainelJogador(new ArrayList<>(), new ArrayList<>());
        somController = new SomController(); // Instancia o controlador de som

        telas.add(criarPainelComFundo("recursos/imagens/menu/fundo_inicial.png"), "Introducao1");
        telas.add(criarPainelComFundo("recursos/imagens/menu/fundo_faculdade.png"), "Introducao2");
        telas.add(criarMenuPrincipal(frame), "MenuPrincipal");
        telas.add(criarSelecaoNovoCarregarJogo(frame), "SelecionarJogo");
        telas.add(criarSelecaoQuantidadeJogadores(frame, iniciarJogoCallback), "SelecaoJogadores");

        add(telas, BorderLayout.CENTER);

        exibirIntroducoes();
    }

    private void exibirIntroducoes() {
        somController.tocarSom("recursos/sons/menu.wav", true); // Loop no som do menu
        layout.show(telas, "Introducao1");
        Timer timer1 = new Timer(3000, e -> {
            layout.show(telas, "Introducao2");
            Timer timer2 = new Timer(2000, e2 -> layout.show(telas, "MenuPrincipal"));
            timer2.setRepeats(false);
            timer2.start();
        });
        timer1.setRepeats(false);
        timer1.start();
    }

    private JPanel criarPainelComFundo(String caminhoImagem) {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image imgFundo = new ImageIcon(caminhoImagem).getImage();
                g.drawImage(imgFundo, 0, 0, getWidth(), getHeight(), this);
            }
        };
    }

    private JPanel criarMenuPrincipal(JFrame frame) {
        JPanel painel = new JPanel(new BorderLayout());
        JLabel fundo = new JLabel(new ImageIcon("recursos/imagens/menu/fundo_menu.png"));
        fundo.setLayout(null);
        painel.add(fundo, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(3, 1, 20, 20));
        painelBotoes.setOpaque(false);
        painelBotoes.setBounds(1250, 400, 400, 390);

        JButton botaoJogar = criarBotao("Jogar", e -> {
            somController.tocarSom("recursos/sons/seleciona.wav", false);
            layout.show(telas, "SelecionarJogo");
        });

        JButton botaoCreditos = criarBotao("Créditos", e -> {
            somController.tocarSom("recursos/sons/seleciona.wav", false);
            JOptionPane.showMessageDialog(frame, "Créditos: \nUniversidade XYZ\nDesenvolvido por: Seu Nome");
        });

        JButton botaoSair = criarBotao("Sair", e -> {
            somController.tocarSom("recursos/sons/seleciona.wav", false);
            System.exit(0);
        });

        painelBotoes.add(botaoJogar);
        painelBotoes.add(botaoCreditos);
        painelBotoes.add(botaoSair);
        fundo.add(painelBotoes);

        return painel;
    }

    private JPanel criarSelecaoNovoCarregarJogo(JFrame frame) {
        JPanel painel = criarPainelComFundo("recursos/imagens/menu/fundo_jogar.png");
        painel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JButton botaoNovoJogo = criarBotao("Novo Jogo", e -> {
            somController.tocarSom("recursos/sons/seleciona.wav", false);
            layout.show(telas, "SelecaoJogadores");
        });

        JButton botaoCarregarJogo = criarBotao("Carregar Jogo", e -> {
            somController.tocarSom("recursos/sons/seleciona.wav", false);
            JOptionPane.showMessageDialog(frame, "Funcionalidade de carregar jogo não implementada ainda.");
        });

        painel.add(botaoNovoJogo, gbc);

        gbc.gridy++;
        painel.add(botaoCarregarJogo, gbc);

        return painel;
    }

    private JPanel criarSelecaoQuantidadeJogadores(JFrame frame, Runnable iniciarJogoCallback) {
        JPanel painel = criarPainelComFundo("recursos/imagens/menu/fundo_jogar.png");
        painel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;

        JLabel label = new JLabel("Selecione a quantidade de jogadores:");
        label.setFont(new Font("Arial", Font.BOLD, 32));
        label.setForeground(Color.WHITE);
        painel.add(label, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;

        JButton botao2Jogadores = criarBotao("2 Jogadores", e -> configurarJogadores(frame, 2, iniciarJogoCallback));
        JButton botao3Jogadores = criarBotao("3 Jogadores", e -> configurarJogadores(frame, 3, iniciarJogoCallback));
        JButton botao4Jogadores = criarBotao("4 Jogadores", e -> configurarJogadores(frame, 4, iniciarJogoCallback));

        gbc.gridx = 0;
        painel.add(botao2Jogadores, gbc);

        gbc.gridx = 1;
        painel.add(botao3Jogadores, gbc);

        gbc.gridx = 2;
        painel.add(botao4Jogadores, gbc);

        return painel;
    }

    private void configurarJogadores(JFrame frame, int quantidade, Runnable iniciarJogoCallback) {
        ArrayList<Jogador> jogadores = new ArrayList<>();
        Color[] cores = { Color.GREEN, Color.RED, Color.YELLOW, Color.BLUE };
        String[] nomesPadrao = { "Jogador 1", "Jogador 2", "Jogador 3", "Jogador 4" };
    
        for (int i = 0; i < quantidade; i++) {
            String nome = JOptionPane.showInputDialog(frame, "Nome do " + nomesPadrao[i], nomesPadrao[i]);
            if (nome == null || nome.isEmpty()) nome = nomesPadrao[i];
            jogadores.add(new Jogador(nome, cores[i]));
        }
    
        tabuleiroController.setJogadores(jogadores);
        painelJogador = new PainelJogador(jogadores, new ArrayList<>(Arrays.asList(tabuleiroController.getCasas())));
        iniciarJogoCallback.run();
    }

    private JButton criarBotao(String texto, java.awt.event.ActionListener acao) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 28));
        botao.setPreferredSize(new Dimension(400, 100));
        botao.addActionListener(acao);
        return botao;
    }
}
