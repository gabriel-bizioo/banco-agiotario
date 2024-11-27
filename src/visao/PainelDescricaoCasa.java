package visao;

import modelo.Casa;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PainelDescricaoCasa extends JPanel {
    private Image fundoImagem; // Fundo do painel
    private JLabel imagemCasaLabel; // Exibe a imagem da casa
    private JLabel nomeCasaLabel; // Exibe o nome da casa
    private JLabel tipoCasaLabel; // Exibe o tipo da casa
    private JLabel valorCasaLabel; // Exibe o valor da casa
    private JLabel proprietarioCasaLabel; // Exibe o proprietário da casa

    public PainelDescricaoCasa(String caminhoFundo) {
        carregarFundo(caminhoFundo);

        setLayout(null);
        setPreferredSize(new Dimension(960, 540)); // Tamanho fixo do painel

        inicializarComponentes();
    }

    private void carregarFundo(String caminhoFundo) {
        try {
            this.fundoImagem = ImageIO.read(new File(caminhoFundo));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void inicializarComponentes() {
        // Configuração da imagem da casa
        imagemCasaLabel = new JLabel();
        imagemCasaLabel.setBounds(145, 145, 250, 250); // Margem superior e lateral de 145px
        add(imagemCasaLabel);

        // Configuração das informações da casa
        int xInfo = 145 + 250 + 45; // Posição à direita da imagem
        int yInfoBase = 145;

        nomeCasaLabel = criarLabel("Nome da Casa: ", xInfo, yInfoBase);
        tipoCasaLabel = criarLabel("Tipo: ", xInfo, yInfoBase + 40);
        valorCasaLabel = criarLabel("Valor: ", xInfo, yInfoBase + 80);
        proprietarioCasaLabel = criarLabel("Proprietário: ", xInfo, yInfoBase + 120);

        add(nomeCasaLabel);
        add(tipoCasaLabel);
        add(valorCasaLabel);
        add(proprietarioCasaLabel);
    }

    private JLabel criarLabel(String texto, int x, int y) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(Color.WHITE);
        label.setBounds(x, y, 600, 30);
        return label;
    }

    public void atualizarInformacoes(Casa casa, int index) {
        nomeCasaLabel.setText("Nome da Casa: " + casa.getNome());
        tipoCasaLabel.setText("Tipo: " + casa.getTipo());
        valorCasaLabel.setText("Valor: " + (casa.getValor() > 0 ? "$" + casa.getValor() : "N/A"));
        proprietarioCasaLabel.setText("Proprietário: " + (casa.isComprada() ? casa.getDono().getNome() : "Nenhum"));

        atualizarImagemCasa("recursos/imagens/casas/img" + (index + 1) + ".png");
    }

    private void atualizarImagemCasa(String caminhoImagem) {
        try {
            Image img = ImageIO.read(new File(caminhoImagem));
            ImageIcon icon = new ImageIcon(img.getScaledInstance(250, 250, Image.SCALE_SMOOTH));
            imagemCasaLabel.setIcon(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fundoImagem != null) {
            g.drawImage(fundoImagem, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
