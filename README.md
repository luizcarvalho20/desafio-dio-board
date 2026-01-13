# 📋 Kanban Board API - Core Domain & QA

Este repositório contém a implementação do núcleo de uma API de gerenciamento de boards, desenvolvida com **Java 17** e **Spring Boot**. O foco principal deste projeto é demonstrar o domínio técnico em modelagem de software, separação de responsabilidades (Layered Architecture) e a garantia de qualidade através de **testes automatizados**.

---

## 🎯 Diferenciais Técnicos

Diferente de uma implementação básica, este projeto foi construído focando em:
- **Clean Code & Domain Driven:** Entidades (`Board`, `Column`, `Card`) isoladas com regras de negócio auto-contidas.
- **Resiliência:** Garantia de que o fluxo de estados (INITIAL -> PENDING -> FINAL) respeita o domínio.
- **Test-First Mindset:** Suíte de testes unitários que valida desde enums até a orquestração de serviços.

---

## 🧪 Estratégia de Qualidade (QA)

A suíte de testes integrada ao ciclo de build garante que nenhuma alteração quebre as regras fundamentais.

- **Tecnologias:** JUnit 5, Mockito e Maven Surefire Plugin.
- **Execução:**
  ```bash
  mvn clean test
Nota de QA: Durante o desenvolvimento, os testes automatizados foram cruciais para identificar desalinhamentos na lógica de transição de colunas, permitindo correções antes do deploy.

🐳 Containerização (Docker)
O projeto está totalmente "dockerizado", facilitando o deploy e a consistência entre ambientes de desenvolvimento e produção.

Build da imagem:

Bash

docker build -t kanban-board-api .
Execução do container:

Bash

docker run -p 8080:8080 kanban-board-api
🛠️ Stack Tecnológica
Linguagem: Java 17

Build Tool: Maven

Core: Spring Boot

Testing: JUnit 5 & Mockito

Infra: Docker

🚀 Roadmap de Evolução
[ ] Persistência com MySQL/PostgreSQL.

[ ] Implementação de logs de auditoria (Histórico de cards).

[ ] Interface CLI para interação em tempo real.

Desenvolvido por Luiz Carvalho.
