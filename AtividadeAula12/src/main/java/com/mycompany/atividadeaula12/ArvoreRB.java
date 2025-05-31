/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.atividadeaula12;

/**
 *
 * @author T-GAMER
 */
public class ArvoreRB {
    private static final boolean VERMELHO = true;
    private static final boolean PRETO = false;

    class No {
        int valor;
        int quantidade;
        boolean cor;
        No esquerdo, direito, pai;
        No(int valor) { this.valor = valor; this.quantidade = 1; this.cor = VERMELHO; }
    }

    private No raiz;

    private No tio(No no) {
        No pai = no.pai;
        No avo = (pai != null) ? pai.pai : null;
        if (avo == null) return null;
        if (pai == avo.esquerdo) return avo.direito;
        else return avo.esquerdo;
    }

    private void rotacaoEsquerda(No x) {
        No y = x.direito;
        x.direito = y.esquerdo;
        if (y.esquerdo != null) y.esquerdo.pai = x;
        y.pai = x.pai;
        if (x.pai == null) raiz = y;
        else if (x == x.pai.esquerdo) x.pai.esquerdo = y;
        else x.pai.direito = y;
        y.esquerdo = x;
        x.pai = y;
    }

    private void rotacaoDireita(No x) {
        No y = x.esquerdo;
        x.esquerdo = y.direito;
        if (y.direito != null) y.direito.pai = x;
        y.pai = x.pai;
        if (x.pai == null) raiz = y;
        else if (x == x.pai.direito) x.pai.direito = y;
        else x.pai.esquerdo = y;
        y.direito = x;
        x.pai = y;
    }

    public void inserir(int valor) {
        No novo = new No(valor);
        raiz = bstInsert(raiz, novo);
        fixInsert(novo);
    }

    private No bstInsert(No raizAtual, No no) {
        if (raizAtual == null) return no;
        if (no.valor < raizAtual.valor) {
            raizAtual.esquerdo = bstInsert(raizAtual.esquerdo, no);
            raizAtual.esquerdo.pai = raizAtual;
        } else if (no.valor > raizAtual.valor) {
            raizAtual.direito = bstInsert(raizAtual.direito, no);
            raizAtual.direito.pai = raizAtual;
        } else {
            raizAtual.quantidade++;
        }
        return raizAtual;
    }

    private void fixInsert(No no) {
        while (no.pai != null && no.pai.cor == VERMELHO) {
            No avo = no.pai.pai;
            if (no.pai == avo.esquerdo) {
                No tio = avo.direito;
                if (tio != null && tio.cor == VERMELHO) {
                    no.pai.cor = PRETO;
                    tio.cor = PRETO;
                    avo.cor = VERMELHO;
                    no = avo;
                } else {
                    if (no == no.pai.direito) {
                        no = no.pai;
                        rotacaoEsquerda(no);
                    }
                    no.pai.cor = PRETO;
                    avo.cor = VERMELHO;
                    rotacaoDireita(avo);
                }
            } else {
                No tio = avo.esquerdo;
                if (tio != null && tio.cor == VERMELHO) {
                    no.pai.cor = PRETO;
                    tio.cor = PRETO;
                    avo.cor = VERMELHO;
                    no = avo;
                } else {
                    if (no == no.pai.esquerdo) {
                        no = no.pai;
                        rotacaoDireita(no);
                    }
                    no.pai.cor = PRETO;
                    avo.cor = VERMELHO;
                    rotacaoEsquerda(avo);
                }
            }
        }
        raiz.cor = PRETO;
    }

    private No buscarNo(No no, int valor) {
        while (no != null) {
            if (valor < no.valor) no = no.esquerdo;
            else if (valor > no.valor) no = no.direito;
            else return no;
        }
        return null;
    }

    public int contarOcorrencias(int valor) {
        No encontrado = buscarNo(raiz, valor);
        return (encontrado != null) ? encontrado.quantidade : 0;
    }

    public void remover(int valor) {
        No no = buscarNo(raiz, valor);
        if (no == null) return;
        if (no.quantidade > 1) {
            no.quantidade--;
            return;
        }
        deleteNo(no);
    }

    private void deleteNo(No no) {
        No y = no;
        boolean corOriginal = y.cor;
        No x;
        if (no.esquerdo == null) {
            x = no.direito;
            transplant(no, no.direito);
        } else if (no.direito == null) {
            x = no.esquerdo;
            transplant(no, no.esquerdo);
        } else {
            y = minimo(no.direito);
            corOriginal = y.cor;
            x = y.direito;
            if (y.pai == no) {
                if (x != null) x.pai = y;
            } else {
                transplant(y, y.direito);
                y.direito = no.direito;
                y.direito.pai = y;
            }
            transplant(no, y);
            y.esquerdo = no.esquerdo;
            y.esquerdo.pai = y;
            y.cor = no.cor;
        }
        if (corOriginal == PRETO) {
            fixDelete(x, (x != null) ? x.pai : null);
        }
    }

    private void transplant(No u, No v) {
        if (u.pai == null) raiz = v;
        else if (u == u.pai.esquerdo) u.pai.esquerdo = v;
        else u.pai.direito = v;
        if (v != null) v.pai = u.pai;
    }

    private No minimo(No no) {
        while (no.esquerdo != null) no = no.esquerdo;
        return no;
    }

        private void fixDelete(No x, No pai) {
            while (((x == null) || (x.cor == PRETO)) && (x != raiz)) {
                if (pai == null) {
                    break;
                }
                if (x == pai.esquerdo) {
                    No w = pai.direito;
                    if (w != null && w.cor == VERMELHO) {
                        w.cor = PRETO;
                        pai.cor = VERMELHO;
                        rotacaoEsquerda(pai);
                        w = pai.direito;
                    }
                    if (w != null && 
                        ( (w.esquerdo == null || w.esquerdo.cor == PRETO)
                        && (w.direito  == null || w.direito.cor  == PRETO) )
                    ) {
                        w.cor = VERMELHO;
                        x = pai;
                        pai = x.pai;
                    } else {
                        if (w != null && (w.direito == null || w.direito.cor == PRETO)) {
                            if (w.esquerdo != null) {
                                w.esquerdo.cor = PRETO;
                            }
                            w.cor = VERMELHO;
                            rotacaoDireita(w);
                            w = pai.direito;
                        }
                        if (w != null) {
                            w.cor = pai.cor;
                        }
                        pai.cor = PRETO;
                        if (w != null && w.direito != null) {
                            w.direito.cor = PRETO;
                        }
                        rotacaoEsquerda(pai);
                        x = raiz;
                        break;
                    }
                } else {
                    No w = pai.esquerdo;
                    if (w != null && w.cor == VERMELHO) {
                        w.cor = PRETO;
                        pai.cor = VERMELHO;
                        rotacaoDireita(pai);
                        w = pai.esquerdo;
                    }
                    if (w != null &&
                        ( (w.direito == null || w.direito.cor == PRETO)
                        && (w.esquerdo == null || w.esquerdo.cor == PRETO) )
                    ) {
                        w.cor = VERMELHO;
                        x = pai;
                        pai = x.pai;
                    } else {
                        if (w != null && (w.esquerdo == null || w.esquerdo.cor == PRETO)) {
                            if (w.direito != null) {
                                w.direito.cor = PRETO;
                            }
                            w.cor = VERMELHO;
                            rotacaoEsquerda(w);
                            w = pai.esquerdo;
                        }
                        if (w != null) {
                            w.cor = pai.cor;
                        }
                        pai.cor = PRETO;
                        if (w != null && w.esquerdo != null) {
                            w.esquerdo.cor = PRETO;
                        }
                        rotacaoDireita(pai);
                        x = raiz;
                        break;
                    }
                }
            }
        if (x != null) {
            x.cor = PRETO;
        }
        }


    public void imprimirEmOrdem() {
        imprimirEmOrdem(raiz);
    }

    private void imprimirEmOrdem(No no) {
        if (no != null) {
            imprimirEmOrdem(no.esquerdo);
            System.out.println("Valor: " + no.valor + " (qtd=" + no.quantidade + ") - Cor: " + (no.cor == VERMELHO ? "V" : "P"));
            imprimirEmOrdem(no.direito);
        }
    }
}
