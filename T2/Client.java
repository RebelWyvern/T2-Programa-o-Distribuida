import java.io.*;
import java.net.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

    Client(String ipClient, int portClient) throws IOException {
        multicast = new MulticastSocket(MULTICAST_PORT);
        time = DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now());
        ip = InetAddress.getByName(ipClient);
        port = portClient;
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

                    if(method.equalsIgnoreCase("update")){
                        // updateTimer(ipServer, portServer);
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

            System.out.println(intputStream.readObject());
        } catch (Exception e) {
            socket.close();
            e.printStackTrace();
        }
    }

    // public void updateTimer(String timer) throws IOException {
    //     Socket socket = new Socket(ipServer, Integer.parseInt(portServer));
    //     try {
    //         ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
    //         outputStream.flush();
    //         ObjectInputStream intputStream = new ObjectInputStream(socket.getInputStream());
            
    //         outputStream.writeObject("\nReenviando mensagem..." + port);

    //         System.out.println(intputStream.readObject());
    //     } catch (Exception e) {
    //         socket.close();
    //         e.printStackTrace();
    //     }
    // }
}
