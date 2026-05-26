package banconet.models;

public class Transacao {
    private int origem;
    private int destino;
    private double valor;
    private long timestamp; // Em milissegundos

    public Transacao(int origem, int destino, double valor, long timestamp) {
        this.origem = origem;
        this.destino = destino;
        this.valor = valor;
        this.timestamp = timestamp;
    }

    public int getOrigem() {
        return origem;
    }

    public int getDestino() {
        return destino;
    }

    public double getValor() {
        return valor;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Transacao{" +
                "origem=" + origem +
                ", destino=" + destino +
                ", valor=" + valor +
                ", timestamp=" + timestamp +
                '}';
    }
}
