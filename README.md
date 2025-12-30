# 📋 Desafio Board – Gerenciamento de Tarefas

Projeto desenvolvido como parte do **Desafio Backend Java** da **DIO (Digital Innovation One)**.  
O objetivo é criar um sistema de **gerenciamento de tarefas baseado em boards**, com foco em **boas práticas de orientação a objetos, organização em camadas e testes automatizados**.

---

## 🎯 Objetivo do Projeto

Implementar a base de um sistema de boards customizáveis para acompanhamento de tarefas, permitindo a criação, organização e evolução de cards dentro de colunas bem definidas.

Este projeto foca principalmente em:
- Modelagem correta do domínio
- Regras de negócio centrais
- Serviços da aplicação
- Testes automatizados com JUnit

---

## 🧩 Funcionalidades Implementadas

### ✔️ Estrutura de Domínio
- Board
- Column
- Card
- Enum `ColumnType` (INITIAL, PENDING, FINAL, CANCEL)

### ✔️ Regras de Negócio Iniciais
- Tipos de colunas bem definidos
- Organização do domínio seguindo boas práticas
- Separação entre camadas (domain, dao, service)

### ✔️ Testes Automatizados
- Testes unitários com **JUnit 5**
- Testes cobrindo:
  - Entidades do domínio
  - Enumerações
  - Serviços principais
- Build validado com sucesso via `mvn test`

---

## 🧪 Testes

O projeto possui testes automatizados localizados em:

bash
src/test/java
Para executar os testes:

bash
Copiar código
mvn test
Resultado esperado:

Build: SUCCESS

Todos os testes executados sem falhas

🚧 Escopo da Implementação
Este projeto foi desenvolvido com escopo incremental, priorizando a qualidade do código, domínio bem estruturado e testes automatizados.

Funcionalidades abaixo não fazem parte desta etapa, mas estão previstas para evolução futura do projeto:

Menu interativo via CLI

Persistência em banco de dados MySQL

Relatórios de tempo de execução dos cards

Histórico de bloqueios e desbloqueios

Navegação completa de cards entre colunas via interface

🛠️ Tecnologias Utilizadas
Java 17

Maven

JUnit 5

Mockito (preparado para testes futuros)

IntelliJ IDEA

📌 Observações Finais
Este projeto demonstra a aplicação prática de:

Orientação a Objetos

Organização em camadas

Escrita de testes automatizados

Build e validação com Maven

O código foi estruturado visando manutenibilidade, legibilidade e evolução futura.

👨‍💻 Autor
Projeto desenvolvido por Luiz Carvalho
Desafio educacional — Digital Innovation One (DIO)
