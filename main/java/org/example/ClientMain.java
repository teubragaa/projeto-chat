package org.example;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            // 1. Pergunta o IP do servidor em vez de usar "localhost" fixo
            System.out.print("Digite o IP do servidor (aperte Enter para 'localhost'): ");
            String host = scanner.nextLine().trim();
            if (host.isEmpty()) {
                host = "localhost";
            }

            System.out.print("Digite seu nome de usuário: ");
            String username = scanner.nextLine().trim();

            // 2. Conecta ao registro RMI no IP fornecido
            Registry registry = LocateRegistry.getRegistry(host, 1099);

            ChatServerInterface chatServer = (ChatServerInterface) registry.lookup("ChatService");

            ChatClientImpl clientCallback = new ChatClientImpl(username);

            chatServer.registerClient(username, clientCallback);

            System.out.println("\n--- Conectado ao Chat! (Digite 'sair' para encerrar) ---");
            System.out.print("> ");

            while (true) {
                String message = scanner.nextLine();

                if ("sair".equalsIgnoreCase(message.trim())) {
                    chatServer.unregisterClient(username);
                    System.out.println("Você saiu do chat.");
                    System.exit(0);
                }

                if (!message.trim().isEmpty()) {
                    chatServer.sendMessage(username, message);
                }
                System.out.print("> ");
            }

        } catch (Exception e) {
            System.err.println("Erro no cliente de chat: " + e.getMessage());
            e.printStackTrace();
        }
    }
}