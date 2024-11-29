package visao;

import modelo.Tabuleiro;
import modelo.Jogador;
import modelo.Casa;

import controle.SomController;
import controle.TabuleiroController;
import controle.JogoController;

import util.ArquivoUtil;

import javax.swing.*;

import java.awt.*;

import java.util.ArrayList;
import java.util.Arrays;

import java.io.IOException;

public class PainelMenu extends JPanel {
    private CardLayout layout;
    private JPanel telas;
    private JFrame frame;

    private JogoController jogoController;
    private SomController bgm;
    private SomController sfx;

    public PainelMenu(JFrame frame)
    {
        setLayout(new BorderLayout());

        this.frame = frame;
        this.layout = new CardLayout();
        this.telas = new JPanel(layout);
        this.bgm = new SomController();
        this.sfx = new SomController();
        this.jogoController = null;

        telas.add(criarPainelComFundo("recursos/imagens/menu/fundo_inicial.png"),
                "Introducao1");
        telas.add(criarPainelComFundo("recursos/imagens/menu/fundo_faculdade.png"),
                "Introducao2");
        telas.add(criarMenuPrincipal(),
                "MenuPrincipal");
        telas.add(criarSelecaoPartida(frame),
                "SelecaoPartida");
        telas.add(criarSelecaoQuantidadeJogadores(frame),
                "SelecaoJogadores");

        add(telas, BorderLayout.CENTER);
    }

    private static JPanel criarPainelDireito(PainelJogador painelJogador, PainelDescricaoCasa painelDescricaoCasa) {
        JPanel painelDireito = new JPanel(new BorderLayout());
        painelDireito.setPreferredSize(new Dimension(960, 1080));
        painelDireito.add(painelJogador, BorderLayout.NORTH);
        painelDireito.add(painelDescricaoCasa, BorderLayout.SOUTH);
        return painelDireito;
    }

    private void configurarFramePrincipal(JFrame frame, JPanel painelTabuleiro, JPanel painelDireito) {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());
        frame.add(painelTabuleiro, BorderLayout.CENTER);
        frame.add(painelDireito, BorderLayout.EAST);
        frame.revalidate();
        frame.repaint();
    }

    private JButton criarBotao(String texto, java.awt.event.ActionListener acao) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 28));
        botao.setPreferredSize(new Dimension(400, 100));
        botao.addActionListener(acao);

        return botao;
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

    private JPanel criarMenuPrincipal() {
        JPanel painel = new JPanel(new BorderLayout());
        JLabel fundo = new JLabel(new ImageIcon("recursos/imagens/menu/fundo_menu.png"));
        fundo.setLayout(null);
        painel.add(fundo, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(3, 1, 20, 20));
        painelBotoes.setOpaque(false);
        painelBotoes.setBounds(1250, 400, 400, 390);

        JButton botaoJogar = criarBotao("Jogar", e -> {
            sfx.tocarSom("recursos/sons/seleciona.wav", false);
            layout.show(this.telas, "SelecaoPartida");
        });

        JButton botaoCreditos = criarBotao("Créditos", e -> {
            sfx.tocarSom("recursos/sons/seleciona.wav", false);
            JOptionPane.showMessageDialog(this.frame, "Créditos: \nUniversidade XYZ\nDesenvolvido por: Seu Nome");
        });

        JButton botaoSair = criarBotao("Sair", e -> {
            sfx.tocarSom("recursos/sons/seleciona.wav", false);
            System.exit(0);
        });

        painelBotoes.add(botaoJogar);
        painelBotoes.add(botaoCreditos);
        painelBotoes.add(botaoSair);
        fundo.add(painelBotoes);

        return painel;
    }

    private JPanel criarSelecaoPartida(JFrame frame) {
        JPanel painel = criarPainelComFundo("recursos/imagens/menu/fundo_jogar.png");
        painel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JButton botaoNovoJogo = criarBotao("Novo Jogo", e -> {
            sfx.tocarSom("recursos/sons/seleciona.wav", false);
            Tabuleiro tabuleiro = new Tabuleiro();
            TabuleiroController tabuleiroController = new TabuleiroController(tabuleiro);
            this.jogoController = new JogoController(tabuleiroController);
            layout.show(telas, "SelecaoJogadores");
        });

        JButton botaoCarregarJogo = criarBotao("Carregar Jogo", e -> {
            sfx.tocarSom("recursos/sons/seleciona.wav", false);
            carregarJogo();
        });

        painel.add(botaoNovoJogo, gbc);

        gbc.gridy++;
        painel.add(botaoCarregarJogo, gbc);

        return painel;
    }

    private JPanel criarSelecaoQuantidadeJogadores(JFrame frame) {
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

        JButton botao2Jogadores = criarBotao("2 Jogadores",
                e -> configurarJogadores(frame, 2));
        JButton botao3Jogadores = criarBotao("3 Jogadores",
                e -> configurarJogadores(frame, 3));
        JButton botao4Jogadores = criarBotao("4 Jogadores",
                e -> configurarJogadores(frame, 4));

        gbc.gridx = 0;
        painel.add(botao2Jogadores, gbc);

        gbc.gridx = 1;
        painel.add(botao3Jogadores, gbc);

        gbc.gridx = 2;
        painel.add(botao4Jogadores, gbc);

        return painel;
    }

    private void configurarJogadores(JFrame frame, int quantidade) {
        ArrayList<Jogador> jogadores = new ArrayList<>();
        Color[] cores = { Color.GREEN, Color.RED, Color.YELLOW, Color.BLUE };
        String[] nomesPadrao = { "Jogador 1", "Jogador 2", "Jogador 3", "Jogador 4" };

        for (int i = 0; i < quantidade; i++) {
            String nome = JOptionPane.showInputDialog(frame, "Nome do " + nomesPadrao[i], nomesPadrao[i]);
            if (nome == null || nome.isEmpty()) nome = nomesPadrao[i];
            jogadores.add(new Jogador(nome, cores[i]));
        }

        jogoController.getTabuleiroController().setJogadores(jogadores);
        iniciarJogo();
    }

    private void carregarJogo() {
        try {
            String nome = JOptionPane.showInputDialog(null, "");
            nome = "saves/" + nome;
            this.jogoController = ArquivoUtil.<JogoController>carregarEstado(nome, JogoController.class);
        }
        catch (IOException e) {

        }
        iniciarJogo();
    }

    private void iniciarJogo() {
        bgm.pararSom();
        bgm.tocarSom("recursos/sons/ambiente.wav", true);
        TabuleiroController tabuleiroController = jogoController.getTabuleiroController();

        PainelTabuleiro painelTabuleiro = new PainelTabuleiro(jogoController);
        PainelJogador painelJogador = new PainelJogador(tabuleiroController.getJogadores(),
                new ArrayList<>(Arrays.asList(jogoController.getTabuleiroController().getCasas())));
        PainelDescricaoCasa painelDescricaoCasa = new PainelDescricaoCasa("recursos/imagens/painel/fundo_casas.png");

        JPanel painelDireito = criarPainelDireito(painelJogador, painelDescricaoCasa);

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

        jogoController.setAtualizarInterfaceCallback(() -> {
            painelJogador.atualizarPainel();
            int index = jogoController.getJogadorAtual().getCasaAtual();
            painelDescricaoCasa.atualizarInformacoes(tabuleiroController.getCasa(index), index);
        });

        configurarFramePrincipal(frame, painelTabuleiro, painelDireito);
    }

    public void exibirMenuPrincipal() {
        //bgm.tocarSom("recursos/sons/menu.wav", true);
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
}
