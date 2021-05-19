class Contador:

    def __init__(self):
        self.evaluados = 0
        self.eliminados = 0

    def suma_eval(self):
        self.evaluados +=1

    def suma_elim(self):
        self.eliminados += 1