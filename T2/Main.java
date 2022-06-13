import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length < 3 || args.length > 5) {
            System.out.println("Criar Servidor: java Main server <server_ip> <server_port>");
            System.out.println("Registrar Peer: java Main client <client_ip> <client_port>");
            return;
        } else {
            switch(args[0]){
                case "server":
                    new Server(args[1], Integer.valueOf(args[2])).run();
                    break;
                case "client":
                    new Client(args[1], Integer.valueOf(args[2])).run();
                    break;
            }
        }
    }
}
