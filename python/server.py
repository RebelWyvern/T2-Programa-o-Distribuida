import ipaddress
import threading
import socket

serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

def startConnecting():
    # fetch clock time at slaves / clients
    while True:
        # accepting a client / slave clock client
        conn, addr = serverSocket.accept()
        clientAddr = str(addr[0]) + ":" + str(addr[1])
 
        # thStartConnecting = threading.Thread(target = startReceivingClockTime,args = (conn,clientAddr, ))
        # thStartConnecting.start()


# def startingMaster(id, ip, port, time, pTime, aDelay):
def startingServer(ip, port):
    
    serverSocket.setsockopt(socket.SOL_SOCKET,socket.SO_REUSEADDR, 1)

    print(ip, port)
    serverSocket.bind((ip, int(port)))
    serverSocket.listen()
 
    # thMaster = threading.Thread(target = startConnecting)
    # thMaster.start()
 
    # sync_thread = threading.Thread(
    #                       target = synchronizeAllClocks,
    #                       args = ())
    # sync_thread.start()
 
 
