package org.example;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatClientImpl extends UnicastRemoteObject implements ChatClientInterface {

    private final String username;

    public ChatClientImpl(String username) throws RemoteException {
        super();
        this.username = username;
    }

    @Override
    public void receiveMessage(String sender, String message) throws RemoteException {
        // Exibe a mensagem recebida no console do cliente
        if (!sender.equalsIgnoreCase(username)) {
            System.out.println("\n[" + sender + "]: " + message);
            System.out.print("> "); // Reexibe o prompt
        }
    }
}