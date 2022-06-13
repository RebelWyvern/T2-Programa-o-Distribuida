import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length != 6) {
            System.out.println("Criar Servidor: java Main server <server_id> <server_ip> <server_port> <server_ptime>(em mm) <server_adelay>(mm)");
            System.out.println("Registrar Peer: java Main client <client_id> <client_ip> <client_port> <client_ptime>(em mm) <client_adelay>(mm)");
            return;
        } else {
            switch(args[0]){
                case "server":
                    new Server(Integer.valueOf(args[1]), args[2], Integer.valueOf(args[3]), args[4], args[5]).run();
                    break;
                case "client":
                    new Client(Integer.valueOf(args[1]), args[2], Integer.valueOf(args[3]), args[4], args[5]).run();
                    break;
            }
        }
    }
}
