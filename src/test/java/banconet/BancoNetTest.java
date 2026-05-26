package banconet;

import banconet.models.Conta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BancoNetTest {

    private BancoNet banco;

    @BeforeEach
    void setUp() {
        banco = new BancoNet(1000000);
    }

    @Test
    void testCadastrarEBuscarConta() {
        banco.cadastrarConta(123456, "001", "João Silva", 5432.00);
        Conta c = banco.buscarConta(123456);
        assertNotNull(c);
        assertEquals("João Silva", c.getNomeCliente());
        assertEquals(5432.00, c.getSaldo());
    }

    @Test
    void testPerformanceBusca1MContas() {
        // Criar 1 Milhão de contas aleatórias, mas ordenadas (para otimizar o teste em si)
        // O ArrayList terá inserção rápida se inserirmos ordenado
        System.out.println("Iniciando inserção de 1M contas...");
        long startInsert = System.currentTimeMillis();
        for (int i = 1; i <= 1000000; i++) {
            banco.cadastrarConta(i, "001", "Cliente " + i, i * 10.0);
        }
        long endInsert = System.currentTimeMillis();
        System.out.println("Inserção finalizada em " + (endInsert - startInsert) + "ms");

        long startBusca = System.nanoTime();
        Conta c = banco.buscarConta(999999);
        long endBusca = System.nanoTime();

        assertNotNull(c);
        assertEquals("Cliente 999999", c.getNomeCliente());

        long timeMs = (endBusca - startBusca) / 1000000;
        System.out.println("Tempo de busca: " + timeMs + " ms");
        assertTrue(timeMs < 20, "A busca deve demorar menos de 20ms");
    }

    @Test
    void testTopSaldos() {
        banco.cadastrarConta(101, "001", "Maria", 1250000.0);
        banco.cadastrarConta(102, "002", "Pedro", 980000.0);
        banco.cadastrarConta(103, "003", "Ana", 750000.0);
        banco.cadastrarConta(104, "004", "Zeca", 10.0);

        List<Conta> top = banco.getTopClientes(3);
        assertEquals(3, top.size());
        assertEquals("Maria", top.get(0).getNomeCliente());
        assertEquals("Pedro", top.get(1).getNomeCliente());
        assertEquals("Ana", top.get(2).getNomeCliente());
    }

    @Test
    void testDeteccaoFraudes() {
        banco.cadastrarConta(789, "001", "Fraudadora", 1000.0);
        banco.cadastrarConta(999, "002", "Laranja", 0.0);

        // Nenhuma fraude inicial
        assertFalse(banco.detectarFraude(789));

        // 8 Transferências
        for (int i = 0; i < 8; i++) {
            banco.transferir(789, 999, 10.0);
        }

        assertTrue(banco.detectarFraude(789), "Deve detectar fraude para mais de 5 transferências");
    }

    @Test
    void testAtualizacaoSaldoBSTNaTransferencia() {
        banco.cadastrarConta(1, "001", "A", 100.0);
        banco.cadastrarConta(2, "001", "B", 50.0);

        List<Conta> topAntes = banco.getTopClientes(2);
        assertEquals("A", topAntes.get(0).getNomeCliente()); // 100
        assertEquals("B", topAntes.get(1).getNomeCliente()); // 50

        // A transfere 60 para B. A fica com 40, B fica com 110.
        banco.transferir(1, 2, 60.0);

        List<Conta> topDepois = banco.getTopClientes(2);
        assertEquals("B", topDepois.get(0).getNomeCliente()); // 110
        assertEquals("A", topDepois.get(1).getNomeCliente()); // 40
    }
}
