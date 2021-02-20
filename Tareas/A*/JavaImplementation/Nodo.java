/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A_estrella;

import java.util.Arrays;

/**
 *
 * @author Francisco
 */
public class Nodo {
    int id;
    int padre;
    int nivel;
    int desord;
    int costo;
    int mov;
    int ord[][];

    public Nodo() {
    }

    public Nodo(int id) {
        this.id = id;
    }
    

    public Nodo(int id, int padre, int nivel, int desord, int consto, int mov, int[][] ord) {
        this.id = id;
        this.padre = padre;
        this.nivel = nivel;
        this.desord = desord;
        this.costo = consto;
        this.mov = mov;
        this.ord = ord;
    }
    
    public Nodo(int[][] ord) {
        this.ord = ord;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.id;
        hash = 23 * hash + Arrays.deepHashCode(this.ord);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Nodo other = (Nodo) obj;
        if (this.id == other.id) {
            return true;
        }
        if (Arrays.deepEquals(this.ord, other.ord)) {
            return true;
        }
        return false;
    }


    
    
    

    public int[][] getOrd() {
        return ord;
    }

    public void setOrd(int[][] ord) {
        this.ord = ord;
    }

    public int getPadre() {
        return padre;
    }

    public void setPadre(int padre) {
        this.padre = padre;
    }
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getDesord() {
        return desord;
    }

    public void setDesord(int desord) {
        this.desord = desord;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }
    

    public int getMov() {
        return mov;
    }

    public void setMov(int mov) {
        this.mov = mov;
    }
    
    
}
