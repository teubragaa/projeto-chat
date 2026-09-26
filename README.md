# 💬 Chat Multi-Usuário em Tempo Real com Java RMI

Aplicação de chat em grupo desenvolvida em **Java** utilizando a arquitetura de **RMI (Remote Method Invocation)** e o padrão de projeto **Remote**. O projeto implementa comunicação bidirecional em tempo real (*Broadcast* e *Callbacks*) entre um servidor central e múltiplos clientes conectados na mesma rede local (LAN) ou em `localhost`.

---

## 📌 Sobre o Projeto

Este projeto foi desenvolvido como parte de um trabalho acadêmico de Sistemas Distribuídos. O objetivo principal é demonstrar o funcionamento de chamadas de métodos remotos, gerenciamento de concorrência e notificação proativa de eventos (*Callbacks*) sem a necessidade de requisições de consulta contínua (*polling*).

### Funcionalidades
- **Conexão Dinâmica:** Permite configurar o endereço IP do servidor via linha de comando para execuções em `localhost` ou em rede local (LAN).
- **Gerenciamento de Clientes:** Cadastro e desconexão de usuários no servidor utilizando identificadores únicos.
- **Notificações do Sistema:** Transmissão automática (*Broadcast*) para a sala informando a entrada e saída de alunos.
- **Comunicação em Tempo Real:** Envio de mensagens de texto distribuídas instantaneamente para todos os participantes conectados.
- **Tratamento de Desconexões:** Remoção graciosa do cliente da lista do servidor ao digitar o comando `sair`.

---

## 📐 Arquitetura e Padrões de Projeto

A aplicação é dividida em dois componentes principais (Servidor e Cliente) que interagem através de **Interfaces Remotas**:

1. **`ChatServerInterface` & `ChatServerImpl`**:
   - Atua como a autoridade central.
   - Gerencia a lista de clientes conectados utilizando `ConcurrentHashMap` para garantir a segurança contra condições de corrida (*thread safety*).
   - Realiza o *broadcast* de mensagens percorrendo a lista de callback de cada cliente.

2. **`ChatClientInterface` & `ChatClientImpl`**:
   - Implementa a interface de callback no lado do cliente.
   - Permite que o servidor invoque remotamente o método `receiveMessage(...)` no computador do cliente para entregar mensagens em tempo real.

3. **`Main`**:
   - Inicializa o serviço de Registro RMI (`LocateRegistry`) na porta `1099`.
   - Exporta a propriedade `java.rmi.server.hostname` para garantir o roteamento correto das chamadas em rede local.

4. **`ClientMain`**:
   - Interface de linha de comando (CLI) que permite ao usuário informar o IP do servidor e registrar seu nome no chat.

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java (JDK 17+)
- **Comunicação Distribuída:** Java RMI (`java.rmi.*`)
- **Gerenciamento de Dependências / Build:** Maven (ou Gradle)

## 📂 Estrutura do Código

```text
src/main/java/org/example/
├── ChatClientImpl.java       # Implementação do Callback do Cliente
├── ChatClientInterface.java  # Interface Remota do Cliente
├── ChatServerImpl.java       # Implementação da Lógica do Servidor e Broadcast
├── ChatServerInterface.java  # Interface Remota do Servidor
├── ClientMain.java           # Ponto de Entrada da CLI do Cliente
└── Main.java                 # Ponto de Entrada e Registro RMI do Servidor
