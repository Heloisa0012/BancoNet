package banconet.datastructures;

import banconet.models.Conta;

import java.util.ArrayList;
import java.util.List;

public class BSTContas {

    private class Node {
        double saldo;
        List<Conta> contas; // Pode haver empate de saldos
        Node left;
        Node right;

        Node(Conta conta) {
            this.saldo = conta.getSaldo();
            this.contas = new ArrayList<>();
            this.contas.add(conta);
        }
    }

    private Node root;

    public void inserir(Conta conta) {
        root = inserirRec(root, conta);
    }

    private Node inserirRec(Node root, Conta conta) {
        if (root == null) {
            return new Node(conta);
        }

        if (conta.getSaldo() < root.saldo) {
            root.left = inserirRec(root.left, conta);
        } else if (conta.getSaldo() > root.saldo) {
            root.right = inserirRec(root.right, conta);
        } else {
            // Saldo igual, adiciona na lista
            root.contas.add(conta);
        }

        return root;
    }

    public void remover(Conta conta) {
        root = removerRec(root, conta, conta.getSaldo());
    }

    private Node removerRec(Node root, Conta conta, double saldo) {
        if (root == null) return root;

        if (saldo < root.saldo) {
            root.left = removerRec(root.left, conta, saldo);
        } else if (saldo > root.saldo) {
            root.right = removerRec(root.right, conta, saldo);
        } else {
            // Encontrou o nó com o saldo
            // Remove a conta específica da lista
            root.contas.removeIf(c -> c.getNumero() == conta.getNumero());

            // Se a lista ficar vazia, removemos o nó inteiro
            if (root.contas.isEmpty()) {
                if (root.left == null) return root.right;
                else if (root.right == null) return root.left;

                // Nó com dois filhos: pega o sucessor (menor na subárvore direita)
                Node sucessor = menorValor(root.right);
                root.saldo = sucessor.saldo;
                root.contas = new ArrayList<>(sucessor.contas);
                
                // Remove o sucessor
                root.right = removerRec(root.right, sucessor.contas.get(0), sucessor.saldo);
            }
        }
        return root;
    }

    private Node menorValor(Node root) {
        Node minv = root;
        while (minv.left != null) {
            minv = minv.left;
        }
        return minv;
    }

    public List<Conta> getTopN(int n) {
        List<Conta> result = new ArrayList<>();
        getTopNRec(root, result, n);
        return result;
    }

    // Reverse in-order traversal (Right, Root, Left) para ordem decrescente
    private void getTopNRec(Node root, List<Conta> result, int n) {
        if (root != null && result.size() < n) {
            getTopNRec(root.right, result, n);
            for (Conta c : root.contas) {
                if (result.size() < n) {
                    result.add(c);
                } else {
                    break;
                }
            }
            if (result.size() < n) {
                getTopNRec(root.left, result, n);
            }
        }
    }
}
