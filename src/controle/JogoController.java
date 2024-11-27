package controle;

import javax.swing.JOptionPane;

import modelo.Casa;
import modelo.Jogador;
import modelo.CartaSorteAzar;


public class JogoController {
    private int jogadorAtual;
    private TabuleiroController tabuleiroController;
    private Runnable atualizarInterfaceCallback;
    private SomController somController;

    public JogoController(TabuleiroController tabuleiroController) {
        this.tabuleiroController = tabuleiroController;
        this.jogadorAtual = 0;
        this.somController = new SomController();
        somController.tocarSom("recursos/sons/menu.wav", true); // Tocar som do menu ao iniciar
    }

    public TabuleiroController getTabuleiroController() {
        return tabuleiroController;
    }

    public void setAtualizarInterfaceCallback(Runnable callback) {
        this.atualizarInterfaceCallback = callback;
    }

    public Jogador getJogadorAtual() {
        return tabuleiroController.getJogadores().get(jogadorAtual);
    }

    public void executarJogada(int casasParaMover) {
        Jogador jogadorAtual = getJogadorAtual();
        Casa[] casas = tabuleiroController.getCasas();
    
        // Som ao rolar dados
        somController.tocarSom("recursos/sons/dados.wav", false);

        // Identifica a casa inicial
        Casa casaInicial = casas[jogadorAtual.getCasaAtual()];
    
        // Movimenta o jogador
        jogadorAtual.setCasaAtual((jogadorAtual.getCasaAtual() + casasParaMover) % casas.length);
    
        // Verifica se o jogador completou um loop no tabuleiro
        if (jogadorAtual.getCasaAtual() + casasParaMover >= casas.length) {
            somController.tocarSom("recursos/sons/avanca.wav", false);
        }

        // Identifica a casa final
        Casa casaAtual = casas[jogadorAtual.getCasaAtual()];
    
        if ("propriedade".equals(casaAtual.getTipo())) {
            if (!casaAtual.isComprada()) {
                // Propriedade ainda não comprada
                somController.tocarSom("recursos/sons/seleciona.wav", false); // Som ao selecionar
                int escolha = JOptionPane.showConfirmDialog(
                        null,
                        "Você caiu em " + casaAtual.getNome() + ". Deseja comprar por $" + casaAtual.getValor() + "?",
                        "Compra de Propriedade",
                        JOptionPane.YES_NO_OPTION
                );
    
                if (escolha == JOptionPane.YES_OPTION) {
                    if (jogadorAtual.getSaldo() >= casaAtual.getValor()) {
                        jogadorAtual.setSaldo(jogadorAtual.getSaldo() - casaAtual.getValor());
                        casaAtual.setDono(jogadorAtual);
                        somController.tocarSom("recursos/sons/sorte.wav", false); // Som ao comprar
                        JOptionPane.showMessageDialog(null, "Você comprou " + casaAtual.getNome() + "!");
                    } else {
                        somController.tocarSom("recursos/sons/azar.wav", false); // Som de azar ao não ter saldo
                        JOptionPane.showMessageDialog(null, "Você não tem saldo suficiente para comprar esta propriedade.");
                    }
                }
            } else if (!casaAtual.getDono().equals(jogadorAtual)) {
                // Pagar aluguel
                Jogador dono = casaAtual.getDono();
                int aluguel = casaAtual.calcularAluguel();
                if (jogadorAtual.getSaldo() >= aluguel) {
                    jogadorAtual.setSaldo(jogadorAtual.getSaldo() - aluguel);
                    dono.setSaldo(dono.getSaldo() + aluguel);
                    somController.tocarSom("recursos/sons/ferias.wav", false); // Som ao pagar aluguel
                    JOptionPane.showMessageDialog(null, "Você pagou $" + aluguel + " de aluguel para " + dono.getNome());
                } else {
                    somController.tocarSom("recursos/sons/prisao.wav", false); // Som ao falir
                    JOptionPane.showMessageDialog(null, "Saldo insuficiente para pagar o aluguel. Você está falido!");
                    jogadorAtual.setJogando(false);
                }
            }
        } else if ("sorteAzar".equals(casaAtual.getTipo())) {
            // Sorte ou Azar
            CartaSorteAzar carta = tabuleiroController.sortearCarta();
            if (carta != null) {
                String mensagem = carta.getMensagem();
                if (carta.isPositiva()) {
                    jogadorAtual.setSaldo(jogadorAtual.getSaldo() + carta.getValor());
                    somController.tocarSom("recursos/sons/sorte.wav", false);
                } else {
                    jogadorAtual.setSaldo(jogadorAtual.getSaldo() - carta.getValor());
                    somController.tocarSom("recursos/sons/azar.wav", false);
                }
                JOptionPane.showMessageDialog(null, mensagem);
            }
        } else if ("especial".equals(casaAtual.getTipo())) {
            // Casa especial
            somController.tocarSom("recursos/sons/seleciona.wav", false); // Som ao selecionar casa especial
            JOptionPane.showMessageDialog(null, "Casa Especial: " + casaAtual.getNome());
        }
    
        // Passa para o próximo jogador
        this.jogadorAtual = (this.jogadorAtual + 1) % tabuleiroController.getJogadores().size();
    
        // Atualiza a interface, se o callback estiver configurado
        if (atualizarInterfaceCallback != null) {
            atualizarInterfaceCallback.run();
        }
    }
}
