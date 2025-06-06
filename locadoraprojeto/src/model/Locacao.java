package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Locacao implements Serializable {
    private int id;
    private Cliente cliente;
    private Filme filme;
    private String dataAluguel;
    private String dataDevolucao;
    private double valor;
    private String status;

    public Locacao() {
    }

    public Locacao(int id, Cliente cliente, Filme filme, String dataAluguel, String dataDevolucao, double valor,
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
        return dataAluguel;
    }

    public void setDataAluguel(String dataAluguel) {
        this.dataAluguel = dataAluguel;
    }

    public String getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(String dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public void alugar() {
        if (!filme.isDisponivel()) {
            throw new IllegalStateException("Filme não está disponível para locação.");
        }
        filme.setDisponivel(false);
        this.status = "Alugado";
    }

    public void devolver() {
        filme.setDisponivel(true);
        this.status = "Devolvido";
    }

    public double calcularValor() {
        LocalDate inicio = LocalDate.parse(dataAluguel);
        LocalDate fim = dataDevolucao != null ? LocalDate.parse(dataDevolucao) : LocalDate.now();

        long dias = ChronoUnit.DAYS.between(inicio, fim);
        double precoDia = 5.00;

        double valorBase = dias * precoDia;

        long diasPermitidos = 3;
        long diasAtraso = ChronoUnit.DAYS.between(inicio.plusDays(diasPermitidos), fim);
        double multa = diasAtraso > 0 ? diasAtraso * 2.50 : 0;

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
}
