import java.io.*;
import java.net.*;
import java.util.*;

public class Client implements Runnable {

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

    Client(int idClient, String ipClient, int portClient, String timeClient, String pTimeClient, String aDelayClient)
            throws IOException {
        id = idClient;
        multicast = new MulticastSocket(MULTICAST_PORT);
        time = timeClient;
        ip = InetAddress.getByName(ipClient);
        port = portClient;
        ptime = pTimeClient;
        aDelay = aDelayClient;
    }

    @Override
    public void run() {
        Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				public void run() {
                    
                    time = toSec(time);
                    time = toFormatHour(String.valueOf(Integer.parseInt(time) + 1));
				}
			}, 10000, 500);
        

        while (true) {
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

                    if (method.equalsIgnoreCase("get")) {
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

    public static String[] separateMessage(String message) {
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

            outputStream.writeObject(id + ":" + toSec(time) + ":" + ptime + ":" + aDelay);

            String[] msg = separateMessage(intputStream.readObject().toString());

            if (msg[0].equalsIgnoreCase("update")) {
                updateTimer(msg[1] + ":" + msg[2] + ":" + msg[3]);
            }
        } catch (Exception e) {
            socket.close();
            e.printStackTrace();
        }
    }

    public String toSec(String times) {
        String[] timer = times.split(":");
        int seconds = 0;
        seconds = seconds + Integer.valueOf(timer[0]) * 60 * 60;
        seconds = seconds + Integer.valueOf(timer[1]) * 60;
        seconds = seconds + Integer.valueOf(timer[2]);
        return String.valueOf(seconds);
    }

    public static String toFormatHour(String time) {
        long seconds = Integer.parseInt(time);
        long hours = seconds / 60 / 60;
        long minutes = (seconds / 60) % 60;
        seconds = seconds % 60;

        System.out.println(String.format("Formated: %02d:%02d:%02d", hours, minutes, seconds));
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public void updateTimer(String timer) throws IOException {
        System.out.println("\nNew Timer: " + timer);
        time = timer;
    }
}
