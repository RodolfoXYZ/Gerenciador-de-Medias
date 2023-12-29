package com.mycompany.testefinal;

/*
 * Anthony Thiago da Costa Lima
 * Lucas Galindo Mendonça da Silva
 * Lucas Gomes Martins
 * Maria Helena Araujo dos Santos
 * Rodolfo Rodrigo Alves de Gusmão
 */

import com.opencsv.CSVWriter;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.Scanner;

public class GerenciamentoMedias {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Estudante[] estudantes = new Estudante[20];
        boolean mediasCadastradas = false;
        boolean sair = false;

        while (!sair) {
            System.out.println("----- MENU -----");
            System.out.println("a) Cadastrar as médias dos estudantes");
            System.out.println("b) Apresentar o número de estudantes com aproveitamento A e B");
            System.out.println("c) Apresentar o número de estudantes com aproveitamento C e D");
            System.out.println("d) Apresentar o número de estudantes com aproveitamento abaixo de D");
            System.out.println("e) Apresentar o número de estudantes com aproveitamento acima de B");
            System.out.println("f) Emitir relatório de notas e médias dos estudantes");
            System.out.println("g) Exportar relatório para arquivo CSV");
            System.out.println("h) Sair do programa");
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            switch (opcao.toLowerCase()) {
                case "a":
                    cadastrarMedias(estudantes, scanner); // Opção para cadastrar as médias dos estudantes
                    mediasCadastradas = true;
                    break;
                case "b":
                    if (mediasCadastradas) {
                        contarEstudantesPorConceito(estudantes, "A", "B"); // Opção para contar o número de estudantes com aproveitamento A e B
                    } else {
                        System.out.println("Cadastre as médias dos estudantes antes de executar essa opção.");
                    }
                    break;
                case "c":
                    if (mediasCadastradas) {
                        contarEstudantesPorConceito(estudantes, "C", "D"); // Opção para contar o número de estudantes com aproveitamento C e D
                    } else {
                        System.out.println("Cadastre as médias dos estudantes antes de executar essa opção.");
                    }
                    break;
                case "d":
                    if (mediasCadastradas) {
                        contarEstudantesAbaixoDeD(estudantes); // Opção para contar o número de estudantes com aproveitamento abaixo de D
                    } else {
                        System.out.println("Cadastre as médias dos estudantes antes de executar essa opção.");
                    }
                    break;
                case "e":
                    if (mediasCadastradas) {
                        contarEstudantesAcimaDeB(estudantes); // Opção para contar o número de estudantes com aproveitamento acima de B
                    } else {
                        System.out.println("Cadastre as médias dos estudantes antes de executar essa opção.");
                    }
                    break;
                case "f":
                    if (mediasCadastradas) {
                        emitirRelatorio(estudantes); // Opção para emitir um relatório de notas e médias dos estudantes
                    } else {
                        System.out.println("Cadastre as médias dos estudantes antes de executar essa opção.");
                    }
                    break;
                case "g":
                    if (mediasCadastradas) {
                        System.out.print("Digite o nome do arquivo CSV para exportação (NÃO ACENTUADO): ");
                        String nomeArquivo = scanner.nextLine();
                        exportarRelatorioCSV(estudantes, nomeArquivo); // Opção para exportar o relatório para um arquivo CSV
                    } else {
                        System.out.println("Cadastre as médias dos estudantes antes de executar essa opção.");
                    }
                    break;
                case "h":
                    sair = true; // Opção para sair do programa
                    break;
                default:
                    System.out.println("Opção inválida. Digite uma opção válida.");
                    break;
            }

            System.out.println();
        }

        System.out.println("Programa encerrado.");
        scanner.close();
    }

    // Método para cadastrar as médias dos estudantes
    public static void cadastrarMedias(Estudante[] estudantes, Scanner scanner) {
        int contador = 0;
        boolean continuar = true;
        while (contador < estudantes.length && continuar) {
            System.out.println("Cadastro do estudante " + (contador + 1));
            System.out.print("Nome (NÃO ACENTUADO): ");
            String nome = scanner.nextLine();

            double[] notas = new double[3];
            for (int j = 0; j < notas.length; j++) {
                boolean notaValida = false;
                while (!notaValida) {
                    System.out.print("Digite a nota " + (j + 1) + ": ");
                    String input = scanner.nextLine();

                    try {
                        double nota = Double.parseDouble(input);
                        if (nota >= 0.0 && nota <= 10.0) {
                            notas[j] = nota;
                            notaValida = true;
                        } else {
                            System.out.println("Nota inválida. Digite uma nota entre 0.0 e 10.0.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada inválida. Favor, digitar apenas números.");
                    }
                }
            }

            double mediaExercicios = calcularMediaExercicios(notas);

            estudantes[contador] = new Estudante(nome, notas, mediaExercicios);
            contador++;

            boolean escolhaValida = false;
            while (!escolhaValida && contador < estudantes.length) {
                System.out.print("Deseja cadastrar as médias de mais um estudante? (S/N): ");
                String resposta = scanner.nextLine();
                if (resposta.equalsIgnoreCase("S")) {
                    escolhaValida = true;
                } else if (resposta.equalsIgnoreCase("N")) {
                    continuar = false;
                    break; // Encerra o loop imediatamente
                } else {
                    System.out.println("Opção inválida. Digite uma opção válida (S/N).");
                }
            }
        }

        System.out.println("Médias cadastradas com sucesso!");
    }

    public static double calcularMediaExercicios(double[] notas) {
        double mediaExercicios = (notas[0] + notas[1] + notas[2]) / 3;
        return mediaExercicios;
    }

    // Método para contar o número de estudantes por coneito
    public static void contarEstudantesPorConceito(Estudante[] estudantes, String conceito1, String conceito2) {
        int contador = 0;
        for (Estudante estudante : estudantes) {
            if (estudante != null && (estudante.getConceito().equals(conceito1) || estudante.getConceito().equals(conceito2))) {
                contador++;
            }
        }
        System.out.println("Número de estudantes com aproveitamento " + conceito1 + " e " + conceito2 + ": " + contador);
    }

    // Método para contar o número de estudantes com aproveitamento abaixo de D
    public static void contarEstudantesAbaixoDeD(Estudante[] estudantes) {
        int contador = 0;
        for (Estudante estudante : estudantes) {
            if (estudante != null && (estudante.getConceito().equals("E"))) {
                contador++;
            }
        }
        System.out.println("Número de estudantes com aproveitamento abaixo de D: " + contador);
    }

    // Método para contar o número de estudantes com aproveitamento acima de B
    public static void contarEstudantesAcimaDeB(Estudante[] estudantes) {
        int contador = 0;
        for (Estudante estudante : estudantes) {
            if (estudante != null && (estudante.getConceito().equals("A"))) {
                contador++;
            }
        }
        System.out.println("Número de estudantes com aproveitamento acima de B: " + contador);
    }

    // Método para emitir um relatório de notas e médias dos estudantes
    public static void emitirRelatorio(Estudante[] estudantes) {
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");

        for (Estudante estudante : estudantes) {
            if (estudante != null) {
                System.out.println("----- Estudante: " + estudante.getNome() + " -----");
                System.out.println("Notas:");
                double[] notas = estudante.getNotas();
                for (int i = 0; i < notas.length; i++) {
                    System.out.println("Nota " + (i + 1) + ": " + decimalFormat.format(notas[i]));
                }
                System.out.println("Média dos exercícios: " + decimalFormat.format(estudante.getMediaExercicios()));
                System.out.println("Média final: " + decimalFormat.format(estudante.getMediaFinal()));
                System.out.println("Conceito: " + estudante.getConceito());
                System.out.println();
            }
        }
    }

    // Método para exportar o relatório para um arquivo CSV
    public static void exportarRelatorioCSV(Estudante[] estudantes, String nomeArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(nomeArquivo + ".csv"), StandardCharsets.UTF_8))) {
            writer.write('\ufeff'); // Adiciona o BOM no início do arquivo

            CSVWriter csvWriter = new CSVWriter(writer);

            DecimalFormat decimalFormat = new DecimalFormat("#0.00");

            String[] header = {"Nome", "Nota 1 (N1)", "Nota 2 (N2)", "Nota 3 (N3)", "Média dos Exercícios (ME)", "Média Final (MF)", "Conceito (C)"};
            csvWriter.writeNext(header);

            for (Estudante estudante : estudantes) {
                if (estudante != null) {
                    String nome = estudante.getNome();
                    double[] notas = estudante.getNotas();
                    double mediaExercicios = estudante.getMediaExercicios();
                    double mediaFinal = estudante.getMediaFinal();
                    String conceito = estudante.getConceito();

                    String[] row = {
                            nome,
                            decimalFormat.format(notas[0]),
                            decimalFormat.format(notas[1]),
                            decimalFormat.format(notas[2]),
                            decimalFormat.format(mediaExercicios),
                            decimalFormat.format(mediaFinal),
                            conceito
                    };
                    csvWriter.writeNext(row);
                }
            }

            csvWriter.close();

            System.out.println("O relatório foi exportado para o arquivo " + nomeArquivo + ".csv.");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao exportar o relatório em formato CSV: " + e.getMessage());
        }
    }
}

class Estudante {
    private String nome;
    private double[] notas;
    private double mediaExercicios;
    private double mediaFinal;
    private String conceito;

    public Estudante(String nome, double[] notas, double mediaExercicios) {
        this.nome = nome; // Nome do estudante
        this.notas = notas; // Array de notas do estudante
        this.mediaExercicios = mediaExercicios; // Média dos exercícios do estudante
        this.mediaFinal = calcularMediaFinal(); // Média final do estudante
        this.conceito = calcularConceito(); // Conceito do estudante
    }

    public String getNome() {
        return nome;
    }

    public double[] getNotas() {
        return notas;
    }

    public double getMediaExercicios() {
        return mediaExercicios;
    }

    public double getMediaFinal() {
        return mediaFinal;
    }

    public String getConceito() {
        return conceito;
    }

    // Método privado para calcular a média final do estudante
    private double calcularMediaFinal() {
        return (notas[0] + 2 * notas[1] + 3 * notas[2] + mediaExercicios) / 7;
    }

    // Método privado para calcular o conceito do estudante com base na média final
    private String calcularConceito() {
        if (mediaFinal >= 9.0 && mediaFinal <= 10.0) {
            return "A"; // Média final entre 9.0 e 10.0, conceito A
        } else if (mediaFinal >= 7.5 && mediaFinal < 9.0) {
            return "B"; // Média final entre 7.5 e 9.0 (exclusivo), conceito B
        } else if (mediaFinal >= 6.0 && mediaFinal < 7.5) {
            return "C"; // Média final entre 6.0 e 7.5 (exclusivo), conceito C
        } else if (mediaFinal >= 4.0 && mediaFinal < 6.0) {
            return "D"; // Média final entre 4.0 e 6.0 (exclusivo), conceito D
        } else {
            return "E"; // Média final abaixo de 4.0, conceito E
        }
    }
}