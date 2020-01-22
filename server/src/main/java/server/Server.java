package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.*;

import static java.util.logging.Logger.*;

public class Server {
    private Vector<ClientHandler> clients;
    private AuthService authService;
    private static final Logger logger = getLogger(Server.class.getName());
    Handler fileHandler = null;

    public AuthService getAuthService() {
        return authService;
    }

    public Server() {
        clients = new Vector<>();
        try {
            fileHandler = new FileHandler("server/src/main/java/server/log.log", 1024,1,true );
            fileHandler.setFormatter(new SimpleFormatter());
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.addHandler(fileHandler);

//        authService = new SimpleAuthService();
        if (!SQLHandler.connect()) {
            logger.log(Level.SEVERE,"Не удалось подключиться к БД");
            throw new RuntimeException("Не удалось подключиться к БД");
        }
        authService = new DBAuthServise();

        ServerSocket server = null;
        Socket socket = null;

        ExecutorService cashedTreadPool = null;
        try {
            server = new ServerSocket(8189);
            logger.log(Level.INFO, "Сервер запущен");
            cashedTreadPool = Executors.newCachedThreadPool();

            while (true) {
                socket = server.accept();
                logger.log(Level.INFO,"Клиент подключился");
                cashedTreadPool.execute(new ClientHandler(this, socket));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            cashedTreadPool.shutdownNow();
            SQLHandler.disconnect();
            try {
                server.close();
                logger.log(Level.WARNING,"Сервер прекратил работу");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void broadcastMsg(String nick, String msg) {
        for (ClientHandler c : clients) {
            c.sendMsg(nick + " : " + msg);
        }
    }

    public void privateMsg(ClientHandler sender, String receiver, String msg) {
        String message = String.format("[ %s ] private [ %s ] : %s",
                sender.getNick(), receiver, msg);

        for (ClientHandler c : clients) {
            if (c.getNick().equals(receiver)) {
                c.sendMsg(message);
                sender.sendMsg(message);
                return;
            }
        }
        sender.sendMsg("Пользователь с ником: " + receiver + " не найден");
    }

    public void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
        broadcastClientlist();
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        broadcastClientlist();
    }

    public boolean isLoginAuthorized(String login) {
        for (ClientHandler c : clients) {
            if (c.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    public void broadcastClientlist() {
        StringBuilder sb = new StringBuilder("/clientlist ");
        for (ClientHandler c : clients) {
            sb.append(c.getNick() + " ");
        }

        String msg = sb.toString();
        for (ClientHandler c : clients) {
            c.sendMsg(msg);
        }
    }
}
