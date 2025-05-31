/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.atividadeaula12;

import java.io.*;
import java.util.*;

/**
 *
 * @author T-GAMER
 */
public class AtividadeAula12 {

    public static void main(String[] args) {
        List<Integer> dados = lerArquivo("dados100_mil.txt");
        List<Integer> operacoes = gerarOperacoes(50000, -9999, 9999, 12345L);

        ArvoreAVL arvoreAVL = new ArvoreAVL();
        long inicioAVL_ms = System.currentTimeMillis();
        for (int valor : dados) {
            arvoreAVL.inserir(valor);
        }

        for (int valor : operacoes) {
            if (valor % 3 == 0) {
                arvoreAVL.inserir(valor);
            } else if (valor % 5 == 0) {
                arvoreAVL.remover(valor);
            } else {
                arvoreAVL.contarOcorrencias(valor);
            }
        }
        long fimAVL_ms = System.currentTimeMillis();
        long duracaoAVL_ms = fimAVL_ms - inicioAVL_ms;

        ArvoreRB arvoreRB = new ArvoreRB();
        long inicioRB_ms = System.currentTimeMillis();
        for (int valor : dados) {
            arvoreRB.inserir(valor);
        }
        for (int valor : operacoes) {
            if (valor % 3 == 0) {
                arvoreRB.inserir(valor);
            } else if (valor % 5 == 0) {
                arvoreRB.remover(valor);
            } else {
                arvoreRB.contarOcorrencias(valor);
            }
        }
        long fimRB_ms = System.currentTimeMillis();
        long duracaoRB_ms = fimRB_ms - inicioRB_ms;

        System.out.printf("Tempo total AVL  : %d ms%n", duracaoAVL_ms);
        System.out.printf("Tempo total RB   : %d ms%n", duracaoRB_ms);
        System.out.printf("Comparativo: AVL levou %d ms e RB levou %d ms.%n",duracaoAVL_ms, duracaoRB_ms);
        System.out.printf("Explicacao: Quando ha um volume grande de insercoes e remocoes, a arvore Rubro Negra sai na frente pois tende a ter menor custo medio de rebalanceamento do que a AVL.");
    }

    private static List<Integer> lerArquivo(String nomeArquivo) {
        List<Integer> lista = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                sb.append(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro ao abrir/ler arquivo: " + e.getMessage());
            e.printStackTrace();
            return lista;
        }

        String conteudo = sb.toString().trim();
        if (conteudo.startsWith("[")) {
            conteudo = conteudo.substring(1);
        }
        if (conteudo.endsWith("]")) {
            conteudo = conteudo.substring(0, conteudo.length() - 1);
        }

        String[] tokens = conteudo.split(",");
        for (String token : tokens) {
            String t = token.trim();
            if (!t.isEmpty()) {
                try {
                    lista.add(Integer.parseInt(t));
                } catch (NumberFormatException nfe) {
                    System.err.println("Não consegui converter '" + t + "' como inteiro.");
                }
            }
        }
        return lista;
    }

    private static List<Integer> gerarOperacoes(int quantidade, int min, int max, long seed) {
        Random gerador = new Random(seed);
        List<Integer> lista = new ArrayList<>(quantidade);
        for (int i = 0; i < quantidade; i++) {
            int num = gerador.nextInt(max - min + 1) + min;
            lista.add(num);
        }
        return lista;
    }
}
