import java.io.*;
import java.net.*;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.sound.sampled.Port;

public class Server implements Runnable {

    private static final int MULTICAST_PORT = 4321;
    private static final String MULTICAST_IP = "230.0.0.0";
    private static MulticastSocket multicast;
    private static ServerSocket socket;
    static InetAddress ip;
    static int port;
    static int id;
    static String time;
    static String ptime;
    static String aDelay;
    static ArrayList<Process> timesArray = new ArrayList<Process>();
    static ArrayList<Socket> connArray = new ArrayList<Socket>();

    public Server(String ipServer, int portServer) throws IOException {
        multicast = new MulticastSocket();
        socket = new ServerSocket(portServer);
        time = DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now());
        ip = InetAddress.getByName(ipServer);
        port = portServer;
    }

    @Override
    public void run() {
            Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				public void run() {
                    muticast();
                    
				}
			}, 0, 5000);   

            Thread th = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true){
                        socketTCP();
                    }
                }
            });
            th.start();
    }

    private static void muticast() {
		try {
            byte[] b = new byte[1024];
            String msg = "Get:"+ ip.getHostAddress() + ":" + port;
            b = msg.getBytes();
            DatagramPacket dgram = new DatagramPacket(b, b.length, InetAddress.getByName(MULTICAST_IP), MULTICAST_PORT);
            System.out.println("\n*** Sending multicast ***");
            multicast.send(dgram);
        } catch (IOException e) {
            multicast.close();
            e.printStackTrace();
        }
	}

    private static void socketTCP() {
		try {
            while(true){
                Socket conn = socket.accept();

                // if(conn.getInetAddress().getHostAddress() != null){
                //     connArray.add(conn);
                // }

                System.out.println("\n Conexao estabelecida com: " + conn.getInetAddress().getHostAddress());

                ObjectOutputStream outputStream = new ObjectOutputStream(conn.getOutputStream());
                outputStream.flush();
                ObjectInputStream intputStream = new ObjectInputStream(conn.getInputStream());

                outputStream.writeObject("Conexao estabelecida com sucesso...\n");

                // String[] msg = separateMessage(intputStream.readObject().toString());

                // for(int i=0; i<timesArray.size(); i++){
                //     if(timesArray.get(i).id != Integer.parseInt((msg[0]))){
                //         timesArray.add(new Process(Integer.parseInt(msg[0]), msg[1]));
                //     }
                // }

                // if(timesArray.size() == connArray.size()){
                //     System.out.println("Ta igual a quantidade:");
                // }

                System.out.println("\n Tempo do Cliente: "+intputStream.readObject());
                outputStream.close();
                conn.close();
            }
           
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
	}

    public static String calculateTimes(ArrayList<String> timesArray){
        long seconds = 0;

        for(String tClient : timesArray){
            String[] time = tClient.split(":");
            seconds = seconds + Integer.valueOf(time[0]) * 60 * 60;
            seconds = seconds + Integer.valueOf(time[1]) * 60;
            seconds = seconds + Integer.valueOf(time[2]);
        }

        seconds = seconds / timesArray.size();
        long hours = seconds / 60 / 60;
        long minutes = (seconds / 60) % 60;
        seconds = seconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private static String[] separateMessage(String message){
        String[] str = message.split(":");
        return str;
    }
}