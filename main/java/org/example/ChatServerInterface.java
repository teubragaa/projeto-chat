package org.example;


import java.rmi.Remote;

public interface ChatServerInterface extends Remote {
    void registerClient(String username, ChatClientInterface client) throws java.rmi.RemoteException;
    void unregisterClient(String username) throws java.rmi.RemoteException;
    void sendMessage(String sender, String message) throws java.rmi.RemoteException;
}