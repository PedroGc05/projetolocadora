package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Locacao implements Serializable {
    private static final double PRECO_DIA = 5.00;
    private static final double MULTA_DIA = 2.50;
    private static final int DIAS_PERMITIDOS = 3;

    private int id;
    private Cliente cliente;
    private Filme filme;
    private LocalDate dataAluguel;
    private LocalDate dataDevolucao;
    private double valor;
    private String status;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Locacao() {
    }

    public Locacao(int id, Cliente cliente, Filme filme, String dataAluguel, String dataDevolucao, double valor,
            String status) {
        this.id = id;
        this.cliente = cliente;
        this.filme = filme;
        this.dataAluguel = LocalDate.parse(dataAluguel);
        this.dataDevolucao = dataDevolucao != null ? LocalDate.parse(dataDevolucao) : null;
        this.valor = valor;
        this.status = status;
    }

    public Locacao(int id, Cliente cliente, Filme filme, LocalDate dataAluguel, LocalDate dataDevolucao, double valor,
            String status) {
        this.id = id;
        this.cliente = cliente;
        this.filme = filme;
        this.dataAluguel = dataAluguel;
        this.dataDevolucao = dataDevolucao;
        this.valor = valor;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public String getDataAluguel() {
        return dataAluguel.format(formatter);
    }

    public LocalDate getDataAluguelAsDate() {
        return dataAluguel;
    }

    public void setDataAluguel(String dataAluguel) {
        this.dataAluguel = LocalDate.parse(dataAluguel);
    }

    public void setDataAluguel(LocalDate dataAluguel) {
        this.dataAluguel = dataAluguel;
    }

    public String getDataDevolucao() {
        return dataDevolucao != null ? dataDevolucao.format(formatter) : null;
    }

    public LocalDate getDataDevolucaoAsDate() {
        return dataDevolucao;
    }

    public void setDataDevolucao(String dataDevolucao) {
        this.dataDevolucao = dataDevolucao != null ? LocalDate.parse(dataDevolucao) : null;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public void alugar() {
        if (!filme.isDisponivel()) {
            throw new IllegalStateException("Filme não está disponível para locação.");
        }
        filme.setDisponivel(false);
        this.status = "Alugado";
        if (dataAluguel == null) {
            dataAluguel = LocalDate.now();
        }
    }

    public void devolver() {
        filme.setDisponivel(true);
        this.status = "Devolvido";
        this.dataDevolucao = LocalDate.now();
        this.valor = calcularValor();
    }

    public void devolver(String dataDevolucao) {
        this.dataDevolucao = LocalDate.parse(dataDevolucao);
        filme.setDisponivel(true);
        this.status = "Devolvido";
        this.valor = calcularValor();
    }

    public void devolver(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
        filme.setDisponivel(true);
        this.status = "Devolvido";
        this.valor = calcularValor();
    }

    public double calcularValor() {
        if (dataAluguel == null) {
            return 0.0;
        }

        LocalDate fim = dataDevolucao != null ? dataDevolucao : LocalDate.now();

        // Garantir que a data de devolução não seja anterior à data de aluguel
        if (fim.isBefore(dataAluguel)) {
            return 0.0;
        }

        long dias = ChronoUnit.DAYS.between(dataAluguel, fim);
        // Mínimo de um dia
        dias = Math.max(1, dias);

        double valorBase = dias * PRECO_DIA;

        long diasAtraso = ChronoUnit.DAYS.between(dataAluguel.plusDays(DIAS_PERMITIDOS), fim);
        double multa = diasAtraso > 0 ? diasAtraso * MULTA_DIA : 0;

        return valorBase + multa;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Locação #" + id +
                " | Cliente: " + (cliente != null ? cliente.getNome() : "N/A") +
                " | Filme: " + (filme != null ? filme.getTitulo() : "N/A") +
                " | Data Aluguel: " + getDataAluguel() +
                " | Data Devolução: " + getDataDevolucao() +
                " | Valor: R$" + String.format("%.2f", valor) +
                " | Status: " + status;
    }
}
