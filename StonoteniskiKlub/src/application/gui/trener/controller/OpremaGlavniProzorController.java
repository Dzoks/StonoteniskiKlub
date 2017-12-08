package application.gui.trener.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.OpremaClanaDAO;
import application.model.dao.OpremaKlubaDAO;
import application.model.dto.OpremaClanaDTO;
import application.model.dto.OpremaKlubaDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class OpremaGlavniProzorController extends BaseController implements Initializable{
	@FXML
	private Button btnPretraziOpremaKluba;
	@FXML
	private Button btnDodajOpremaKluba;
	@FXML
	private Button btnPretraziOpremaClanova;
	@FXML
	private Button btnDodajOpremaClanova;
	@FXML
	private TableView<OpremaKlubaDTO> tblOpremaKluba;
	@FXML
	private TableColumn<OpremaKlubaDTO, Integer> idKlub;
	@FXML
	private TableColumn<OpremaKlubaDTO, String> tipKlub;
	@FXML
	private TableColumn<OpremaKlubaDTO, String> proizvodjacKlub;
	@FXML
	private TableColumn<OpremaKlubaDTO, String> modelKlub;
	@FXML
	private TableColumn<OpremaKlubaDTO, Boolean> doniranaKlub;
	@FXML
	private TableView<OpremaClanaDTO> tblOpremaClana;
	@FXML
	private TableColumn<OpremaClanaDTO, Integer> idClan;
	@FXML
	private TableColumn<OpremaClanaDTO, String> tipClan;
	@FXML
	private TableColumn<OpremaClanaDTO, String> proizvodjacClan;
	@FXML
	private TableColumn<OpremaClanaDTO, String> modelClan;
	@FXML
	private TableColumn<OpremaClanaDTO, String> jmbClan;
	@FXML
	private TableColumn<OpremaClanaDTO, String> imeClan;
	@FXML
	private TableColumn<OpremaClanaDTO, String> prezimeClan;
	@FXML
	private TableColumn<OpremaClanaDTO, Boolean> doniranaClan;
	@FXML
	private RadioButton rdbtnTipClan;
	@FXML
	private RadioButton rdbtnProizvodjacClan;
	@FXML
	private RadioButton rdbtnModelClan;
	@FXML
	private RadioButton rdbtnJmb;
	@FXML
	private RadioButton rdbtnIme;
	@FXML
	private RadioButton rdbtnPrezime;
	@FXML
	private TextField txtTipClan;
	@FXML
	private TextField txtProizvodjacClan;
	@FXML
	private TextField txtModelClan;
	@FXML
	private TextField txtJmb;
	@FXML
	private TextField txtIme;
	@FXML
	private TextField txtPrezime;
	@FXML
	private RadioButton rdbtnAktivna;
	@FXML
	private RadioButton rdbtnNeaktivna;
	@FXML
	private RadioButton rdbtnSva;
	@FXML
	private RadioButton rdbtnTipKlub;
	@FXML
	private RadioButton rdbtnProizvodjacKlub;
	@FXML
	private RadioButton rdbtnModelKlub;
	@FXML
	private TextField txtTipKlub;
	@FXML
	private TextField txtProizvodjacKlub;
	@FXML
	private TextField txtModelKlub;
	
	private ToggleGroup grupaClan = new ToggleGroup();
	private ToggleGroup grupaKlubVrsta = new ToggleGroup();
	private ToggleGroup grupaKlubTip = new ToggleGroup();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		popuniTabele();
		podesavanjeZaRdbtnClan();
		podesavanjaZaRdbtnKlub();
		podesavanjaZaTekstPolja();
		dodajKonteksniMeniNaOpremuKluba();
	}
	
	public void dodajKonteksniMeniNaOpremuKluba() {
		ContextMenu cm = new ContextMenu();
	    MenuItem postaviUNeaktivno = new MenuItem("Postavi u neaktivno");
	    MenuItem postaviUAktivno = new MenuItem("Postavi u aktivno");
	    MenuItem pogledajOpis = new MenuItem("Pogledaj opis");
	    
	    postaviUNeaktivno.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent t) {
	        	OpremaKlubaDTO selektovanaOprema = null;
	        	ObservableList<OpremaKlubaDTO> listaOpreme = tblOpremaKluba.getItems();
	        	if(!listaOpreme.isEmpty()) {
	        		selektovanaOprema = listaOpreme.get(tblOpremaKluba.getSelectionModel().getSelectedIndex());	        		
	        	}
	            if (selektovanaOprema != null){ 
	            	OpremaKlubaDAO.UPDATE(selektovanaOprema, false);
	            	popuniTabele();
	            	grupaKlubTip.selectToggle(null);
	            	grupaKlubVrsta.selectToggle(null);
	            	txtTipKlub.clear();
	            	txtProizvodjacKlub.clear();
	            	txtModelKlub.clear();
	            	txtTipKlub.setDisable(true);
	            	txtProizvodjacKlub.setDisable(true);
	            	txtModelKlub.setDisable(true);
	            }
	        }
	    });
	    
	    postaviUAktivno.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent t) {
	        	OpremaKlubaDTO selektovanaOprema = null;
	        	ObservableList<OpremaKlubaDTO> listaOpreme = tblOpremaKluba.getItems();
	        	if(!listaOpreme.isEmpty()) {
	        		selektovanaOprema = listaOpreme.get(tblOpremaKluba.getSelectionModel().getSelectedIndex());	        		
	        	}
	            if (selektovanaOprema != null){ 
	            	OpremaKlubaDAO.UPDATE(selektovanaOprema, true);
	            	popuniTabele();
	            	grupaKlubTip.selectToggle(null);
	            	grupaKlubVrsta.selectToggle(null);
	            	txtTipKlub.clear();
	            	txtProizvodjacKlub.clear();
	            	txtModelKlub.clear();
	            	txtTipKlub.setDisable(true);
	            	txtProizvodjacKlub.setDisable(true);
	            	txtModelKlub.setDisable(true);
	            }
	        }
	    });
	    
	    pogledajOpis.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent t) {
	        	//dodati prozor za opis
	        }
	    });
	    
	    tblOpremaKluba.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent t) {
	        	cm.getItems().clear();
	        	OpremaKlubaDTO selektovanaOprema = tblOpremaKluba.getSelectionModel().getSelectedItem();
	    	    if(selektovanaOprema.getAktivan()) {
	    	    	cm.getItems().add(postaviUNeaktivno);
	    	    }
	    	    else {
	    	    	cm.getItems().add(postaviUAktivno);
	    	    }
	    	    cm.getItems().add(pogledajOpis);
	            if(t.getButton() == MouseButton.SECONDARY)
	            {
	            	cm.show(tblOpremaKluba , t.getScreenX() , t.getScreenY());
	            }
	        }
	    });
	}
	
	public void podesavanjaZaTekstPolja() {
		txtTipKlub.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	                pretragaKlub();
	            }
	        }
	    });
		
		txtProizvodjacKlub.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	                pretragaKlub();
	            }
	        }
	    });
		
		txtModelKlub.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	                pretragaKlub();
	            }
	        }
	    });
		
		txtTipClan.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	                pretragaClan();
	            }
	        }
	    });
		
		txtProizvodjacClan.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	                pretragaClan();
	            }
	        }
	    });
		
		txtModelClan.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	                pretragaClan();
	            }
	        }
	    });
		
		txtJmb.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	                pretragaClan();
	            }
	        }
	    });
		
		txtIme.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	                pretragaClan();
	            }
	        }
	    });
		
		txtPrezime.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	                pretragaClan();
	            }
	        }
	    });
	}
	
	public void podesavanjaZaRdbtnKlub() {
		txtTipKlub.setDisable(true);
    	txtProizvodjacKlub.setDisable(true);
    	txtModelKlub.setDisable(true);
    	
    	rdbtnAktivna.setToggleGroup(grupaKlubVrsta);
		rdbtnNeaktivna.setToggleGroup(grupaKlubVrsta);
		rdbtnSva.setToggleGroup(grupaKlubVrsta);
		rdbtnTipKlub.setToggleGroup(grupaKlubTip);
		rdbtnProizvodjacKlub.setToggleGroup(grupaKlubTip);
		rdbtnModelKlub.setToggleGroup(grupaKlubTip);
		
		rdbtnAktivna.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if(isNowSelected) {
		        	ObservableList<OpremaKlubaDTO> listaOpreme = OpremaKlubaDAO.SELECT_AKTIVNOST(true);
		        	tblOpremaKluba.setItems(listaOpreme);
		        }
		    }
		});
		
		rdbtnNeaktivna.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if(isNowSelected) {
		        	ObservableList<OpremaKlubaDTO> listaOpreme = OpremaKlubaDAO.SELECT_AKTIVNOST(false);
		        	tblOpremaKluba.setItems(listaOpreme);
		        }
		    }
		});
		
		rdbtnSva.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if(isNowSelected) {
		        	ObservableList<OpremaKlubaDTO> listaOpreme = OpremaKlubaDAO.SELECT_AKTIVNOST(null);
		        	tblOpremaKluba.setItems(listaOpreme);
		        }
		    }
		});
		
		rdbtnTipKlub.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if(isNowSelected) {
		        	txtTipKlub.setDisable(false);
		        	txtProizvodjacKlub.setDisable(true);
		        	txtModelKlub.setDisable(true);
		        }
		    }
		});
		
		rdbtnProizvodjacKlub.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if(isNowSelected) {
		        	txtTipKlub.setDisable(true);
		        	txtProizvodjacKlub.setDisable(false);
		        	txtModelKlub.setDisable(true);
		        }
		    }
		});
		
		rdbtnModelKlub.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if(isNowSelected) {
		        	txtTipKlub.setDisable(true);
		        	txtProizvodjacKlub.setDisable(true);
		        	txtModelKlub.setDisable(false);
		        }
		    }
		});
		
		btnPretraziOpremaKluba.disableProperty().bind(grupaKlubVrsta.selectedToggleProperty().isNull().or(grupaKlubTip.selectedToggleProperty().isNull()));
	}
	
	public void podesavanjeZaRdbtnClan() {
		txtTipClan.setDisable(true);
    	txtProizvodjacClan.setDisable(true);
    	txtModelClan.setDisable(true);
    	txtJmb.setDisable(true);
    	txtIme.setDisable(true);
    	txtPrezime.setDisable(true);
    	
    	rdbtnTipClan.setToggleGroup(grupaClan);
		rdbtnProizvodjacClan.setToggleGroup(grupaClan);
		rdbtnModelClan.setToggleGroup(grupaClan);
		rdbtnJmb.setToggleGroup(grupaClan);
		rdbtnIme.setToggleGroup(grupaClan);
		rdbtnPrezime.setToggleGroup(grupaClan);
    	
		rdbtnTipClan.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if(isNowSelected) {
		        	txtTipClan.setDisable(false);
		        	txtProizvodjacClan.setDisable(true);
		        	txtModelClan.setDisable(true);
		        	txtJmb.setDisable(true);
		        	txtIme.setDisable(true);
		        	txtPrezime.setDisable(true);
		        }
		    }
		});
		
		rdbtnProizvodjacClan.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if(isNowSelected) {
		        	txtTipClan.setDisable(true);
		        	txtProizvodjacClan.setDisable(false);
		        	txtModelClan.setDisable(true);
		        	txtJmb.setDisable(true);
		        	txtIme.setDisable(true);
		        	txtPrezime.setDisable(true);
		        }
		    }
		});
		
		rdbtnModelClan.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if(isNowSelected) {
		        	txtTipClan.setDisable(true);
		        	txtProizvodjacClan.setDisable(true);
		        	txtModelClan.setDisable(false);
		        	txtJmb.setDisable(true);
		        	txtIme.setDisable(true);
		        	txtPrezime.setDisable(true);
		        }
		    }
		});
		
		rdbtnJmb.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if(isNowSelected) {
		        	txtTipClan.setDisable(true);
		        	txtProizvodjacClan.setDisable(true);
		        	txtModelClan.setDisable(true);
		        	txtJmb.setDisable(false);
		        	txtIme.setDisable(true);
		        	txtPrezime.setDisable(true);
		        }
		    }
		});
		
		rdbtnIme.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if(isNowSelected) {
		        	txtTipClan.setDisable(true);
		        	txtProizvodjacClan.setDisable(true);
		        	txtModelClan.setDisable(true);
		        	txtJmb.setDisable(true);
		        	txtIme.setDisable(false);
		        	txtPrezime.setDisable(true);
		        }
		    }
		});
		
		rdbtnPrezime.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if(isNowSelected) {
		        	txtTipClan.setDisable(true);
		        	txtProizvodjacClan.setDisable(true);
		        	txtModelClan.setDisable(true);
		        	txtJmb.setDisable(true);
		        	txtIme.setDisable(true);
		        	txtPrezime.setDisable(false);
		        }
		    }
		});
		
		btnPretraziOpremaClanova.disableProperty().bind(grupaClan.selectedToggleProperty().isNull());
	}
	
	public void pretragaKlub() {
		RadioButton rdbtnVrsta = (RadioButton)grupaKlubVrsta.getSelectedToggle();
		RadioButton rdbtnTip = (RadioButton)grupaKlubTip.getSelectedToggle();
		String tipPretrage = "";
		String rijec = "";
		Boolean aktivan = null;
		
		if(rdbtnAktivna.equals(rdbtnVrsta)) {
			aktivan = true;
		}
		else if(rdbtnNeaktivna.equals(rdbtnVrsta)) {
			aktivan = false;
		}
		
		if(rdbtnTipKlub.equals(rdbtnTip)) {
			tipPretrage = "Tip";
			rijec = txtTipKlub.getText();
		}
		else if(rdbtnProizvodjacKlub.equals(rdbtnTip)) {
			tipPretrage = "Proizvodjac";
			rijec = txtProizvodjacKlub.getText();
		}
		else {
			tipPretrage = "Model";
			rijec = txtModelKlub.getText();
		}
		
		ObservableList<OpremaKlubaDTO> listaOpreme = OpremaKlubaDAO.SELECT_BY(aktivan, tipPretrage, rijec);
		tblOpremaKluba.setItems(listaOpreme);
	}
	
	public void pretragaClan() {
		RadioButton rdbtn = (RadioButton)grupaClan.getSelectedToggle();
		String tipPretrage = "";
		String rijec = "";
		
		if(rdbtnTipClan.equals(rdbtn)) {
			tipPretrage = "Tip";
			rijec = txtTipClan.getText();
		}
		else if(rdbtnProizvodjacClan.equals(rdbtn)) {
			tipPretrage = "Proizvodjac";
			rijec = txtProizvodjacClan.getText();
		}
		else if(rdbtnModelClan.equals(rdbtn)) {
			tipPretrage = "Model";
			rijec = txtModelClan.getText();
		}
		else if(rdbtnJmb.equals(rdbtn)) {
			tipPretrage = "JMB";
			rijec = txtJmb.getText();
		}
		else if(rdbtnIme.equals(rdbtn)) {
			tipPretrage = "Ime";
			rijec = txtIme.getText();
		}
		else {
			tipPretrage = "Prezime";
			rijec = txtPrezime.getText();
		}
		
		ObservableList<OpremaClanaDTO> listaOpreme = OpremaClanaDAO.SELECT_BY(tipPretrage, rijec);
		tblOpremaClana.setItems(listaOpreme);
	}
	
	public void popuniTabele() {
		idKlub.setCellValueFactory(new PropertyValueFactory<OpremaKlubaDTO, Integer>("id"));
		tipKlub.setCellValueFactory(new PropertyValueFactory<OpremaKlubaDTO, String>("tipOpreme"));
		proizvodjacKlub.setCellValueFactory(new PropertyValueFactory<OpremaKlubaDTO, String>("tipProizvodjac"));
		modelKlub.setCellValueFactory(new PropertyValueFactory<OpremaKlubaDTO, String>("tipModel"));
		doniranaKlub.setCellValueFactory(new PropertyValueFactory<OpremaKlubaDTO, Boolean>("donirana"));
		
		ObservableList<OpremaKlubaDTO> listaOpremeKluba = OpremaKlubaDAO.SELECT_ALL();
		
		tblOpremaKluba.setItems(listaOpremeKluba);
		
		idClan.setCellValueFactory(new PropertyValueFactory<OpremaClanaDTO, Integer>("id"));
		tipClan.setCellValueFactory(new PropertyValueFactory<OpremaClanaDTO, String>("tipOpreme"));
		proizvodjacClan.setCellValueFactory(new PropertyValueFactory<OpremaClanaDTO, String>("tipProizvodjac"));
		modelClan.setCellValueFactory(new PropertyValueFactory<OpremaClanaDTO, String>("tipModel"));
		jmbClan.setCellValueFactory(new PropertyValueFactory<OpremaClanaDTO, String>("jmbClana"));
		imeClan.setCellValueFactory(new PropertyValueFactory<OpremaClanaDTO, String>("imeClana"));
		prezimeClan.setCellValueFactory(new PropertyValueFactory<OpremaClanaDTO, String>("prezimeClana"));
		doniranaClan.setCellValueFactory(new PropertyValueFactory<OpremaClanaDTO, Boolean>("donirana"));
		
		ObservableList<OpremaClanaDTO> listaOpremeClana = OpremaClanaDAO.SELECT_ALL();
		
		tblOpremaClana.setItems(listaOpremeClana);
	}
	
	public void idiNaDodajOpremuKluba() {
		Stage noviStage = new Stage();
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/trener/view/DodajOpremuProzor.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root,761,576);
			DodajOpremuProzorController controller = loader.<DodajOpremuProzorController>getController();
			controller.setPrimaryStage(noviStage);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub - rad sa opremom");
			noviStage.initModality(Modality.APPLICATION_MODAL);
			
			controller.setOpremaKluba();
			controller.ucitajComboBoxeve();
			controller.disableDodajDugme();
			
			noviStage.showAndWait();
			popuniTabele();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void idiNaDodajOpremuClana() {
		Stage noviStage = new Stage();
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/gui/trener/view/DodajOpremuProzor.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root,761,576);
			DodajOpremuProzorController controller = loader.<DodajOpremuProzorController>getController();
			controller.setPrimaryStage(noviStage);
			noviStage.setScene(scene);
			noviStage.setResizable(false);
			noviStage.setTitle("Stonoteniski klub - rad sa opremom");
			noviStage.initModality(Modality.APPLICATION_MODAL);
			
			controller.ucitajComboBoxeve();
			controller.disableDodajDugme();
			
			noviStage.showAndWait();
			popuniTabele();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
