# Sistema de Controle de Estoque para Loja de Videogames

## 📝 Descrição
Este é um projeto acadêmico de um sistema de controle de estoque desenvolvido em Java com interface gráfica utilizando Swing. O principal objetivo do projeto é aplicar conceitos de Estruturas de Dados, como a implementação e manipulação de uma lista encadeada customizada para gerenciar o estoque de produtos.

## ✨ Funcionalidades
- **Adicionar Jogos:** Cadastro de novos jogos no estoque com informações como código, nome, marca, valores e quantidade.
- **Editar Jogos:** Alteração das informações de um jogo já cadastrado.
- **Vender Jogos:** Realiza a baixa no estoque e registra a transação no histórico de vendas.
- **Repor Estoque:** Adiciona unidades a um jogo existente.
- **Buscar Jogo:** Permite a busca de um jogo específico pelo seu código.
- **Visualização em Tabela:** Exibe todos os jogos do estoque em uma tabela organizada e de fácil leitura.
- **Ordenação Flexível:** Permite ordenar a lista de jogos por **Código** ou por **Nome**.
- **Relatórios Detalhados:**
    - **Relatório de Estoque:** Mostra todos os produtos, a quantidade total de itens e o valor total de custo do estoque.
    - **Relatório de Vendas:** Exibe um histórico de todas as vendas realizadas, com detalhes de cada transação e um resumo com o valor total arrecadado.

## 🛠️ Estrutura do Projeto
O projeto está organizado nas seguintes classes principais:
- **`LojaVideogamesGUI.java`**: Classe principal que constrói e gerencia toda a interface gráfica (View) e os eventos do usuário.
- **`Estoque.java`**: Classe que representa o modelo de um produto (Model), contendo seus atributos.
- **`Venda.java`**: Classe que representa o modelo de uma transação de venda (Model).
- **`Lista.java`**: Implementação da estrutura de dados de lista encadeada customizada para armazenar os objetos de Estoque e Venda.
- **`EstoqueTableModel.java`**: Classe adaptadora (Adapter) que conecta a lista de produtos à `JTable` da interface gráfica, permitindo a exibição em formato de tabela.

## 💻 Tecnologias Utilizadas
- **Java**: Linguagem de programação principal.
- **Java Swing**: Biblioteca para a construção da interface gráfica (GUI).

## 🚀 Como Executar o Projeto

**Pré-requisitos:**
- JDK (Java Development Kit) 11 ou superior.
- Visual Studio Code com o pacote de extensões "Extension Pack for Java".

**Passos:**
1.  Clone ou baixe este repositório para o seu computador.
2.  Abra o Visual Studio Code e vá em `File` > `Open Folder...`.
3.  Selecione a pasta raiz do projeto.
4.  Aguarde o VS Code carregar e indexar os arquivos Java.
5.  Abra o arquivo `LojaVideogamesGUI.java`.
6.  Clique no botão **"Run"** que aparece acima do método `main`.

## 👨‍💻 Autor
*[Seu Nome Aqui]*