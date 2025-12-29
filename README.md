
 📋 Desafio Board — Java + JDBC + MySQL

Projeto desenvolvido como parte do **Desafio prático da DIO**, com o objetivo de aplicar conceitos de **Java**, **Programação Orientada a Objetos**, **JDBC**, **MySQL** e **arquitetura em camadas**, criando uma aplicação de **quadro de tarefas (Board)** executada via **console**.

---

## 🎯 Objetivo do Projeto

Criar uma aplicação de console que permita:

- Criar e gerenciar **boards**
- Criar **colunas** associadas a um board
- Criar, mover e listar **cards**
- Persistir os dados em banco de dados **MySQL**
- Gerar relatórios simples a partir dos dados cadastrados

O projeto segue boas práticas de organização, separando responsabilidades entre **UI**, **Service**, **DAO** e **Domain**.

---

## 🧠 Conceitos Aplicados

- Programação Orientada a Objetos (POO)
- Arquitetura em camadas
- JDBC (Java Database Connectivity)
- MySQL
- Maven
- Organização de pacotes
- Enumerações
- Relacionamentos entre entidades
- Boas práticas de código

---

## 🛠️ Tecnologias Utilizadas

- **Java 17+**
- **Maven**
- **MySQL 8+**
- **JDBC**
- **IntelliJ IDEA**
- **Git/GitHub**

---

## 📂 Estrutura do Projeto

```text
desafio-board
├── doc
│   └── mysql-setup.md
├── src
│   └── main
│       ├── java
│       │   └── br.com.dio.board
│       │       ├── dao
│       │       ├── domain
│       │       ├── service
│       │       ├── ui
│       │       └── Main.java
│       ├── db
│       │   ├── ConnectionFactory.java
│       │   └── DbBootstrap.java
│       └── resources
│           └── schema.sql
├── pom.xml
└── README.md
📌 Camadas
ui → Interação com o usuário via terminal

service → Regras de negócio

dao → Acesso ao banco de dados

domain → Entidades do sistema

db → Configuração de conexão e inicialização do banco

🗄️ Banco de Dados
O banco de dados é criado automaticamente na primeira execução da aplicação, utilizando o arquivo:

text
Copiar código
src/main/resources/schema.sql
A inicialização é feita pela classe:

text
Copiar código
DbBootstrap
As instruções para configuração do MySQL estão documentadas em:

text
Copiar código
doc/mysql-setup.md
▶️ Como Executar o Projeto
🔹 Pré-requisitos
Java 17 ou superior

Maven instalado

MySQL rodando localmente

🔹 Passo 1 — Clonar o repositório
bash
Copiar código
git clone https://github.com/seu-usuario/desafio-board.git
cd desafio-board
🔹 Passo 2 — Configurar o banco de dados
Siga as instruções do arquivo:

text
Copiar código
doc/mysql-setup.md
Verifique as credenciais configuradas em ConnectionFactory.

🔹 Passo 3 — Executar a aplicação
bash
Copiar código
mvn exec:java
🖥️ Execução da Aplicação
A aplicação é executada via terminal, apresentando um menu interativo que permite:

Criar boards

Criar colunas

Criar e mover cards

Listar informações

Gerar relatórios

📈 Melhorias Futuras
Implementação de testes automatizados

Tratamento global de exceções

Interface gráfica ou API REST

Autenticação de usuários

Exportação de relatórios

👨‍💻 Autor
Projeto desenvolvido por Luiz Carvalho
Desafio prático — Digital Innovation One (DIO)
