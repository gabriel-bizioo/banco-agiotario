package visao;

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;

import controle.JogoController;
import modelo.Jogador;

import util.*;

public class PainelTabuleiro extends JPanel {
    private static final int TAMANHO_CASA = 80;             // Tamanho de cada casa em pixels
    private static final double LOGO_SCALE = 0.4;           // Escala da logo
    private static final int TAMANHO_DADO = 64;             // Altura e largura do asset do dado
    private static final int ESPACAMENTO_COMPONENTES = 20;  // Espaçamento entre componentes
    private static final int PADDING_CASA = 2;              // Padding de 1 pixel ao redor das casas


    private JogoController jogoController;

    private BufferedImage fundoTabuleiro;   // Fundo do tabuleiro
    private BufferedImage logoImage;        // Logo do jogo
    private BufferedImage[] imagensCasas;   // Imagens das casas
    private BufferedImage dado1Imagem;      // Imagem do primeiro dado
    private BufferedImage dado2Imagem;      // Imagem do segundo dado
    private JButton botaoRolarDados;        // Botão para rolar os dados
    private JButton botaoSalvarJogo;        // Botão para rolar os dados

    public PainelTabuleiro(JogoController jogoController) {
        this.jogoController = jogoController;

        carregarImagensTabuleiro();
        inicializarComponentes();

        setPreferredSize(new Dimension(960, 1080)); // Tamanho fixo do painel
        setLayout(null); // Layout absoluto para controle manual
    }

    private void salvarJogo() {
        File arquivo = new File("saves/teste.sav");

        try {
            arquivo.createNewFile();
            System.out.println("Criou arquivo: " + arquivo.getPath());
            SaveUtil.salvarEstado(jogoController, arquivo);
        }
        catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar o jogo");
                System.out.println("Erro ao salvar o jogo:");
                e.printStackTrace();
                //System.out.println(e.toString());
                return;
        }

        JOptionPane.showMessageDialog(null, "Jogo salvo com sucesso em " + arquivo.getPath());
    }

    private void carregarImagensTabuleiro() {
        try {
            fundoTabuleiro = ImageIO.read(new File("recursos/imagens/painel/fundo_tabuleiro.png"));
            logoImage = redimensionarImagem(ImageIO.read(new File("recursos/imagens/logo.png")), LOGO_SCALE);
            carregarImagensCasas();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage redimensionarImagem(BufferedImage imagemOriginal, double escala) {
        int largura = (int) (imagemOriginal.getWidth() * escala);
        int altura = (int) (imagemOriginal.getHeight() * escala);
        BufferedImage imagemRedimensionada = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = imagemRedimensionada.createGraphics();
        g2d.drawImage(imagemOriginal, 0, 0, largura, altura, null);
        g2d.dispose();
        return imagemRedimensionada;
    }

    private void carregarImagensCasas() {
        imagensCasas = new BufferedImage[40];
        for (int i = 0; i < 40; i++) {
            try {
                imagensCasas[i] = ImageIO.read(new File("recursos/imagens/casas/img" + (i + 1) + ".png"));
            } catch (IOException e) {
                imagensCasas[i] = null; // Define como null se a imagem não for encontrada
            }
        }
    }

    private void inicializarComponentes() {
        botaoRolarDados = new JButton("Rolar Dados");
        botaoRolarDados.setPreferredSize(new Dimension(150, TAMANHO_DADO));
        botaoRolarDados.addActionListener(e -> rolarDados());
        add(botaoRolarDados);

        botaoSalvarJogo = new JButton("Salvar jogo");
        botaoSalvarJogo.setPreferredSize(new Dimension(150, TAMANHO_DADO));
        botaoSalvarJogo.addActionListener(e -> salvarJogo());
        add(botaoSalvarJogo);
    }

    private void rolarDados() {
        int dado1 = (int) (Math.random() * 6) + 1;
        int dado2 = (int) (Math.random() * 6) + 1;
        int movimento = dado1 + dado2;

        // Movimenta o jogador atual
        jogoController.executarJogada(movimento);

        // Atualiza as imagens dos dados
        carregarImagensDados(dado1, dado2);

        // Repaint para atualizar as bolinhas no tabuleiro
        repaint();
    }

    private void carregarImagensDados(int dado1, int dado2) {
        try {
            dado1Imagem = ImageIO.read(new File("recursos/imagens/dados/" + dado1 + ".png"));
            dado2Imagem = ImageIO.read(new File("recursos/imagens/dados/" + dado2 + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Fundo do tabuleiro
        if (fundoTabuleiro != null) {
            g.drawImage(fundoTabuleiro, 0, 0, getWidth(), getHeight(), this);
        }

        // Casas do tabuleiro
        desenharCasas(g);

        // Bolinhas dos jogadores
        desenharBolinhas(g);

        // Logo no centro do tabuleiro
        desenharLogo(g);

        // Botão e dados
        posicionarComponentesInterativos(g);
    }

    private void desenharCasas(Graphics g) {
        for (int i = 0; i < 40; i++) {
            Point posicao = jogoController.getTabuleiroController().getCoordenadaCasa(i);
            if (posicao == null)
                continue;

            // Posição e tamanho ajustados para o padding
            int x = posicao.x + PADDING_CASA; // Ajuste da posição X
            int y = posicao.y + PADDING_CASA; // Ajuste da posição Y
            int largura = TAMANHO_CASA - (2 * PADDING_CASA); // Reduz largura pelo padding
            int altura = TAMANHO_CASA - (2 * PADDING_CASA); // Reduz altura pelo padding

            // Fundo branco (padding)
            g.setColor(Color.WHITE);
            g.fillRect(posicao.x, posicao.y, TAMANHO_CASA, TAMANHO_CASA);

            // Desenhar fundo da casa (padrão cinza claro)
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(x, y, largura, altura);

            // Desenhar borda preta ao redor da casa
            g.setColor(Color.BLACK);
            g.drawRect(posicao.x, posicao.y, TAMANHO_CASA, TAMANHO_CASA);

            // Desenhar a imagem da casa (se disponível)
            BufferedImage imagemCasa = imagensCasas[i];
            if (imagemCasa != null) {
                g.drawImage(imagemCasa, x, y, largura, altura, this);
            }
        }
    }

    private void desenharLogo(Graphics g) {
        if (logoImage != null) {
            int larguraFundo = getWidth();
            int alturaFundo = getHeight();

            int logoX = (larguraFundo - logoImage.getWidth()) / 2;
            int logoY = (alturaFundo - logoImage.getHeight()) / 2;

            g.drawImage(logoImage, logoX, logoY, this);
        }
    }

    private void desenharBolinhas(Graphics g) {
        int raio = 20; // Tamanho das bolinhas
        int offset = 10; // Espaçamento entre bolinhas
        ArrayList<Jogador> jogadoresSelecionados = jogoController.getTabuleiroController().getJogadores();

        for (int i = 0; i < jogadoresSelecionados.size(); i++) {
            Jogador jogador = jogadoresSelecionados.get(i);
            Point posicao = jogoController.getTabuleiroController().getCoordenadaCasa(jogador.getCasaAtual());
            if (posicao != null) {
                int xBase = posicao.x + (TAMANHO_CASA / 2) - raio;
                int yBase = posicao.y + (TAMANHO_CASA / 2) - raio;

                switch (jogadoresSelecionados.size()) {
                    case 2: // Dois jogadores, bolinhas lado a lado
                        g.setColor(CorUtil.getCorDoJogador((jogador.getCor())));
                        g.fillOval(xBase + (i * 2 - 1) * offset, yBase, raio, raio);
                        break;

                    case 3: // Três jogadores, dois em cima, um embaixo
                        if (i < 2) {
                            g.setColor(CorUtil.getCorDoJogador((jogador.getCor())));
                            g.fillOval(xBase + (i * 2 - 1) * offset, yBase - offset, raio, raio);
                        } else {
                            g.setColor(CorUtil.getCorDoJogador((jogador.getCor())));
                            g.fillOval(xBase, yBase + offset, raio, raio);
                        }
                        break;

                    case 4: // Quatro jogadores, dois em cima, dois embaixo
                        g.setColor(CorUtil.getCorDoJogador((jogador.getCor())));
                        if (i < 2) {
                            g.fillOval(xBase + (i * 2 - 1) * offset, yBase - offset, raio, raio);
                        } else {
                            g.fillOval(xBase + ((i - 2) * 2 - 1) * offset, yBase + offset, raio, raio);
                        }
                        break;
                }
            }
        }
    }

    private void posicionarComponentesInterativos(Graphics g) {
        int larguraComponente = botaoRolarDados.getPreferredSize().width + (TAMANHO_DADO * 2)
                + (ESPACAMENTO_COMPONENTES * 2);
        int xCentro = (this.getWidth() - larguraComponente) / 2;
        int yCentro = this.getHeight() - 100;

        botaoRolarDados.setBounds(xCentro, yCentro, botaoRolarDados.getPreferredSize().width,
                botaoRolarDados.getPreferredSize().height);

        botaoSalvarJogo.setBounds(xCentro-160, yCentro, botaoSalvarJogo.getPreferredSize().width,
                botaoSalvarJogo.getPreferredSize().height);

        int xDado1 = xCentro + botaoRolarDados.getPreferredSize().width + ESPACAMENTO_COMPONENTES;
        int xDado2 = xDado1 + TAMANHO_DADO + ESPACAMENTO_COMPONENTES;

        if (dado1Imagem != null) {
            g.drawImage(dado1Imagem, xDado1, yCentro, TAMANHO_DADO, TAMANHO_DADO, this);
        }
        if (dado2Imagem != null) {
            g.drawImage(dado2Imagem, xDado2, yCentro, TAMANHO_DADO, TAMANHO_DADO, this);
        }
    }
}
