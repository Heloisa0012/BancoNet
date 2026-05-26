package banconet.datastructures;

import banconet.models.Transacao;

import java.util.*;

public class GrafoTransacoes {
    
    // Lista de Adjacência: Conta Origem -> Lista de Transações
    private Map<Integer, List<Transacao>> adjacencias;

    public GrafoTransacoes() {
        this.adjacencias = new HashMap<>();
    }

    public void adicionarTransacao(Transacao t) {
        adjacencias.putIfAbsent(t.getOrigem(), new ArrayList<>());
        adjacencias.putIfAbsent(t.getDestino(), new ArrayList<>()); // Garante que destino também existe no grafo
        adjacencias.get(t.getOrigem()).add(t);
    }

    /**
     * Detectar fraudes: mais de 5 transações partindo da mesma conta em um intervalo curto de tempo (ex: 2 min = 120000 ms)
     */
    public boolean detectarFraudeAltaFrequencia(int numeroConta, long intervaloMs, int limiteTransacoes) {
        if (!adjacencias.containsKey(numeroConta)) return false;

        List<Transacao> transacoes = adjacencias.get(numeroConta);
        if (transacoes.size() < limiteTransacoes) return false;

        // Ordenar por timestamp
        transacoes.sort(Comparator.comparingLong(Transacao::getTimestamp));

        for (int i = 0; i <= transacoes.size() - limiteTransacoes; i++) {
            long tempoInicial = transacoes.get(i).getTimestamp();
            long tempoFinal = transacoes.get(i + limiteTransacoes - 1).getTimestamp();

            if ((tempoFinal - tempoInicial) <= intervaloMs) {
                return true;
            }
        }
        return false;
    }

    /**
     * Recomendação (Centralidade de Grau): Clientes com mais conexões (entradas + saídas)
     */
    public List<Integer> recomendarInfluentes(int topN) {
        Map<Integer, Integer> grau = new HashMap<>();
        
        for (Integer node : adjacencias.keySet()) {
            grau.put(node, adjacencias.get(node).size());
            for (Transacao t : adjacencias.get(node)) {
                grau.put(t.getDestino(), grau.getOrDefault(t.getDestino(), 0) + 1);
            }
        }

        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(grau.entrySet());
        list.sort((a, b) -> b.getValue().compareTo(a.getValue())); // Decrescente

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < Math.min(topN, list.size()); i++) {
            result.add(list.get(i).getKey());
        }
        return result;
    }

    /**
     * Menor caminho (BFS para grafos sem peso): Melhor rota de transferência entre contas
     */
    public List<Integer> menorCaminho(int origem, int destino) {
        if (!adjacencias.containsKey(origem) || !adjacencias.containsKey(destino)) {
            return Collections.emptyList();
        }

        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> parentMap = new HashMap<>();
        Set<Integer> visited = new HashSet<>();

        queue.add(origem);
        visited.add(origem);
        parentMap.put(origem, null);

        boolean found = false;
        while (!queue.isEmpty()) {
            int curr = queue.poll();

            if (curr == destino) {
                found = true;
                break;
            }

            for (Transacao t : adjacencias.get(curr)) {
                int vizinho = t.getDestino();
                if (!visited.contains(vizinho)) {
                    visited.add(vizinho);
                    parentMap.put(vizinho, curr);
                    queue.add(vizinho);
                }
            }
        }

        if (!found) return Collections.emptyList();

        List<Integer> path = new ArrayList<>();
        Integer step = destino;
        while (step != null) {
            path.add(step);
            step = parentMap.get(step);
        }
        Collections.reverse(path);
        return path;
    }
}
