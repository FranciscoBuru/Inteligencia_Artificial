#Maneja movimientos y dibujos.
import pygame
pygame.init()
from .constants import BLACK, ROWS, RED, SQUARE_SIZE, WHITE, COLS
from .piece import Piece

class Board:
    def __init__(self):
        self.board = []
        self.red_left = self.white_left = 12
        self.red_kings = self.white_kings = 0
        self.create_board()

    def evaluate(self, turno):
#Defensivo
    #    if turno<15:
    #        return 15*self.white_left - 8*self.red_left + self.white_kings*0.5 -self.red_kings*0
    #    elif turno <20:
    #        return 10*self.white_left - 5*self.red_left + self.white_kings -self.red_kings*0
    #    else:
    #        return 30*self.white_left - 5*self.red_left + 0*self.white_kings - 0*self.red_kings

#Ofensivo
       if turno<15:
           return 8*self.white_left - 15*self.red_left + self.white_kings*0.5 -self.red_kings*0
       elif turno <20:
           return 10*self.white_left - 15*self.red_left + self.white_kings -self.red_kings*0
       else:
           return 30*self.white_left - 5*self.red_left + 0*self.white_kings - 0*self.red_kings


#Equilibrado
    #    if turno<15:
    #        return 8*self.white_left - 8*self.red_left + self.white_kings*0.5 -self.red_kings*0
    #    elif turno <20:
    #        return 10*self.white_left - 5*self.red_left + self.white_kings -self.red_kings*0
    #    else:
    #        return 30*self.white_left - 5*self.red_left + 0*self.white_kings - 0*self.red_kings
        
    def get_all_pieces(self, color):
        pieces = []
        for row in self.board:
            for piece in row:
                if piece != 0 and piece.color == color:
                    pieces.append(piece)
        return pieces
    
    def draw_squares(self, win):
        win.fill(BLACK)
        for row in range(ROWS):
            for col in range(row % 2, ROWS, 2): #Pinta columnas alternando color iniciaL Negro es fondo so solo pinto rojos
                pygame.draw.rect(win, RED, (row*SQUARE_SIZE, col*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE))


    def create_board(self):
        for row in range(ROWS):
            self.board.append([])
            for col in range(COLS):
                if col % 2 == ((row + 1) % 2):
                    if row < 3:
                        self.board[row].append(Piece(row, col, WHITE))
                    elif row > 4:
                        self.board[row].append(Piece(row, col, RED))
                    else:
                        self.board[row].append(0)
                else:
                    self.board[row].append(0)

    def draw(self, win):
        self.draw_squares(win)
        pygame.draw.line(win, WHITE, (1,1050),(800-1,1050), 20)
        for row in range(ROWS):
            for col in range(COLS):
                piece = self.board[row][col]
                if piece != 0:
                    piece.draw(win)
        #GUI
        pygame.draw.line(win, WHITE, (800,0),(800,800), 5)
        pygame.draw.line(win, WHITE, (1045,0),(1045,800), 5)
        #Titulo
        font1 = pygame.font.SysFont('chalkduster.ttf', 72)
        img1 = font1.render('Damas', True, WHITE)
        win.blit(img1, (840, 10))
        #Reset
        font2 = pygame.font.SysFont('chalkduster.ttf', 40)
        img2 = font2.render('Reiniciar', True, WHITE)
        rect = img2.get_rect()
        pygame.draw.rect(img2, WHITE, rect,3)
        win.blit(img2, (860, 400))
        #Dificultad
        font3 = pygame.font.SysFont('chalkduster.ttf', 50)
        img3 = font3.render('Dificultad:', True, WHITE)
        win.blit(img3, (840, 120))
        #1
        img4 = font3.render('1', True, WHITE)
        rect = img4.get_rect()
        pygame.draw.rect(img4, WHITE, rect,3)
        win.blit(img4, (870, 180))
        #2
        img4 = font3.render('2', True, WHITE)
        rect = img4.get_rect()
        pygame.draw.rect(img4, WHITE, rect,3)
        win.blit(img4, (910, 180))
        #3
        img4 = font3.render('3', True, WHITE)
        rect = img4.get_rect()
        pygame.draw.rect(img4, WHITE, rect,3)
        win.blit(img4, (950, 180))
        #minimax
        img5 = font3.render('Minimax', True, WHITE)
        rect = img5.get_rect()
        pygame.draw.rect(img5, WHITE, rect,3)
        win.blit(img5, (840, 240))
        #alfabeta
        img6 = font3.render('Alpha-Beta', True, WHITE)
        rect = img6.get_rect()
        pygame.draw.rect(img6, WHITE, rect,3)
        win.blit(img6, (840, 300))
        #Info
        img7 = font3.render('Info', True, WHITE)
        win.blit(img7, (890, 500))


    def move(self, piece, row, col):
        self.board[piece.row][piece.col],  self.board[row][col] = self.board[row][col], self.board[piece.row][piece.col]
        piece.move(row, col)
        if(row == ROWS-1 or row == 0):
            if(not piece.is_king()):
                piece.make_king()
                if piece.color == WHITE:
                    self.white_kings += 1
                    #print(self.white_kings)
                else:
                    self.red_kings += 1
    
    def get_piece(self, row, col):
        return self.board[row][col]


    def get_valid_moves(self, piece):
        moves = {}
        left = piece.col -1
        right = piece.col +1
        row = piece.row
        if piece.color == RED or piece.king:
            moves.update(self._traverse_left(row-1, max(row-3,-1), -1, piece.color, left))
            moves.update(self._traverse_right(row-1, max(row-3,-1), -1, piece.color, right))
        if piece.color == WHITE or piece.king:
            moves.update(self._traverse_left(row+1, min(row+3,ROWS), 1, piece.color, left))
            moves.update(self._traverse_right(row+1, min(row+3,ROWS), 1, piece.color, right))
        return moves

    def _traverse_left(self, start, stop, step, color, left, skipped=[]):
        moves = {}
        last = []
        for r in range(start, stop, step):
            if left < 0:
                break
            current = self.board[r][left]
            if current == 0:  #Found empty square. 
                if skipped and not last:
                    break
                elif skipped:
                    moves[(r,left)] = last + skipped
                else:
                    moves[(r,left)]=last
                if last:
                    if step == -1:
                        row = max(r-3,0)
                    else:
                        row = min(r+3, ROWS)
                    moves.update(self._traverse_left(r+step, row, step, color, left-1, skipped=last))
                    moves.update(self._traverse_right(r+step, row, step, color, left+1, skipped=last))
                break
            elif current.color == color:
                break
            else:
                last = [current]
            left -= 1
        return moves

    def _traverse_right(self, start, stop, step, color, right, skipped=[]):
        moves = {}
        last = []
        for r in range(start, stop, step):
            if right >= COLS:
                break
            current = self.board[r][right]
            if current == 0:  #Found empty square. 
                if skipped and not last:
                    break
                elif skipped:
                    moves[(r,right)] = last + skipped
                else:
                    moves[(r,right)]=last
                if last:
                    if step == -1:
                        row = max(r-3,0)
                    else:
                        row = min(r+3, ROWS)
                    moves.update(self._traverse_left(r+step, row, step, color, right-1, skipped=last))
                    moves.update(self._traverse_right(r+step, row, step, color, right+1, skipped=last))
                break
            elif current.color == color:
                break
            else:
                last = [current]
            right += 1
        #print('moves:   ')
        #z0print(moves)
        return moves

    def remove(self, pieces):
        for piece in pieces:
            self.board[piece.row][piece.col] = 0
            if piece != 0:
                if piece.color == RED:
                    self.red_left -=1
                    if piece.is_king():
                        self.red_kings -=1
                else:
                    self.white_left -=1
                    if piece.is_king():
                        self.white_kings -=1

    def winner(self):
        if self.red_left <=0:
            print("whitwewwwwwww")
            return WHITE
        elif self.white_left <=0:
            print("reddddddddddddd")
            return RED
        else:
            return None
