package tdas;

import java.util.LinkedList;

public class ArbolBinario {
	    private Nodo raiz;
	    private int num_nodos;
	    private int alt;
	 
	    public ArbolBinario() {
	        raiz = null;
	        num_nodos = 0;
	        alt = 0;
	    }
	 
	 
	    public Nodo getRaiz() {
	        return raiz;
	    }
	 
	    public void setRaiz(Nodo raiz) {
	        this.raiz = raiz;
	    }
	 
	    public int getNumNodos() {
	        return num_nodos;
	    }
	 
	    //Recorrido preorden, recibe el nodo a empezar (raiz) y una linkedlist para ir guardando el recorrido
	    public void preorden(Nodo aux,LinkedList recorrido){
	          if (aux != null)
	          {
	        	  recorrido.add(aux.getDato());
	              preorden (aux.getIzq(),recorrido);
	              preorden (aux.getDer(),recorrido);
	          }
	    }
	    //Recorrido inorden, recibe el nodo a empezar (raiz) y una linkedlist para ir guardando el recorrido
	    public void inorden(Nodo aux,LinkedList recorrido){
	          if (aux != null)
	          {
	              inorden (aux.getIzq(),recorrido);
	              recorrido.add(aux.getDato());
	              inorden (aux.getDer(),recorrido);
	          }
	    }
	    //Recorrido postorden, recibe el nodo a empezar (raiz) y una linkedlist para ir guardando el recorrido
	    public void postorden(Nodo aux,LinkedList recorrido){
	          if (aux != null)
	          {
	              postorden (aux.getIzq(),recorrido);
	              postorden (aux.getDer(),recorrido);
	              recorrido.add(aux.getDato());
	          }
	    }
	    //Recorrido por nivel, recibe el nodo a empezar (raiz) y una linkedlist para ir guardando el recorrido
	    public void porNivel(Nodo aux,LinkedList recorrido){
	        LinkedList<Nodo> cola = new LinkedList<Nodo>();
	        cola.addLast(aux);
	        while(cola.size()> 0){
	            Nodo tmp = cola.pollFirst();
	            recorrido.add(tmp.getDato());
	            if(tmp.getIzq()!=null){
	                cola.addLast(tmp.getIzq());
	            }
	            if(tmp.getDer()!=null){
	                cola.addLast(tmp.getDer());
	            } 
	        }
	    }
	 
	 
	    private void altura (Nodo aux,int nivel)  {
	        if (aux != null) {    
	            altura(aux.getIzq(),nivel+1);
	            alt = nivel;
	            altura(aux.getDer(),nivel+1);
	        }
	    }
	    //Devuleve la altura del arbol
	    public int getAltura(){
	        altura(raiz,1);
	        return alt;
	    }
}
