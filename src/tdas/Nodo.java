package tdas;

public class Nodo {

	private Object dato;
	private Nodo izq,der;
	public Nodo(Object dato){
		this.dato = dato;
	    izq = null;
	    der = null;
	}
	 
	public Object getDato() {
		return dato;
	}
	 
	public Nodo getIzq() {
		return izq;
	}
	 
	public Nodo getDer() {
		return der;
	}
	 
	public void setDato(Object dato) {
		this.dato = dato;
	}
	 
	public void setIzq(Nodo izq) {
		this.izq = izq;
	}
	 
	public void setDer(Nodo der) {
		this.der = der;
	} 
}
