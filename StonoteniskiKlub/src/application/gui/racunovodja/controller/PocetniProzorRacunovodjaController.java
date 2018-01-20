package application.gui.racunovodja.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PocetniProzorRacunovodjaController extends BaseController implements Initializable{
	@FXML
	private Button btnEvidentiranjeNovcanihSredstava;
	@FXML
	private Button btnUplateClanarine;
	@FXML
	private Button btnUplataTurnir;
	@FXML
	private Button btnDonacije;
	@FXML
	private Button btnPlate;
	@FXML
	private Button btnObracunavanjePlata;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public void idiNaEvidentiranjeNovcanihSredstava() {
		Stage noviStage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/racunovodja/view/EvidentiranjeNovcanihSredstavaView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root,800,550);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub");
			noviStage.initModality(Modality.APPLICATION_MODAL);
			noviStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void idiNaObracunavanjePlata() {
		Stage noviStage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/racunovodja/view/ObracunavanjePlateView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root,800,700);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub - rad sa finansijama");
			noviStage.initModality(Modality.APPLICATION_MODAL);
			noviStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void idiNaEvidentiranjeUplataZaTurnir() {
		Stage noviStage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/racunovodja/view/EvidentiranjeUplataZaTurnirView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root,761,484);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub - rad sa finansijama");
			noviStage.initModality(Modality.APPLICATION_MODAL);
			noviStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void idiNaEvidentiranjeClanarina() {
		Stage noviStage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/racunovodja/view/EvidentiranjeClanarinaView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root,761,484);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub - rad sa finansijama");
			noviStage.initModality(Modality.APPLICATION_MODAL);
			noviStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void idiNaEvidentiranjeDonacija() {
		Stage noviStage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/racunovodja/view/EvidentiranjeDonacijaView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root,761,484);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub - rad sa finansijama");
			noviStage.initModality(Modality.APPLICATION_MODAL);
			noviStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void idiNaEvidentiranjeIsplacenihPlata() {
		Stage noviStage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/racunovodja/view/EvidentiranjeIsplacenihPlataView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root,761,484);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub - rad sa finansijama");
			noviStage.initModality(Modality.APPLICATION_MODAL);
			noviStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void idiNaTroskoviOprema() {
		Stage noviStage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/racunovodja/view/EvidentiranjeSredstavaZaOpremu.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root,761,484);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub - rad sa finansijama");
			noviStage.initModality(Modality.APPLICATION_MODAL);
			noviStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void idiNaTroskoviTurnir() {
		Stage noviStage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/racunovodja/view/EvidentiranjeSredstavaZaTurnire.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root,761,484);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub - rad sa finansijama");
			noviStage.initModality(Modality.APPLICATION_MODAL);
			noviStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
