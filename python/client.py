import threading
import socket

clientSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

def startConnecting():
    # fetch clock time at slaves / clients
    while True:
        # accepting a client / slave clock client
        conn, addr = clientSocket.accept()
        clientAddr = str(addr[0]) + ":" + str(addr[1])
 
        # thStartConnecting = threading.Thread(target = startReceivingClockTime,args = (conn,clientAddr, ))
        # thStartConnecting.start()


# def startingMaster(id, ip, port, time, pTime, aDelay):
def startingClient(ip, port):
    clientSocket.bind((ip, port))
 
    clientSocket.listen()
 
    thMaster = threading.Thread(target = startConnecting)
    thMaster.start()