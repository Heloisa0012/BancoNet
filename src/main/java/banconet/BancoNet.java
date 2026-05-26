package banconet;

import banconet.datastructures.ArrayContas;
import banconet.datastructures.BSTContas;
import banconet.datastructures.GrafoTransacoes;
import banconet.models.Conta;
import banconet.models.Transacao;

import java.util.List;

public class BancoNet {
    private ArrayContas arrayContas;
    private BSTContas bstContas;
    private GrafoTransacoes grafoTransacoes;

    public BancoNet(int capacidadeInicial) {
        arrayContas = new ArrayContas(capacidadeInicial);
        bstContas = new BSTContas();
        grafoTransacoes = new GrafoTransacoes();
    }

    public void cadastrarConta(int numero, String agencia, String nomeCliente, double saldoInicial) {
        Conta novaConta = new Conta(numero, agencia, nomeCliente, saldoInicial);
        arrayContas.inserir(novaConta);
        bstContas.inserir(novaConta);
    }

    public Conta buscarConta(int numero) {
        return arrayContas.buscarConta(numero);
    }

    public void transferir(int origem, int destino, double valor) {
        Conta contaOrigem = buscarConta(origem);
        Conta contaDestino = buscarConta(destino);

        if (contaOrigem == null) throw new IllegalArgumentException("Conta de origem não encontrada.");
        if (contaDestino == null) throw new IllegalArgumentException("Conta de destino não encontrada.");
        if (contaOrigem.getSaldo() < valor) throw new IllegalArgumentException("Saldo insuficiente.");

        // Atualizar saldos removendo e reinserindo na BST para manter ordenação correta
        bstContas.remover(contaOrigem);
        bstContas.remover(contaDestino);

        contaOrigem.sacar(valor);
        contaDestino.depositar(valor);

        bstContas.inserir(contaOrigem);
        bstContas.inserir(contaDestino);

        // Registrar no Grafo
        long timestamp = System.currentTimeMillis();
        Transacao transacao = new Transacao(origem, destino, valor, timestamp);
        grafoTransacoes.adicionarTransacao(transacao);
    }

    public List<Conta> getTopClientes(int n) {
        return bstContas.getTopN(n);
    }

    public boolean detectarFraude(int numeroConta) {
        // Fraude: mais de 5 transações em menos de 2 minutos (120000 ms)
        return grafoTransacoes.detectarFraudeAltaFrequencia(numeroConta, 120000, 5);
    }

    public List<Integer> recomendarInfluentes(int topN) {
        return grafoTransacoes.recomendarInfluentes(topN);
    }

    public List<Integer> menorCaminhoAgencias(int origem, int destino) {
        return grafoTransacoes.menorCaminho(origem, destino);
    }
}
