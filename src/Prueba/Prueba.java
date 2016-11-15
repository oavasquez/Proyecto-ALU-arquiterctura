package Prueba;

import java.io.Console;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jdk.nashorn.internal.ir.SetSplitState;
import tdas.ArbolBinario;
import tdas.Nodo;

public class Prueba extends Application {

	Stage principal;
	VentanaController ventanaController;
	public static void main(String[] args){
		launch(args);
	}
	
	public void start(Stage stage){
		
		try{
			//Parent root = FXMLLoader.load(getClass().getResource("Ventana.fxml"));
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Ventana.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			Scene scene = new Scene(root);
			ventanaController = loader.getController();
			ventanaController.setPrueba(this);
			stage.setScene(scene);
			principal = stage;
			stage.show();
		}
		catch(Exception e){
			
		}
		
	}
	
	public void limpiar(){
		try{
			//Parent root = FXMLLoader.load(getClass().getResource("Ventana.fxml"));
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Ventana.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			Scene scene = new Scene(root);
			ventanaController = loader.getController();
			ventanaController.setPrueba(this);
			principal.setScene(scene);
			principal.show();
		}
		catch(Exception e){
			
		}
	}
}
