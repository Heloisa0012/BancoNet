# BancoNet 🏦

Um sistema bancário de alta performance construído inteiramente em **Java**, projetado para gerenciar milhões de contas e transações financeiras com latência mínima. Este projeto aplica rigorosamente conceitos de **Estruturas de Dados Avançadas** para resolver gargalos reais de engenharia de software financeiro.

## 🚀 Estruturas e Algoritmos Implementados

O ecossistema do BancoNet é dividido em três pilares matemáticos e algorítmicos:

* **Gerenciador de Contas (Arrays Ordenados e Busca Binária):**
  * Capacidade de cadastrar e buscar dados em uma base de mais de 1 milhão de clientes em menos de 20ms.
  * Performance garantida de **O(log n)** para consultas numéricas.
* **Saldos e Relatórios (Árvores Binárias de Busca - BST):**
  * Organização dinâmica de capital utilizando os saldos como chaves da árvore.
  * Geração em tempo real de relatórios de clientes VIP e contas inadimplentes utilizando travessia *In-Order*, sem a necessidade de reordenação em massa (O(n log n)).
* **Segurança e Rede (Grafos Direcionados Ponderados):**
  * Mapeamento de transferências utilizando Listas de Adjacência.
  * **Detecção de Fraudes:** Utilização de Busca em Profundidade (DFS) para rastrear ciclos de lavagem de dinheiro (ex: A -> B -> C -> A).
  * **Recomendação de Investimentos:** Cálculo de centralidade (*In-Degree*) para identificar clientes "Hubs" com grande influência na rede.
  * **Otimização de Rotas:** Algoritmo de Dijkstra para encontrar o menor caminho/taxa de transferência entre nós.

## 🛠️ Tecnologias Utilizadas
* **Java:** Linguagem principal, utilizando POO e padrão arquitetural **Facade** para integração via interface CLI.
* **JUnit 5:** Framework utilizado para testes unitários automatizados, garantindo a integridade dos nós e da ordenação dos arrays.

## 👩‍💻 Autora
**Maria Heloísa**
Estudante de Ciência da Computação
