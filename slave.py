from ast import arg
import sys
import threading
import datetime
import socket
import time

from T2.src.server import Main


def sendTime(slave, dados):
    while True:
        slave.send(str(dados.time+dados.adelay).encode())

def reciveTime():
    a = '1'

def initSlave():
    args = sys.argv[1:]
    if len(args) != 6:
        print('Wrong args. Template: <ip> <port> <time> <ptime> <adelay> <isserver>')
        return
    dados ={
    'ip' : args[0],
    'port' : arg[1],
    'time' : arg[2],
    'ptime' : arg[3],
    'adelay' : arg[4],
    'isServer' : arg[5]
    } 
    if isServer:
        print('I am a server')
    else:
        slave = socket.socket()
        print('provide server IP: ')
        servIP = input('prompt')
        slave.connect(servIP, slave.port)



