package visao;

import modelo.Casa;
import modelo.Jogador;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class PainelJogador extends JPanel {
    private ArrayList<Jogador> jogadores;
    private ArrayList<Casa> casas;
    private Image fundoImagem;

    public PainelJogador(ArrayList<Jogador> jogadores, ArrayList<Casa> casas) {
        this.jogadores = jogadores;
        this.casas = casas;

        carregarFundo("recursos/imagens/painel/fundo_jogadores.png");
        setLayout(new GridLayout(1, 4, 20, 0)); // Espaçamento horizontal entre colunas
        setBorder(new EmptyBorder(20, 20, 20, 20)); // Margem ao redor do painel
        setPreferredSize(new Dimension(960, 540));
        atualizarPainel();
    }

    private void carregarFundo(String caminhoFundo) {
        try {
            fundoImagem = Toolkit.getDefaultToolkit().getImage(caminhoFundo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizarPainel() {
        removeAll();

        int numJogadores = jogadores.size();
        Color[] coresPredefinidas = { Color.GREEN, Color.RED, Color.YELLOW, Color.BLUE };

        for (int i = 0; i < 4; i++) {
            Jogador jogador = i < numJogadores ? jogadores.get(i) : null;
            String status = jogador != null && jogador.isJogando() ? "Jogando" : "N/A";
            JPanel painelComMargem = criarPainelComMargem(criarPainelJogador(jogador, status, coresPredefinidas[i]));
            add(painelComMargem);
        }

        revalidate();
        repaint();
    }

    private JPanel criarPainelJogador(Jogador jogador, String status, Color corFaltante) {
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout());
        painelPrincipal.setBackground(Color.LIGHT_GRAY);

        // Painel do cabeçalho (Nome, Saldo, Status)
        JPanel painelCabecalho = new JPanel();
        painelCabecalho.setLayout(new BoxLayout(painelCabecalho, BoxLayout.Y_AXIS));
        painelCabecalho.setBackground(jogador != null ? jogador.getCor() : corFaltante);

        Color corTexto = (painelCabecalho.getBackground() == Color.BLUE || painelCabecalho.getBackground() == Color.BLACK)
                ? Color.WHITE
                : Color.BLACK;

        JLabel nomeLabel = criarLabel("Nome: " + (jogador != null ? jogador.getNome() : "N/A"), corTexto, Font.BOLD);
        JLabel saldoLabel = criarLabel("Saldo: $" + (jogador != null ? jogador.getSaldo() : "N/A"), corTexto, Font.PLAIN);
        JLabel statusLabel = criarLabel("Status: " + (jogador != null ? "Jogando" : "N/A"), corTexto, Font.PLAIN);

        painelCabecalho.add(nomeLabel);
        painelCabecalho.add(saldoLabel);
        painelCabecalho.add(statusLabel);

        // Painel de Propriedades
        JPanel painelPropriedades = criarPainelPropriedades(jogador);

        painelPrincipal.add(painelCabecalho, BorderLayout.NORTH);
        painelPrincipal.add(painelPropriedades, BorderLayout.CENTER);

        return painelPrincipal;
    }

    private JPanel criarPainelComMargem(JPanel painel) {
        JPanel painelComMargem = new JPanel(new BorderLayout());
        painelComMargem.setBorder(new EmptyBorder(10, 10, 10, 10)); // Margens internas ao redor do painel
        painelComMargem.setBackground(Color.LIGHT_GRAY); // Cor do fundo para separação visual
        painelComMargem.add(painel, BorderLayout.CENTER);
        return painelComMargem;
    }

    private JLabel criarLabel(String texto, Color corTexto, int estiloFonte) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", estiloFonte, 14));
        label.setForeground(corTexto);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JPanel criarPainelPropriedades(Jogador jogador) {
        JPanel propriedadesPanel = new JPanel();
        propriedadesPanel.setLayout(new BorderLayout());
        propriedadesPanel.setPreferredSize(new Dimension(180, 220));

        Color corFundoCasas = new Color(45, 45, 45); // Tom para o fundo das propriedades
        propriedadesPanel.setBackground(corFundoCasas);

        JTextArea propriedadesArea = new JTextArea(
                jogador != null ? jogador.listarPropriedades(casas) : "N/A"
        );
        propriedadesArea.setEditable(false);
        propriedadesArea.setLineWrap(true);
        propriedadesArea.setWrapStyleWord(true);
        propriedadesArea.setFont(new Font("Arial", Font.PLAIN, 12));
        propriedadesArea.setForeground(Color.WHITE);
        propriedadesArea.setBackground(corFundoCasas);

        propriedadesPanel.add(propriedadesArea, BorderLayout.CENTER);
        return propriedadesPanel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fundoImagem != null) {
            g.drawImage(fundoImagem, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
