/*
* Trabalho para a Disciplina SCC 0630 - Inteligência Artificial
* Professor: João Luís Garcia Rosa
* * * * * * * * Grupo: * * * * * * * * 
*** Nome:                                     No. USP
*** Elisa Jorge Marcatto                      7961965
*** Giuliano Barbosa Prado                    7961109
*** Henrique de Almeida Machado da Silveira   7961089
*** Lucas Tomazela                            8124271
*** Victor Marcelino Nunes                    8622381
**************************************
* TEMA: COLÔNIA DE FORMIGAS
*/

Implementamos o algoritmo AntSystm aplicado ao problema do Caixeiro Viajante em JAVA.
Nosso problema contem um grafo fortemente conexo representando as 30 cidades
que o caixeiro precisa visitar.

Na main(), criamos vários objetos que representam a implementacao do AntSystem, para
que os parametros fossem variados e para que pudessemos avaliar o impacto de tal
variacao na qualidade da solucao e no numero de iteracoes necessario para chegar-se a
melhor solucao.

A implementacao de fato do algoritmo pode ser verificada nos seguintes arquivos,
nos metodos indicados: 

Arquivo: AntSystem.java

-> Metodos: nextCity() e calculateProbabilities()
		Contem a implementacao de fato do calculo da probabilidade
		de se escolher um determinado caminho

Arquivo: Graph.java
-> Metodos: updatePheromones() e edgeInTrail()
		Contem a implementacao da atualizacao do nivel de feromonio
		nos caminhos (atualizacao de tau em cada caminho)


Para executar o algoritmo, utilize o Makefile disponibilizado.
-> PARA COMPILAR: execute:
		$ make
-> PARA EXECUTAR O CODIGO: execute
		$ make run
-> PARA REMOVER OS ARQUIVOS .class: execute
		$ make clean