package org.example;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServerImpl extends UnicastRemoteObject implements ChatServerInterface {

    private final Map<String, ChatClientInterface> clients = new ConcurrentHashMap<>();

    public ChatServerImpl() throws RemoteException {
        super();
    }

    @Override
    public synchronized void registerClient(String username, ChatClientInterface client) throws RemoteException {
        if (clients.containsKey(username)) {
            throw new RemoteException("Nome do aluno " + username + " já existe ou já está na sala");
        }
        clients.put(username, client);
        System.out.println("O " + username + " acessou a sala");
        sendMessage("ChatSD", username + " entrou na sala");
    }

    @Override
    public synchronized void unregisterClient(String username) throws RemoteException {
        if (clients.remove(username) != null) {
            System.out.println("Aluno " + username + " saiu da sala");
            sendMessage("ChatSD", username + " saiu da sala");
        }
    }

    @Override
    public void sendMessage(String sender, String message) throws RemoteException {
        System.out.println("[" + sender + "] " + message);
        for (Map.Entry<String, ChatClientInterface> entry : clients.entrySet()) {
            try {
                entry.getValue().receiveMessage(sender, message);
            } catch (RemoteException e) {
                System.err.println("Erro ao enviar a mensagem para " + entry.getKey() + ". Removendo cliente");
                clients.remove(entry.getKey());
            }
        }
    }
}