import java.io.*;
import java.net.*;

public class Client implements Runnable{

    private static final int MULTICAST_PORT = 4321;
    private static final String MULTICAST_IP = "230.0.0.0";
    static byte[] buf = new byte[1024];
    static MulticastSocket multicast;
    static InetAddress ip;
    static int id;
    static int port;
    static String time;
    static String ptime;
    static String aDelay;

    Client(int idClient, String ipClient, int portClient, String timeClient, String pTimeClient, String aDelayClient) throws IOException {
        id = idClient;
        multicast = new MulticastSocket(MULTICAST_PORT);
        time = timeClient;
        ip = InetAddress.getByName(ipClient);
        port = portClient;
        ptime = pTimeClient;
        aDelay = aDelayClient;
    }

    @Override
    public void run (){
        while(true){
            try {
                multicast.joinGroup(InetAddress.getByName(MULTICAST_IP));
                while (true) {
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    multicast.receive(packet);
                    buf = packet.getData();
                    System.out.print(new String(buf));
                    
                    String[] messageArray = separateMessage(new String(buf));

                    String method = messageArray[0].trim();
                    String ipServer = messageArray[1].trim();
                    String portServer = messageArray[2].trim();

                    if(method.equalsIgnoreCase("get")){
                        sendMessage(ipServer, portServer);
                    }

                    if (method.equalsIgnoreCase("end")) {
                        break;
                    }
                }
                multicast.leaveGroup(InetAddress.getByName(MULTICAST_IP));
                multicast.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String[] separateMessage(String message){
        System.out.println("\nMSG do Server: " + message);

        String[] str = message.split(":");
        return str;
    }

    public void sendMessage(String ipServer, String portServer) throws IOException {
        Socket socket = new Socket(ipServer, Integer.parseInt(portServer));
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.flush();
            ObjectInputStream intputStream = new ObjectInputStream(socket.getInputStream());
            
            outputStream.writeObject(id+":"+time);

            String[] msg = separateMessage(intputStream.readObject().toString());

            if(msg[0].equalsIgnoreCase("update")){
                updateTimer(msg[1]+":"+msg[2]+":"+msg[3]);
            }
        } catch (Exception e) {
            socket.close();
            e.printStackTrace();
        }
    }

    public void updateTimer(String timer) throws IOException {
        time = timer;
    }
}
