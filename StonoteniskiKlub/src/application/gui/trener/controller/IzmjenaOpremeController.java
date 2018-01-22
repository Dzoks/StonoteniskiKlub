package application.gui.trener.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dto.Clan;
import application.model.dto.Oprema;
import application.model.dto.OpremaClana;
import application.model.dto.OpremaTip;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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
	@FXML
	private TextField txtVelicina;
	
	private Oprema oprema = null;
	private Boolean opremaKluba = false;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	public void disable() {
		if(opremaKluba) {
			comboBoxClan.setDisable(true);
		}
		if(!comboBoxTip.getSelectionModel().getSelectedItem().getImaVelicinu()) {
			txtVelicina.setDisable(true);
		}
	}
	
	public void provjeriParametre() {
		if(!comboBoxTip.getSelectionModel().getSelectedItem().getImaVelicinu()) {
			btnEvidentiraj.disableProperty().bind(comboBoxTip.selectionModelProperty().isNull());
		}
		else {
			btnEvidentiraj.disableProperty().bind(txtVelicina.textProperty().isEmpty());
		}
	}
	
	public void azurirajUBazi() {
		String velicina = "";
		if(comboBoxTip.getSelectionModel().getSelectedItem().getImaVelicinu()) {
			velicina = txtVelicina.getText();
		}
		else {
			velicina = "-";
		}
		
		if(opremaKluba) {
			DAOFactory.getDAOFactory().getOpremaDAO().UPDATE(oprema, velicina, comboBoxTip.getSelectionModel().getSelectedItem().getId());
		}
		else {
			DAOFactory.getDAOFactory().getOpremaClanaDAO().UPDATE((OpremaClana)oprema, comboBoxClan.getSelectionModel().getSelectedItem().getId());
			DAOFactory.getDAOFactory().getOpremaDAO().UPDATE((OpremaClana)oprema, velicina, comboBoxTip.getSelectionModel().getSelectedItem().getId());
		}
	}
	
	public void sacuvajIzmjene(ActionEvent event) {
		azurirajUBazi();
		primaryStage.close();
	}
	
	public void ucitajComboBoxeve() {
		if(!opremaKluba) {
			ObservableList<Clan> listaClanova = DAOFactory.getDAOFactory().getOpremaClanaDAO().SELECT_AKTIVNE();
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
		
		ObservableList<OpremaTip> listaTipaOpreme = DAOFactory.getDAOFactory().getOpremaTipDAO().SELECT_ALL();
		comboBoxTip.setItems(listaTipaOpreme);
		int brojac = 0;
		for(OpremaTip opremaTip : listaTipaOpreme) {
			if(opremaTip.getId().equals(oprema.getIdTipaOpreme())) {
				break;
			}
			brojac++;
		}
		comboBoxTip.getSelectionModel().select(brojac);
		
		comboBoxTip.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<OpremaTip>() {

			@Override
			public void changed(ObservableValue<? extends OpremaTip> observable, OpremaTip oldValue, OpremaTip newValue) {
				if(newValue != null) {
					if(newValue.getImaVelicinu()) {
						txtVelicina.setDisable(false);
					}
					else {
						txtVelicina.setDisable(true);
					}
					provjeriParametre();
				}
			}
			
		});
		
		if(comboBoxTip.getSelectionModel().getSelectedItem().getImaVelicinu()) {
			txtVelicina.setText(oprema.getVelicina());
		}
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
			noviStage.setTitle("Stonoteniski klub");
			noviStage.initModality(Modality.APPLICATION_MODAL);
			noviStage.showAndWait();
			
			if("YES".equals(controller.getPovratnaVrijednost())) {
				OpremaTip noviTipOpreme = controller.vratiTipOpreme();
				DAOFactory.getDAOFactory().getOpremaTipDAO().INSERT(noviTipOpreme);
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
