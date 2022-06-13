from server import startingServer
from client import startingClient
from pip._vendor.distlib.compat import raw_input

# idProcess = raw_input('ID do Processo: ')
ipHost = raw_input('IP do Host: ')
portHost = raw_input('PORTA do Host: ')
# timeProcess = raw_input('TEMPO inicial: ')
# pTimeProcess = raw_input('TEMPO de processamento: ')
# aDelayProcess = raw_input('TEMPO de atraso: ')
nodoResponsibility = raw_input('É o server?: **Digite S para sim ou N para não: ')

if nodoResponsibility.upper() == 'S':
    # startingMaster(idProcess, ipHost, portHost, timeProcess, pTimeProcess, aDelayProcess)
    startingServer(ipHost, portHost)
elif nodoResponsibility.upper() == 'N':
    # startingClient(idProcess, ipHost, portHost, timeProcess, pTimeProcess, aDelayProcess)
    startingClient(ipHost, portHost)
    

