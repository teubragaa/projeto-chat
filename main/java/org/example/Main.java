package org.example;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
    public static void main(String[] args) {
        try {
            int port = 1099;
            Registry registry = LocateRegistry.createRegistry(port);

            ChatServerImpl server = new ChatServerImpl();

            registry.rebind("ChatService", server);

            System.out.println("==========================================");
            System.out.println("Servidor de Chat RMI rodando na porta " + port);
            System.out.println("==========================================");
        } catch (Exception e) {
            System.err.println("Erro ao iniciar o servidor RMI: " + e.getMessage());
            e.printStackTrace();
        }
    }
}