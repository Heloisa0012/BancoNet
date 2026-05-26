package banconet.datastructures;

import banconet.models.Conta;

import java.util.ArrayList;
import java.util.List;

public class ArrayContas {
    private List<Conta> contas;

    public ArrayContas(int initialCapacity) {
        this.contas = new ArrayList<>(initialCapacity);
    }

    public ArrayContas() {
        this.contas = new ArrayList<>();
    }

    /**
     * Insere mantendo o array ordenado pelo número da conta.
     * Complexidade de Busca do ponto de inserção: O(log n)
     * Complexidade do shift (ArrayList): O(n)
     */
    public void inserir(Conta conta) {
        int pos = buscaBinariaPosicao(conta.getNumero());
        if (pos >= 0 && pos < contas.size() && contas.get(pos).getNumero() == conta.getNumero()) {
            throw new IllegalArgumentException("Conta já existe com este número: " + conta.getNumero());
        }
        contas.add(pos, conta);
    }

    public Conta buscarConta(int numero) {
        int left = 0;
        int right = contas.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            Conta midConta = contas.get(mid);

            if (midConta.getNumero() == numero) {
                return midConta;
            } else if (midConta.getNumero() < numero) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }

    /**
     * Retorna a posição exata se encontrar, ou a posição onde deveria ser inserido para manter a ordem.
     */
    private int buscaBinariaPosicao(int numero) {
        int left = 0;
        int right = contas.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            Conta midConta = contas.get(mid);

            if (midConta.getNumero() == numero) {
                return mid;
            } else if (midConta.getNumero() < numero) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    public int size() {
        return contas.size();
    }
}
