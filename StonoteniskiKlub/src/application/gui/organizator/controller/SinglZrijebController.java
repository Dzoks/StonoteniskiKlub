package application.gui.organizator.controller;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import application.gui.controller.BaseController;
import application.model.dao.MecDAO;
import application.model.dao.RundaDAO;
import application.model.dao.TimDAO;
import application.model.dao.ZrijebDAO;
import application.model.dto.MecDTO;
import application.model.dto.TimDTO;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class SinglZrijebController extends BaseController{
	@FXML
	private TableView<MecDTO> tblRunda1;
	@FXML
	private TableColumn<MecDTO,String> clnMec1;
	@FXML
	private TableColumn<MecDTO,String> clnRezultat1;
	@FXML
	private TableView<MecDTO> tblRunda2;
	@FXML
	private TableColumn<MecDTO,String> clnMec2;
	@FXML
	private TableColumn<MecDTO,String> clnRezultat2;
	@FXML
	private TableView<MecDTO> tblRunda5;
	@FXML
	private TableColumn<MecDTO,String> clnMec5;
	@FXML
	private TableColumn<MecDTO,String> clnRezultat5;
	@FXML
	private TableView<MecDTO> tblRunda4;
	@FXML
	private TableColumn<MecDTO,String> clnMec4;
	@FXML
	private TableColumn<MecDTO,String> clnRezultat4;
	@FXML
	private TableView<MecDTO> tblRunda3;
	@FXML
	private TableColumn<MecDTO,String> clnMec3;
	@FXML
	private TableColumn<MecDTO,String> clnRezultat3;
	@FXML
	private TextField txtPobjednik;
	@FXML
	private Button btnOk;
	@FXML
	private Button btnRunda1;
	@FXML
	private Button btnRunda2;
	@FXML
	private Button btnRunda3;
	@FXML
	private Button btnRunda4;
	@FXML
	private Button btnRunda5;

	private Integer idTurnira;
	private Integer idKategorije;
	private Integer idZrijeba;
	private Integer brojTimova;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public void inicijalizuj(Integer idTurnira,Integer idKategorije){
		this.idTurnira=idTurnira;
		this.idKategorije=idKategorije;
		this.idZrijeba=ZrijebDAO.getZrijeb(idTurnira, idKategorije).getId();
		primaryStage.setTitle("Žrijeb");
		txtPobjednik.setEditable(false);
		btnRunda1.setDisable(true);
		btnRunda2.setDisable(true);
		btnRunda3.setDisable(true);
		btnRunda4.setDisable(true);
		btnRunda5.setDisable(true);
		popuniZrijeb();
	}
	
	public void inicijalizujPrvi(Integer idTurnira,Integer idKategorije,Integer brojTimova){
		this.idTurnira=idTurnira;
		this.idKategorije=idKategorije;
		this.brojTimova=brojTimova;
		primaryStage.setTitle("Žrijeb");
		txtPobjednik.setEditable(false);
		btnRunda1.setDisable(true);
		btnRunda2.setDisable(true);
		btnRunda3.setDisable(true);
		btnRunda4.setDisable(true);
		btnRunda5.setDisable(true);
		ZrijebDAO.insert(idTurnira, idKategorije, brojTimova);
		this.idZrijeba=ZrijebDAO.getZrijeb(idTurnira, idKategorije).getId();
		ArrayList<TimDTO> lista=TimDAO.getSingleList(idTurnira, idKategorije);
		Random rand=new Random();
		for(int i=0;i<brojTimova;i++){
			TimDTO tim1=lista.remove(rand.nextInt(lista.size()));
			if(i<(32-brojTimova)){
				MecDAO.insertSingle(tim1.getId(), idZrijeba, 1, i+1);
			}
			else{
				TimDTO tim2=lista.remove(rand.nextInt(lista.size()));
				MecDAO.insert(tim1.getId(), tim2.getId(), idZrijeba, 1, i+1);
				if(lista.size()==0)
					break;
			}
		}
		popuniZrijeb();
	}
	
	public void inicijalizujZrijeb(){
		clnMec1.setCellValueFactory(new PropertyValueFactory<>("prikazMeca"));
		clnMec2.setCellValueFactory(new PropertyValueFactory<>("prikazMeca"));
		clnMec3.setCellValueFactory(new PropertyValueFactory<>("prikazMeca"));
		clnMec4.setCellValueFactory(new PropertyValueFactory<>("prikazMeca"));
		clnMec5.setCellValueFactory(new PropertyValueFactory<>("prikazMeca"));

		clnRezultat1.setCellValueFactory(new PropertyValueFactory<>("rezultat"));
		clnRezultat2.setCellValueFactory(new PropertyValueFactory<>("rezultat"));
		clnRezultat3.setCellValueFactory(new PropertyValueFactory<>("rezultat"));
		clnRezultat4.setCellValueFactory(new PropertyValueFactory<>("rezultat"));
		clnRezultat5.setCellValueFactory(new PropertyValueFactory<>("rezultat"));
	}
//	
//	public void ispisiZrijeb(){
//		inicijalizujZrijeb();
//		tblRunda1.setItems(MecDAO.getAllSingle(ZrijebDAO.getZrijeb(idTurnira, idKategorije).getId(), 1));
//		tblRunda2.setItems(MecDAO.getAllSingle(ZrijebDAO.getZrijeb(idTurnira, idKategorije).getId(), 2));
//		tblRunda3.setItems(MecDAO.getAllSingle(ZrijebDAO.getZrijeb(idTurnira, idKategorije).getId(), 3));
//		tblRunda4.setItems(MecDAO.getAllSingle(ZrijebDAO.getZrijeb(idTurnira, idKategorije).getId(), 4));
//		tblRunda5.setItems(MecDAO.getAllSingle(ZrijebDAO.getZrijeb(idTurnira, idKategorije).getId(), 5));
//	}

	public void popuniZrijeb(){
		inicijalizujZrijeb();

		tblRunda1.setItems(MecDAO.getAllSingle(idZrijeba, 1));
		if(RundaDAO.numCompleted(idZrijeba, 1)<16){
			tblRunda1.setEditable(true);
			clnRezultat1.setEditable(true);
			clnRezultat1.setCellFactory(TextFieldTableCell.forTableColumn());
			clnRezultat1.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MecDTO,String>>() {
				@Override
				public void handle(CellEditEvent<MecDTO, String> event) {
					event.getRowValue().setRezultat(event.getNewValue());
					MecDAO.insertRezultat(event.getRowValue());
					if(RundaDAO.numCompleted(idZrijeba, 1)==16)
						btnRunda1.setDisable(false);
				}
			});
		}
		else{
			tblRunda2.setItems(MecDAO.getAllSingle(idZrijeba, 2));
			if(RundaDAO.numCompleted(idZrijeba, 2)<8){
				tblRunda2.setEditable(true);
				clnRezultat2.setEditable(true);
				clnRezultat2.setCellFactory(TextFieldTableCell.forTableColumn());
				clnRezultat2.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MecDTO,String>>() {
					@Override
					public void handle(CellEditEvent<MecDTO, String> event) {
						event.getRowValue().setRezultat(event.getNewValue());
						MecDAO.insertRezultat(event.getRowValue());
						if(RundaDAO.numCompleted(idZrijeba, 2)==8)
							btnRunda2.setDisable(false);
					}
				});
			}
			else{
				tblRunda3.setItems(MecDAO.getAllSingle(idZrijeba, 3));
				if(RundaDAO.numCompleted(idZrijeba, 3)<4){
					tblRunda3.setEditable(true);
					clnRezultat3.setEditable(true);
					clnRezultat3.setCellFactory(TextFieldTableCell.forTableColumn());
					clnRezultat3.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MecDTO,String>>() {
						@Override
						public void handle(CellEditEvent<MecDTO, String> event) {
							event.getRowValue().setRezultat(event.getNewValue());
							MecDAO.insertRezultat(event.getRowValue());
							if(RundaDAO.numCompleted(idZrijeba, 3)==4)
								btnRunda3.setDisable(false);
						}
					});
				}
				else{
					tblRunda4.setItems(MecDAO.getAllSingle(idZrijeba, 4));
					if(RundaDAO.numCompleted(idZrijeba, 4)<2){
						tblRunda4.setEditable(true);
						clnRezultat4.setEditable(true);
						clnRezultat4.setCellFactory(TextFieldTableCell.forTableColumn());
						clnRezultat4.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MecDTO,String>>() {
							@Override
							public void handle(CellEditEvent<MecDTO, String> event) {
								event.getRowValue().setRezultat(event.getNewValue());
								MecDAO.insertRezultat(event.getRowValue());
								if(RundaDAO.numCompleted(idZrijeba, 4)==2)
									btnRunda4.setDisable(false);
							}
						});
					}
					else{
						tblRunda5.setItems(MecDAO.getAllSingle(idZrijeba, 5));
						if(RundaDAO.numCompleted(idZrijeba, 4)<2){
							tblRunda5.setEditable(true);
							clnRezultat5.setEditable(true);
							clnRezultat5.setCellFactory(TextFieldTableCell.forTableColumn());
							clnRezultat5.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MecDTO,String>>() {
								@Override
								public void handle(CellEditEvent<MecDTO, String> event) {
									event.getRowValue().setRezultat(event.getNewValue());
									MecDAO.insertRezultat(event.getRowValue());
									btnRunda5.setDisable(false);
								}
							});
						}
						else{
							ArrayList<MecDTO> lista=MecDAO.getAllList(idZrijeba, 5);
							Integer idTim;
							MecDTO mec=lista.get(0);
							if(Integer.valueOf(mec.getRezultat().charAt(0))>Integer.valueOf(mec.getRezultat().charAt(2)))
								idTim=mec.getIdPrvogTima();
							else
								idTim=mec.getIdDrugogTima();
							txtPobjednik.setText(TimDAO.getSingleById(idTim));
						}
					}
				}
			}
		}
	}
	
	public void refresujRundu1(){
		tblRunda1.setEditable(false);
		clnRezultat1.setEditable(false);
		btnRunda1.setDisable(true);
		ArrayList<MecDTO> lista=MecDAO.getAllList(idZrijeba, 1);
		for(int i=0;i<16;i+=2){
			Integer idTim1,idTim2;
			MecDTO mec1=lista.get(i);
			if(Integer.valueOf(mec1.getRezultat().charAt(0))>Integer.valueOf(mec1.getRezultat().charAt(2)))
				idTim1=mec1.getIdPrvogTima();
			else
				idTim1=mec1.getIdDrugogTima();
			MecDTO mec2=lista.get(i+1);
			if(Integer.valueOf(mec2.getRezultat().charAt(0))>Integer.valueOf(mec2.getRezultat().charAt(2)))
				idTim2=mec2.getIdPrvogTima();
			else
				idTim2=mec2.getIdDrugogTima();
			MecDAO.insert(idTim1, idTim2, idZrijeba, 2, i/2+1);
		}
		tblRunda2.setItems(MecDAO.getAllSingle(idZrijeba, 2));
		tblRunda2.setEditable(true);
		clnRezultat2.setEditable(true);
		clnRezultat2.setCellFactory(TextFieldTableCell.forTableColumn());
		clnRezultat2.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MecDTO,String>>() {
			@Override
			public void handle(CellEditEvent<MecDTO, String> event) {
				event.getRowValue().setRezultat(event.getNewValue());
				MecDAO.insertRezultat(event.getRowValue());
				if(RundaDAO.numCompleted(idZrijeba, 2)==8)
					btnRunda2.setDisable(false);
			}
		});
	}
	
	public void refresujRundu2(){
		tblRunda2.setEditable(false);
		clnRezultat2.setEditable(false);
		btnRunda2.setDisable(true);
		ArrayList<MecDTO> lista=MecDAO.getAllList(idZrijeba, 2);
		for(int i=0;i<8;i+=2){
			Integer idTim1,idTim2;
			MecDTO mec1=lista.get(i);
			if(Integer.valueOf(mec1.getRezultat().charAt(0))>Integer.valueOf(mec1.getRezultat().charAt(2)))
				idTim1=mec1.getIdPrvogTima();
			else
				idTim1=mec1.getIdDrugogTima();
			MecDTO mec2=lista.get(i+1);
			if(Integer.valueOf(mec2.getRezultat().charAt(0))>Integer.valueOf(mec2.getRezultat().charAt(2)))
				idTim2=mec2.getIdPrvogTima();
			else
				idTim2=mec2.getIdDrugogTima();
			
			MecDAO.insert(idTim1, idTim2, idZrijeba, 3, i/2+1);
		}
		tblRunda3.setItems(MecDAO.getAllSingle(idZrijeba, 3));
		tblRunda3.setEditable(true);
		clnRezultat3.setEditable(true);
		clnRezultat3.setCellFactory(TextFieldTableCell.forTableColumn());
		clnRezultat3.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MecDTO,String>>() {
			@Override
			public void handle(CellEditEvent<MecDTO, String> event) {
				event.getRowValue().setRezultat(event.getNewValue());
				MecDAO.insertRezultat(event.getRowValue());
				if(RundaDAO.numCompleted(idZrijeba, 3)==4)
					btnRunda3.setDisable(false);
			}
		});
	}

	public void refresujRundu3(){
		tblRunda3.setEditable(false);
		clnRezultat3.setEditable(false);
		btnRunda3.setDisable(true);
		ArrayList<MecDTO> lista=MecDAO.getAllList(idZrijeba, 3);
		for(int i=0;i<4;i+=2){
			Integer idTim1,idTim2;
			MecDTO mec1=lista.get(i);
			if(Integer.valueOf(mec1.getRezultat().charAt(0))>Integer.valueOf(mec1.getRezultat().charAt(2)))
				idTim1=mec1.getIdPrvogTima();
			else
				idTim1=mec1.getIdDrugogTima();
			MecDTO mec2=lista.get(i+1);
			if(Integer.valueOf(mec2.getRezultat().charAt(0))>Integer.valueOf(mec2.getRezultat().charAt(2)))
				idTim2=mec2.getIdPrvogTima();
			else
				idTim2=mec2.getIdDrugogTima();
			
			MecDAO.insert(idTim1, idTim2, idZrijeba, 4, i/2+1);
		}		
		tblRunda4.setItems(MecDAO.getAllSingle(idZrijeba, 4));
		tblRunda4.setEditable(true);
		clnRezultat4.setEditable(true);
		clnRezultat4.setCellFactory(TextFieldTableCell.forTableColumn());
		clnRezultat4.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MecDTO,String>>() {
			@Override
			public void handle(CellEditEvent<MecDTO, String> event) {
				event.getRowValue().setRezultat(event.getNewValue());
				MecDAO.insertRezultat(event.getRowValue());
				if(RundaDAO.numCompleted(idZrijeba, 4)==2)
					btnRunda4.setDisable(false);
			}
		});
	}

	public void refresujRundu4(){
		tblRunda4.setEditable(false);
		clnRezultat4.setEditable(false);
		btnRunda4.setDisable(true);
		ArrayList<MecDTO> lista=MecDAO.getAllList(idZrijeba, 4);
		for(int i=0;i<2;i+=2){
			Integer idTim1,idTim2;
			MecDTO mec1=lista.get(i);
			if(Integer.valueOf(mec1.getRezultat().charAt(0))>Integer.valueOf(mec1.getRezultat().charAt(2)))
				idTim1=mec1.getIdPrvogTima();
			else
				idTim1=mec1.getIdDrugogTima();
			MecDTO mec2=lista.get(i+1);
			if(Integer.valueOf(mec2.getRezultat().charAt(0))>Integer.valueOf(mec2.getRezultat().charAt(2)))
				idTim2=mec2.getIdPrvogTima();
			else
				idTim2=mec2.getIdDrugogTima();
			
			MecDAO.insert(idTim1, idTim2, idZrijeba, 5, i/2+1);
		}		
		tblRunda5.setItems(MecDAO.getAllSingle(idZrijeba, 5));
		tblRunda5.setEditable(true);
		clnRezultat5.setEditable(true);
		clnRezultat5.setCellFactory(TextFieldTableCell.forTableColumn());
		clnRezultat5.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MecDTO,String>>() {
			@Override
			public void handle(CellEditEvent<MecDTO, String> event) {
				event.getRowValue().setRezultat(event.getNewValue());
				MecDAO.insertRezultat(event.getRowValue());
				btnRunda5.setDisable(false);
			}
		});
	}
	
	public void refresujRundu5(){
		tblRunda5.setEditable(false);
		clnRezultat5.setEditable(false);
		btnRunda5.setDisable(true);
		ArrayList<MecDTO> lista=MecDAO.getAllList(idZrijeba, 5);
		Integer idTim;
		MecDTO mec=lista.get(0);
		if(Integer.valueOf(mec.getRezultat().charAt(0))>Integer.valueOf(mec.getRezultat().charAt(2)))
			idTim=mec.getIdPrvogTima();
		else
			idTim=mec.getIdDrugogTima();	
		txtPobjednik.setText(TimDAO.getSingleById(idTim));
	}
	
	public void ok(){
		primaryStage.close();
	}
}
