/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A_estrella;

import java.util.ArrayList;

/**
 *
 * @author Francisco
 */


public class A__Estrella__8_Puzzle {
    int nodeCounter = 0;
    int fin[][];

    public A__Estrella__8_Puzzle() {
    }

    public A__Estrella__8_Puzzle(int[][] fin) {
        this.fin = fin;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize(); //To change body of generated methods, choose Tools | Templates.
    }
 //Nodo(int id, int padre, int nivel, int ctoM int consto, int mov, int[][] ord)
    public ArrayList<Nodo> estrella(Nodo inicio, Nodo fin){
        ArrayList<Nodo> open = new ArrayList();
        ArrayList<Nodo> closed = new ArrayList();
        ArrayList<Nodo> hijos = new ArrayList();
        ArrayList<Nodo> res = new ArrayList();
        ArrayList<Integer> quita = new ArrayList();
        Nodo aux;
        
        open.add(inicio);
        Nodo currentNode = null;
        while(!open.isEmpty()){
            //Get Current Node
            currentNode = nodoMin(open);
            closed.add(currentNode);
            open.remove(currentNode);
            
            //Find Goal
            if(currentNode.equals(fin)){
                //Ya Acabaste 
                break;
            }
            hijos.clear();
            //Generar hijos:
            hijos = generaHijos(currentNode);
            //Descartar hijos en closed
            int n = hijos.size();
            for(int i = 0; i < n; i++){
                for(int ii = 0; ii < closed.size(); ii++){
                    if(hijos.get(i).equals(closed.get(ii))){
                        quita.add(i);
                    }    
                }
            }
            for(int i = 0; i < quita.size(); i++){
                hijos.remove(quita.get(i));
            }
            //calcula costos;
            //Previene errores si hijos se vac[ia.
            if(!hijos.isEmpty()){
                n = hijos.size();
                for(int i = 0; i<n ; i++){
                    aux = hijos.get(i);
                    aux.setDesord(manhattan(aux.ord,fin.ord));
                    aux.setCosto(Math.max(aux.desord + aux.nivel, currentNode.costo));
                    //aux.setCosto(aux.desord + aux.nivel);
                }
                //Si nodo nuevo en open con costo mayor en open los cambio,
                //si costo menor en open descarto y si no est[a agrego.
                for(int i = 0; i < n; i++){
                    if(open.contains(hijos.get(i))){
                        if(open.get(open.indexOf(hijos.get(i))).costo > hijos.get(i).costo){
                            open.set(open.indexOf(hijos.get(i)), hijos.get(i));
                        }
                    }else{
                        open.add(hijos.get(i));
                    }
                }
            }
        }
        if(currentNode.equals(fin)){
            //Sacamos la lista de pasos:
            res.add(currentNode);
            int padre = closed.indexOf(new Nodo(currentNode.padre));
            System.out.println(closed.size());
            int m = currentNode.nivel;
            Nodo aux2 = currentNode;
            for(int i = 0; i < m; i++){
                System.out.println(i + "  \n");
                aux2 = closed.get(padre);
                padre = closed.indexOf(new Nodo(aux2.padre));
                res.add(aux2);
            }
        }
        
        return res;
    }
    //Nodo(int id, int padre, int nivel, int ctoM int consto, int mov, int[][] ord)
    public ArrayList<Nodo> generaHijos(Nodo inicial){
        ArrayList<Nodo> res = new ArrayList();
        //prepara vars;
        int padre = inicial.id;
        int nivel = inicial.nivel +1;
        //Encuentra el cero
        int ii;
        int x = 0; 
        int y = 0;
        int i = 0;
        while(i < 3){
            ii = 0;
            while(ii < 3){
               if(inicial.ord[i][ii] == 0){
                   x = i;
                   y = ii;
                   i = 5;
                   ii = 5;
               } 
               ii++;
            }
            i++;
        }
        //genera nodos
        int auxNum;
        for(i = 1; i < 5; i++){
            if(validaMov(x,y,i)){
                int[][] aux =  copiaPuzzle(inicial.ord);
                switch(i){
                    case 1: //arriba
                        auxNum = aux[x][y];
                        aux[x][y] = aux[x-1][y];
                        aux[x-1][y] = auxNum;
                        break;
                    case 2: //der
                        auxNum = aux[x][y];
                        aux[x][y] = aux[x][y+1];
                        aux[x][y+1] = auxNum;
                        break;
                    case 3: //abajo
                        auxNum = aux[x][y];
                        aux[x][y] = aux[x+1][y];
                        aux[x+1][y] = auxNum;
                        break;
                    case 4:  //izq
                        auxNum = aux[x][y];
                        aux[x][y] = aux[x][y-1];
                        aux[x][y-1] = auxNum;
                        break;
                }
//Nodo(int id, int padre, int nivel, int ctoM int consto, int mov, int[][] ord)
                res.add(new Nodo(nodeCounter++,padre,nivel,0,0,i,aux));
                
            }
        }
        return res;
    }
    
    public static int[][] copiaPuzzle(int[][] b){
        int[][] a = new int[3][3];
        for(int i =0; i<3; i++){
            System.arraycopy(b[i], 0, a[i], 0, 3);
        }
        return a;
    }
    
    public Nodo nodoMin(ArrayList<Nodo> arre){
        int minimo = 1000000000;
        int indx = 1000000;
        for(int i =0; i < arre.size(); i++){
            if(arre.get(i).costo < minimo){
                minimo = arre.get(i).costo;
                indx = i;
            }
            if(arre.get(i).costo == minimo){
                if((hamming(arre.get(indx).ord)+arre.get(indx).nivel) > hamming(arre.get(i).ord)+arre.get(i).nivel){
                    minimo = arre.get(i).costo;
                    indx = i;
                }
            }
        }
        Nodo aux = arre.get(indx);
        arre.remove(indx);
        return aux;
    }
    
    //Cuenta movimientos para que cada cuadro termine en su lugar.
    public static int manhattan(int[][] inicio, int[][] fin){
        int costo = 0;
        int j, jj;
        for(int i = 0; i < 3; i++){
            for(int ii = 0; ii < 3; ii++){
                if(inicio[i][ii] != fin[i][ii]){
                    j= 0;
                    while(j < 3){
                        jj = 0;
                        while(jj < 3){
                            if(inicio[i][ii] == fin[j][jj]){
                                costo = costo + Math.abs(i-j) + Math.abs(ii-jj);
                                j = 3;
                                jj = 3;
                            }
                            jj++;
                        }
                        j++;
                    }
                }
            }
        }
        return costo;
    }
    
    public int hamming(int[][] inicio){
        int costo = 0;
        for(int i = 0; i < 3; i++){
            for(int ii = 0; ii < 3; ii++){
                if(inicio[i][ii] != fin[i][ii]){
                    costo++;
                }
            }
        }
        return costo;
        
    }
    
    
    
    
    //mov:
    //El cero lo muevo hacia:
    // 1 = arriba 
    // 2 = derecha
    // 3 = abajo
    // 4 = izquireda
    public static boolean validaMov(int i1, int i2, int mov){
        boolean res = true;
        switch(mov){
            case 1:   //arriba
                if(i1 == 0 ){
                    res = false;
                }
                break;
            case 2:  //derecha
                if(i2 == 2 ){
                    res = false;
                }
                break;
            case 3:   //abajo
                if(i1 == 2 ){
                    res = false;
                }
                break;
            case 4:   //izquierda
                if(i2 == 0 ){
                    res = false;
                }
                break;
        }
    
        return res;
    }
    
    public static void printarre(int[][] arre){
        String aux = "";
        for(int i = 0; i < 3; i++){
            for(int ii = 0; ii < 3; ii++){
                aux = aux + arre[i][ii] + " ";
                if((ii+1)%3 != 0){
                    aux = aux + "| ";
                }
            }
            if((i+1)%3 != 0){
                aux = aux + "\n" + "----------" + "\n";
            }else{
                aux = aux + "\n";
            }
        }
        System.out.println(aux);
    }
  
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        int[][] arreIni = new int[3][3];
        arreIni[0][0] = 0;
        arreIni[0][1] = 2;
        arreIni[0][2] = 6;
        arreIni[1][0] = 5;
        arreIni[1][1] = 8;
        arreIni[1][2] = 4;
        arreIni[2][0] = 3;
        arreIni[2][1] = 7;
        arreIni[2][2] = 1;
        printarre(arreIni);
        
        int[][] arreFin = new int[3][3];
        arreFin[0][0] = 1;
        arreFin[0][1] = 2;
        arreFin[0][2] = 3;
        arreFin[1][0] = 4;
        arreFin[1][1] = 5;
        arreFin[1][2] = 6;
        arreFin[2][0] = 7;
        arreFin[2][1] = 8;
        arreFin[2][2] = 0;
        printarre(arreFin);
        
        
        int aux = manhattan(arreIni, arreFin);
        Nodo nodoIni = new Nodo(0,0,0,aux,aux,0,arreIni);
        Nodo nodoFin = new Nodo(1000000000,0,0,0,0,0,arreFin);
        
       A__Estrella__8_Puzzle prueba = new A__Estrella__8_Puzzle(arreFin);
        ArrayList<Nodo> res = new ArrayList();
        res = prueba.estrella(nodoIni, nodoFin);
        int m = res.size();
        for(int i = 0; i < m; i++){
            System.out.println(i);
            System.out.println("\n");
            System.out.println("-------------------------------------");
            System.out.println("Padre:  "+ res.get(m-i-1).padre);
            System.out.println("Nodo: " + res.get(m-i-1).id);
            printarre(res.get(m-i-1).ord);
            System.out.println("-------------------------------------");
            
        }    
    }
}
