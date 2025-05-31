/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.atividadeaula12;

/**
 *
 * @author T-GAMER
 */
public class ArvoreAVL {
    class No {
        int valor;
        int quantidade;
        No esquerdo, direito;
        int altura;
        No(int valor) { this.valor = valor; this.quantidade = 1; altura = 1; }
    }

    private No raiz;

    private int altura(No no) {
        return (no == null) ? 0 : no.altura;
    }

    private int calcularFB(No no) {
        return (no == null) ? 0 : altura(no.direito) - altura(no.esquerdo);
    }

    private No rotacaoDireita(No y) {
        No x = y.esquerdo;
        No T2 = x.direito;
        x.direito = y;
        y.esquerdo = T2;
        y.altura = Math.max(altura(y.esquerdo), altura(y.direito)) + 1;
        x.altura = Math.max(altura(x.esquerdo), altura(x.direito)) + 1;
        return x;
    }

    private No rotacaoEsquerda(No x) {
        No y = x.direito;
        No T2 = y.esquerdo;
        y.esquerdo = x;
        x.direito = T2;
        x.altura = Math.max(altura(x.esquerdo), altura(x.direito)) + 1;
        y.altura = Math.max(altura(y.esquerdo), altura(y.direito)) + 1;
        return y;
    }

    private No inserirRec(No no, int valor) {
        if (no == null) {
            return new No(valor);
        }
        if (valor < no.valor) {
            no.esquerdo = inserirRec(no.esquerdo, valor);
        } else if (valor > no.valor) {
            no.direito = inserirRec(no.direito, valor);
        } else {
            no.quantidade++;
            return no;
        }
        no.altura = Math.max(altura(no.esquerdo), altura(no.direito)) + 1;
        int fb = calcularFB(no);
        if (fb > 1 && valor > no.direito.valor) {
            return rotacaoEsquerda(no);
        }
        if (fb < -1 && valor < no.esquerdo.valor) {
            return rotacaoDireita(no);
        }
        if (fb > 1 && valor < no.direito.valor) {
            no.direito = rotacaoDireita(no.direito);
            return rotacaoEsquerda(no);
        }
        if (fb < -1 && valor > no.esquerdo.valor) {
            no.esquerdo = rotacaoEsquerda(no.esquerdo);
            return rotacaoDireita(no);
        }
        return no;
    }

    public void inserir(int valor) {
        raiz = inserirRec(raiz, valor);
    }

    private No getMinNo(No no) {
        No atual = no;
        while (atual.esquerdo != null) {
            atual = atual.esquerdo;
        }
        return atual;
    }

    private No removerRec(No no, int valor) {
        if (no == null) {
            return null;
        }
        if (valor < no.valor) {
            no.esquerdo = removerRec(no.esquerdo, valor);
        } else if (valor > no.valor) {
            no.direito = removerRec(no.direito, valor);
        } else {
            if (no.quantidade > 1) {
                no.quantidade--;
                return no;
            }
            if (no.esquerdo == null || no.direito == null) {
                No temp = (no.esquerdo != null) ? no.esquerdo : no.direito;
                if (temp == null) {
                    no = null;
                } else {
                    no = temp;
                }
            } else {
                No temp = getMinNo(no.direito);
                no.valor = temp.valor;
                no.quantidade = temp.quantidade;
                temp.quantidade = 1;
                no.direito = removerRec(no.direito, temp.valor);
            }
        }
        if (no == null) {
            return null;
        }
        no.altura = Math.max(altura(no.esquerdo), altura(no.direito)) + 1;
        int fb = calcularFB(no);
        if (fb > 1 && calcularFB(no.direito) >= 0) {
            return rotacaoEsquerda(no);
        }
        if (fb > 1 && calcularFB(no.direito) < 0) {
            no.direito = rotacaoDireita(no.direito);
            return rotacaoEsquerda(no);
        }
        if (fb < -1 && calcularFB(no.esquerdo) <= 0) {
            return rotacaoDireita(no);
        }
        if (fb < -1 && calcularFB(no.esquerdo) > 0) {
            no.esquerdo = rotacaoEsquerda(no.esquerdo);
            return rotacaoDireita(no);
        }
        return no;
    }

    public void remover(int valor) {
        raiz = removerRec(raiz, valor);
    }

    public int contarOcorrencias(int valor) {
        No atual = raiz;
        while (atual != null) {
            if (valor < atual.valor) {
                atual = atual.esquerdo;
            } else if (valor > atual.valor) {
                atual = atual.direito;
            } else {
                return atual.quantidade;
            }
        }
        return 0;
    }

    public void imprimirEmOrdem() {
        imprimirEmOrdem(raiz);
    }

    private void imprimirEmOrdem(No no) {
        if (no != null) {
            imprimirEmOrdem(no.esquerdo);
            System.out.println("Valor: " + no.valor + " (qtd=" + no.quantidade + ") - F.B.: " + calcularFB(no));
            imprimirEmOrdem(no.direito);
        }
    }
}
