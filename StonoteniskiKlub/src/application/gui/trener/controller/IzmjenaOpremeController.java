package application.gui.trener.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.OpremaClanaDAO;
import application.model.dao.OpremaDAO;
import application.model.dao.OpremaKlubaDAO;
import application.model.dao.OpremaTipDAO;
import application.model.dto.Clan;
import application.model.dto.DistributerOpreme;
import application.model.dto.Oprema;
import application.model.dto.OpremaClana;
import application.model.dto.OpremaTip;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class IzmjenaOpremeController extends BaseController implements Initializable{
	@FXML
	private ComboBox<OpremaTip> comboBoxTip;
	@FXML
	private ComboBox<Clan> comboBoxClan;
	@FXML
	private Button btnDodajTipOpreme;
	@FXML
	private Button btnEvidentiraj;
	
	private Oprema oprema = null;
	private Boolean opremaKluba = false;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public void disable() {
		if(opremaKluba) {
			comboBoxClan.setDisable(true);
		}
	}
	
	public void azurirajUBazi() {
		if(opremaKluba) {
			OpremaDAO.UPDATE(oprema, comboBoxTip.getSelectionModel().getSelectedItem().getId());
		}
		else {
			OpremaClanaDAO.UPDATE((OpremaClana)oprema, comboBoxClan.getSelectionModel().getSelectedItem().getId());
			OpremaDAO.UPDATE((OpremaClana)oprema, comboBoxTip.getSelectionModel().getSelectedItem().getId());
		}
	}
	
	public void sacuvajIzmjene(ActionEvent event) {
		azurirajUBazi();
		primaryStage.close();
	}
	
	public void ucitajComboBoxeve() {
		if(!opremaKluba) {
			ObservableList<Clan> listaClanova = OpremaClanaDAO.SELECT_AKTIVNE();
			comboBoxClan.setItems(listaClanova);
			int brojac=0;
			for(Clan clan : listaClanova) {
				if(clan.getId() == ((OpremaClana)oprema).getIdClana()) {
					break;
				}
				brojac++;
			}
			comboBoxClan.getSelectionModel().select(brojac);
		}
		
		ObservableList<OpremaTip> listaTipaOpreme = OpremaTipDAO.SELECT_ALL();
		comboBoxTip.setItems(listaTipaOpreme);
		int brojac = 0;
		for(OpremaTip opremaTip : listaTipaOpreme) {
			if(opremaTip.getId().equals(oprema.getIdTipaOpreme())) {
				break;
			}
			brojac++;
		}
		comboBoxTip.getSelectionModel().select(brojac);
	}
	
	public void idiNaDodajTipOpreme() {
		Stage noviStage = new Stage();
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/trener/view/DodajTipOpremeView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root,267,207);
			DodajTipOpremeController controller = loader.<DodajTipOpremeController>getController();
			controller.setPrimaryStage(noviStage);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub - rad sa opremom");
			noviStage.initModality(Modality.APPLICATION_MODAL);
			noviStage.showAndWait();
			if("YES".equals(controller.getPovratnaVrijednost())) {
				OpremaTip noviTipOpreme = controller.vratiTipOpreme();
				OpremaTipDAO.INSERT(noviTipOpreme);
				ucitajComboBoxeve();
				comboBoxTip.getSelectionModel().selectLast();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setOprema(Oprema opremaClana) {
		this.oprema = opremaClana;
	}

	public void setOpremaKluba() {
		this.opremaKluba = true;
	}
}
