package banconet;

import banconet.models.Conta;

import java.util.List;
import java.util.Scanner;

public class BancoNetCLI {
    private BancoNet banco;
    private Scanner scanner;

    public BancoNetCLI() {
        banco = new BancoNet(10000);
        scanner = new Scanner(System.in);
        popularDadosIniciais();
    }

    private void popularDadosIniciais() {
        banco.cadastrarConta(123456, "001", "João Silva", 5432.00);
        banco.cadastrarConta(789, "002", "Suspeito", 100.00);
        banco.cadastrarConta(101, "001", "Maria Santos", 1250000.00);
        banco.cadastrarConta(102, "003", "Pedro Lima", 980000.00);
        banco.cadastrarConta(103, "004", "Ana Costa", 750000.00);
        banco.cadastrarConta(999, "001", "Laranja", 0.0);

        // Simulando fraude
        for (int i = 0; i < 8; i++) {
            banco.transferir(789, 999, 10.0);
        }
    }

    public void iniciar() {
        while (true) {
            System.out.println("\n=== BancoNet ===");
            System.out.println("1. Cadastrar conta (busca binária)");
            System.out.println("2. Buscar conta");
            System.out.println("3. Transferência (grafo)");
            System.out.println("4. Relatório saldos / Top clientes (BST)");
            System.out.println("5. Detectar fraudes (grafo)");
            System.out.println("6. Sair");
            System.out.print("> Escolha uma opcao: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarConta();
                    break;
                case 2:
                    buscarConta();
                    break;
                case 3:
                    transferir();
                    break;
                case 4:
                    relatorioSaldos();
                    break;
                case 5:
                    detectarFraude();
                    break;
                case 6:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void cadastrarConta() {
        System.out.print("Numero: ");
        int numero = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Agência: ");
        String agencia = scanner.nextLine();
        System.out.print("Nome do Cliente: ");
        String nome = scanner.nextLine();
        System.out.print("Saldo Inicial: ");
        double saldo = scanner.nextDouble();

        try {
            banco.cadastrarConta(numero, agencia, nome, saldo);
            System.out.println("Conta cadastrada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void buscarConta() {
        System.out.print("> Buscar conta: ");
        int numero = scanner.nextInt();
        Conta conta = banco.buscarConta(numero);
        if (conta != null) {
            System.out.printf("Conta encontrada: %s (R$ %,.2f)\n", conta.getNomeCliente(), conta.getSaldo());
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private void transferir() {
        System.out.print("Conta de Origem: ");
        int origem = scanner.nextInt();
        System.out.print("Conta de Destino: ");
        int destino = scanner.nextInt();
        System.out.print("Valor: ");
        double valor = scanner.nextDouble();

        try {
            banco.transferir(origem, destino, valor);
            System.out.println("Transferência realizada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro na transferência: " + e.getMessage());
        }
    }

    private void relatorioSaldos() {
        System.out.print("> Top clientes (quantidade): ");
        int n = scanner.nextInt();
        List<Conta> top = banco.getTopClientes(n);
        System.out.println("> Top " + n + " saldos:");
        for (int i = 0; i < top.size(); i++) {
            System.out.printf("%d. %s: R$ %,.2f\n", (i + 1), top.get(i).getNomeCliente(), top.get(i).getSaldo());
        }
    }

    private void detectarFraude() {
        System.out.print("> Detectar fraudes para a conta: ");
        int numero = scanner.nextInt();
        boolean fraude = banco.detectarFraude(numero);
        if (fraude) {
            System.out.println("Conta " + numero + " frauda: > 5 transferências em 2min");
        } else {
            System.out.println("Nenhuma fraude detectada para a conta " + numero);
        }
    }

    public static void main(String[] args) {
        new BancoNetCLI().iniciar();
    }
}
