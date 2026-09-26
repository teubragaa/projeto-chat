package org.example;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Digite o IP desta maquina na rede local (ex: 192.168.x.x) ou aperte Enter para localhost: ");
            String hostIp = scanner.nextLine().trim();

            if (!hostIp.isEmpty()) {
                // Define explicitamente o IP onde o RMI vai publicar o servidor para a LAN
                System.setProperty("java.rmi.server.hostname", hostIp);
            }

            int port = 1099;
            Registry registry = LocateRegistry.createRegistry(port);

            ChatServerImpl server = new ChatServerImpl();

            registry.rebind("ChatService", server);

            System.out.println("==========================================");
            System.out.println("Servidor de Chat RMI rodando na porta " + port);
            if (!hostIp.isEmpty()) {
                System.out.println("IP publicado para os clientes: " + hostIp);
            }
            System.out.println("==========================================");

        } catch (Exception e) {
            System.err.println("Erro ao iniciar o servidor RMI: " + e.getMessage());
            e.printStackTrace();
        }
    }
}