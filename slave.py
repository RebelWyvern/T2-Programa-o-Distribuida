from ast import arg
import sys
import threading
import datetime
import socket
import time

dados = {}

def sendTime(slave, dados):
    while True:
        slave.send(str(dados.time+dados.adelay).encode())

def receiveTime():
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
    if 'isServer':
        print('I am a server')
    else:
        slave = socket.socket()
        print('provide server IP: ')
        servIP = input('prompt')
        slave.connect(servIP, slave.port)

def getDiferencaRelogios():
    
        dadosAtuaisCliente = dados.copy 
        listaTemposDiferentes = list(client['adelay'] for client_addr, client in dados.items())
 
        somaDiferenca = sum(listaTemposDiferentes, \
                                datetime.timedelta(0,0))

        mediaDiferenca = somaDiferenca \
                               / len(dados)

        return mediaDiferenca


def sincronizaRelogios():
    while True:
        print("novo ciclo de sincronização iniciada")
        print("clientes a serem sincronizados: " + \
                                        str(len(dados)) )

        if len(dados) > 0:
            mediaDiferenca = getDiferencaRelogios()

            for client_addr, client in dados.items():
                try:
                    tempoSincronizado = \
                                 datetime.datetime.now() + \
                                                      mediaDiferenca  

                    client['ip'].send(str(tempoSincronizado).encode())#não tenho certeza se é client['ip']

                except Exception as e:
                    print("Something went wrong while " + \
                          "sending synchronized time " + \
                          "through " + str(client_addr))     
                else :
                    print("sem dados do cliente")
 
                print("\n\n")              