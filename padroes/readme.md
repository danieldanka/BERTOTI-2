# ⚖️ Minhas Contribuições - JurisAI

Este documento detalha as minhas contribuições pessoais para o desenvolvimento do **JurisAI**, assistente inteligente de conhecimento jurídico desenvolvido no repositório [joaosantos13/Padroes-De-Projetos](https://github.com/joaosantos13/Padroes-De-Projetos).

O meu foco no projeto foi a concepção da **arquitetura do agente**, implementação da **máquina de estados (State Pattern)**, definição dos **contratos de integração** para os demais padrões de projeto e o mapeamento do **fluxo de execução de ponta a ponta**.

---

## 💻 O que eu desenvolvi

- **Orquestrador Central (`LegalAgentContext`):** Desenvolvi o núcleo de controle do agente responsável por gerenciar a sessão da consulta jurídica, armazenar dados transientes (`userQuery`, `retrievedSources`, `generatedAnswer`) e coordenar as fases de processamento da dúvida.
- **Implementação do State Pattern (`AgentState` e Estados Concretos):** Estruturei o ciclo de vida da requisição dividindo-a em estados desacoplados dentro do pacote `com.jurisai.agent`:
  - `ReceivingQuestionState`: Captura e validação da pergunta submetida pela interface.
  - `AnalyzingState`: Análise do tema e seleção da estratégia jurídica correspondente.
  - `SearchingKnowledgeState`: Acionamento da busca de artigos e incisos pertinentes na base de conhecimento (RAG).
  - `GeneratingAnswerState`: Envio do contexto legal recuperado e do prompt ao modelo Llama 3 via Spring AI e Ollama.
  - `CompletedState` e `ErrorState`: Finalização da consulta com resposta pronta ou tratamento resiliente de falhas.
- **Contratos e Interfaces de Integração:** Criei os contratos base das camadas para permitir o trabalho desacoplado e em paralelo da equipe:
  - `AgentState`: Contrato dos estados do agente.
  - `LegalStrategy`: Interface do padrão Strategy para a especialização de regras por legislação (CF/88, CDC e LGPD).
  - `LegalCommand`: Contrato do padrão Command para encapsular as ações do agente.
  - `AgentObserver`: Interface do padrão Observer para desacoplar a atualização de logs e a renderização da interface visual.
- **Documentação de Arquitetura e Engenharia de Processos:** Estruturei no repositório (`docs/architecture/README.md`) a divisão formal de papéis e entregas da equipe (Item 6) e o fluxo detalhado de execução do sistema (Item 9).

---

## 📐 Estrutura de Arquivos Criados

Os arquivos foram organizados seguindo o pacote base `com.jurisai` da aplicação:

src/main/java/com/jurisai/
├── agent/
│   ├── AgentState.java             <- Interface base do State Pattern
│   ├── AgentStates.java            <- Classes concretas de estado da consulta
│   └── LegalAgentContext.java      <- Orquestrador do fluxo e contexto do agente
├── command/
│   └── LegalCommand.java           <- Contrato para comandos encapsulados
├── observer/
│   └── AgentObserver.java          <- Contrato para listeners de logs e interface
└── strategy/
└── LegalStrategy.java          <- Contrato para estratégias jurídicas

---

## 🚀 Como testar a minha contribuição

Para validar a integridade dos contratos, classes Java e a inicialização do contexto do Spring Boot, execute no terminal:

```bash
git clone [https://github.com/joaosantos13/Padroes-De-Projetos.git](https://github.com/joaosantos13/Padroes-De-Projetos.git)
cd Padroes-De-Projetos
git checkout Fluxo-Agente
./mvnw clean compile
./mvnw test
./mvnw spring-boot:run
