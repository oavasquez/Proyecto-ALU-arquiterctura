package Prueba;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import tdas.ArbolBinario;
import tdas.Nodo;


public class VentanaController implements Initializable{

	@FXML TextField txtExpresionMatematica;
	@FXML TextField txtResultado;
	@FXML Button btnCalcular;
	@FXML AnchorPane h;
	@FXML ListView<String> lwPila;
	@FXML ListView<String> lwVPila;
	@FXML Label lbAdvert;
	private ObservableList<String> listaPila;
	private ObservableList<String> listaVPila;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listaPila = FXCollections.observableArrayList();
		listaVPila = FXCollections.observableArrayList();
		lbAdvert.setVisible(false);
	}
	
	
	
	Prueba prueba;
	
	public void setPrueba(Prueba prueba){
		this.prueba = prueba;
	}
	
	public void btnLimpiarClick(){
		prueba.limpiar();
	}
	
	public void btnCalcularClick(){
		String cadena = txtExpresionMatematica.getText();
		ArrayList<String> cadenaArreglada = new ArrayList<String>();
		
		
		
	    String expr = depurar(cadena);
	    String[] arrayInfix = expr.split(" ");
		
	    Stack < String > E = new Stack < String > (); //Pila entrada
	    Stack < String > P = new Stack < String > (); //Pila temporal para operadores
	    Stack < String > S = new Stack < String > (); //Pila salida
	    
	    for (int i = arrayInfix.length - 1; i >= 0; i--) {
	      E.push(arrayInfix[i]);
	      
	    }
  
	    try {
	        //post order
	        while (!E.isEmpty()) {
	        	
	          switch (pref(E.peek())){
	            case 1:
	              P.push(E.pop());
	             
	              
	              break;
	            case 3:
	            case 4:
	              while(pref(P.peek()) >= pref(E.peek())) {
	                S.push(P.pop());
	              }
	              P.push(E.pop());
	              break; 
	            case 2:
	              while(!P.peek().equals("(")) {
	                S.push(P.pop());
	              }
	              P.pop();
	              E.pop();
	              break; 
	            default:
	              S.push(E.pop()); 
	          } 
	        }
	      
	        String infix = expr.replace(" ", "");
	        String postfix = S.toString().replaceAll("[\\]\\[,]", "");
	        postfix = postfix.replace(" ", "");
	        pilaaArray(S, cadenaArreglada);

	      }catch(Exception ex){ 
	        System.out.println("Error en la expresión algebraica");
	        System.err.println(ex);
	      }
		
		
		ArrayList<Nodo> listaNodos = new ArrayList<Nodo>();
		listaNodos = crearNodos(cadenaArreglada);
		
		Nodo raiz = crearRaiz(listaNodos);
		ArbolBinario nArbol = new ArbolBinario();
		nArbol.setRaiz(raiz);
		
		LinkedList lista = new LinkedList();
		nArbol.postorden(raiz, lista);
		
		imprimirList(lista);
		
		
		pintaArbol(h, raiz, 250, 100, 10, 10, 0);
		
		
		
		resolverOperaciones(lista);
		lbAdvert.setVisible(true);
	}
	
	//Depurar expresión algebraica
	  private static String depurar(String s) {
	    s = s.replaceAll("\\s+", ""); //Elimina espacios en blanco
	    s = "(" + s + ")";
	    String simbols = "+-*/()";
	    String str = "";
	    
	 
	    //Deja espacios entre operadores
	    for (int i = 0; i < s.length(); i++) {
	      if (simbols.contains("" + s.charAt(i))) {
	        str += " " + s.charAt(i) + " ";
	      }else str += s.charAt(i);
	    }
	    
	    return str.replaceAll("\\s+", " ").trim();
	  }
	
	  public void pilaaArray(Stack pila, ArrayList<String> lista){
		  ArrayList<String> temporal = new ArrayList<String>();
		  lista.clear();
		  while(!pila.isEmpty())
			  temporal.add(pila.pop().toString());
		  
		  int size = temporal.size();
		  for(int i = 0; i < size; i++){
			  lista.add(temporal.get(temporal.size() - 1));
			  temporal.remove(temporal.size() - 1);
		  }
	  }
	
	public ArrayList<Nodo> crearNodos(ArrayList<String> cadenaTokens){
		ArrayList<Nodo> listaNodos = new ArrayList<Nodo>();
		for(int i = 0; i < cadenaTokens.size(); i++){
			listaNodos.add(new Nodo(cadenaTokens.get(i)));
		}
		
		return listaNodos;
	}
	
	public Nodo crearRaiz(ArrayList<Nodo> listaNodos){
		while(listaNodos.size() > 1){
			for(int i = 0; i < listaNodos.size(); i++){
				try{
					int j = Integer.parseInt((String)listaNodos.get(i).getDato());
				}
				catch(Exception exp){
					if(i > 1){
						listaNodos.get(i).setIzq(listaNodos.get(i-2));
						listaNodos.remove(i - 2);
					}
					i--;
					if(i > 0){ 
						listaNodos.get(i).setDer(listaNodos.get(i-1));
						listaNodos.remove(i - 1);
					}
					i--;
				}
			}
		}
		
		return(listaNodos.get(0));
	}
	
	public void imprimirList(LinkedList lista){
		String cadena="";
		for(int i = 0; i < lista.size(); i++){
			cadena=cadena+lista.get(i);
			listaVPila.add(0,lista.get(i).toString());
		}
		lwVPila.setItems(listaVPila);
		txtResultado.setText(cadena);
	}
	
	public int resolverOperaciones(LinkedList lista){
		Stack<Integer> pila = new Stack<Integer>();
		Stack<String> spila = new Stack<String>();
		
		
		for(int i = 0; i < lista.size(); i++){
			try{
				
				
				pila.push(Integer.parseInt((String)lista.get(i)));
				
				System.out.println("push " + lista.get(i));
				
				 listaPila.add(0,(String)lista.get(i));
				 lwPila.setItems(listaPila);
				
				
			}
			catch(Exception exp){
				if(lista.get(i).toString().endsWith("+")){
					
					
					listaPila.add(0,"-----op + -----");
					listaPila.add(0,listaPila.get(2).toString()+"+"+listaPila.get(1).toString());
					
					int operacion = pila.pop() + pila.pop();	
					
					pila.push(operacion);
					
					
					
				}
				
				if(lista.get(i).toString().endsWith("-")){
					
					int b = pila.pop();
					int a = pila.pop();
					int operacion = a - b;
					listaPila.add(0,"-----op - -----");
					listaPila.add(0,listaPila.get(2).toString()+"-"+listaPila.get(1).toString());	
					pila.push(operacion);
					
					}
					
				}
				
				if(lista.get(i).toString().endsWith("*")){
					
					
						
					
					int operacion = pila.pop() * pila.pop();
					listaPila.add(0,"-----op * -----");
					listaPila.add(0,listaPila.get(2).toString()+"*"+listaPila.get(1).toString());
					
					try{
						if(!listaPila.get(4).isEmpty()){
							
						if(!(listaPila.get(4).equals("-----op * -----")||
								listaPila.get(4).equals("-----op / -----")))	{
						listaPila.add(1,listaPila.get(4).toString());
						}
						}
						}
						catch(Exception e){
							
						}
					
					pila.push(operacion);
					lwPila.setItems(listaPila);
					}
				
				
				if(lista.get(i).toString().endsWith("/")){
					
					int b = pila.pop();
					int a = pila.pop();
					int operacion = a / b;
					listaPila.add(0,"-----op / -----");
					listaPila.add(0,listaPila.get(2).toString()+"/"+listaPila.get(1).toString());	
					
					try{
					if(!listaPila.get(4).isEmpty()){
						
					if(!(listaPila.get(4).equals("-----op * -----")||
							listaPila.get(4).equals("-----op / -----")))	{
					listaPila.add(1,listaPila.get(4).toString());
					}
					}
					}
					catch(Exception e){
						
					}
					pila.push(operacion);
					lwPila.setItems(listaPila);
					}
				
			}
	
		
		return pila.pop();
	}
	
	public void pintaArbol(AnchorPane h, Nodo n, int x, int y, int xoff, int yoff, int nivel){
		
		if (n == null) return;		
		
	     if(n.getIzq()!= null){
	    	 Line linea = new Line(x + 20, y + 10, x - xoff + (nivel * 2) + 4, y + yoff + 30);
	    	 h.getChildren().add(linea);
	     }
	     if(n.getDer()!= null){
	    	 Line linea = new Line(x + 20, y + 10, x + xoff + (nivel * 2) + 30, y + yoff + 30);
	    	 h.getChildren().add(linea);
	     }
	 
	    Rectangle rectanguloR = new Rectangle(x + 10, y + 10, xoff + 10, yoff + 10);
		rectanguloR.setArcWidth(70);
		rectanguloR.setArcHeight(70);
		rectanguloR.setStrokeWidth(1.5);
		rectanguloR.setStroke(Color.BLACK);
		rectanguloR.setFill(Color.WHITE);
		h.getChildren().add(rectanguloR);
	 
		Text text = new Text(x + 16, y + 22, n.getDato().toString());
		text.setStroke(Color.BLACK);
		text.setFill(Color.BLACK);
		h.getChildren().add(text);
	 
	     pintaArbol(h, n.getIzq(), (int)(x - xoff - 10), (y + yoff + 10), xoff + nivel * 2, yoff, nivel+1);
	     pintaArbol(h, n.getDer(), (int)(x + xoff + 10), (y + yoff + 10), xoff - nivel * 2, yoff, nivel+1);
	}
	
	private static int pref(String op) {
	    int prf = 99;
	    if (op.equals("^")) prf = 5;
	    if (op.equals("*") || op.equals("/")) prf = 4;
	    if (op.equals("+") || op.equals("-")) prf = 3;
	    if (op.equals(")")) prf = 2;
	    if (op.equals("(")) prf = 1;
	    return prf;
	  }
	private void esperar(){
		for(int i=0;i<10000;i++){
			for(int j=0;i<10;i++){
				
			}
		}
		
		
	}

	
}
