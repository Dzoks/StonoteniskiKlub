package application.gui.sekretar.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import com.itextpdf.text.TabStop.Alignment;
import com.itextpdf.text.log.SysoCounter;

import application.gui.controller.BaseController;
import application.model.dao.DAOFactory;
import application.model.dto.DogadjajDTO;
import application.util.TextUtility;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class CalendarController extends BaseController {
	@FXML
	private GridPane calendar;
	@FXML
	private Button btnPrije;
	@FXML
	private Button btnPoslije;
	@FXML
	private Label lblNaslov;
	@FXML
	private TextFlow taDnevniPregled;
	// Event Listener on GridPane[#calendar].onMouseClicked
	@FXML
	public void getDay(MouseEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				labele[i][j] = new Label("");
				labele[i][j].setPrefSize(80, 80);
				labele[i][j].setStyle("-fx-alignment: center; -fx-font-size: 25; -fx-border-color: black; ");
				calendar.add(labele[i][j], j, i + 1);
			}
		}
		currentYearMonth = YearMonth.now();
		lblNaslov.setText(MJESECI[currentYearMonth.getMonthValue() - 1] + " " + currentYearMonth.getYear());
		populateCalendar(currentYearMonth);
	}

	@FXML
	public void sledeci(ActionEvent event) {
		currentYearMonth = currentYearMonth.plusMonths(1);
		lblNaslov.setText(MJESECI[currentYearMonth.getMonthValue() - 1] + " " + currentYearMonth.getYear());
		lblNaslov.getScene().setCursor(Cursor.WAIT);
		populateCalendar(currentYearMonth);
		lblNaslov.getScene().setCursor(Cursor.DEFAULT);
	}

	@FXML
	public void prethodni(ActionEvent event) {
		currentYearMonth = currentYearMonth.minusMonths(1);
		lblNaslov.setText(MJESECI[currentYearMonth.getMonthValue() - 1] + " " + currentYearMonth.getYear());
		lblNaslov.getScene().setCursor(Cursor.WAIT);
		populateCalendar(currentYearMonth);
		lblNaslov.getScene().setCursor(Cursor.DEFAULT);
	}

	private void populateCalendar(YearMonth yearMonth) {
		reset();
		LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
		calendarDate = calendarDate.minusDays(calendarDate.getDayOfWeek().getValue() - 1);
		dogadjaji = DAOFactory.getDAOFactory().getDogadjajDAO().selectAll(yearMonth);
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				int day = calendarDate.getDayOfMonth();
				Label label = labele[i][j];
				label.setText(day + "");
				if (YearMonth.of(calendarDate.getYear(), calendarDate.getMonth()).equals(yearMonth)) {
					LocalDate date = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(),
							Integer.parseInt(label.getText()));
					boolean contains = false;
					String fontColor = "";
					for (DogadjajDTO dogadjaj : dogadjaji) {
						if (date.equals(LocalDate.of(dogadjaj.getPocetak().getYear(), dogadjaj.getPocetak().getMonth(),
								dogadjaj.getPocetak().getDayOfMonth()))) {
							contains = true;
							break;
						}
					}
					if (contains) {
						fontColor = " -fx-text-fill: red;";
					} else {
						fontColor = " -fx-text-fill: green;";
					}
					label.setStyle(label.getStyle() + fontColor);
					label.setOnMouseClicked(new EventHandler<Event>() {
						@Override
						public void handle(Event event) {
							LocalDate date = LocalDate.of(currentYearMonth.getYear(), currentYearMonth.getMonth(),
									Integer.parseInt(label.getText()));
							List<DogadjajDTO> dnevniDogadjaji = new ArrayList<DogadjajDTO>();
							for (DogadjajDTO dogadjaj : dogadjaji) {
								if (date.equals(LocalDate.of(dogadjaj.getPocetak().getYear(),
										dogadjaj.getPocetak().getMonth(), dogadjaj.getPocetak().getDayOfMonth()))) {
									dnevniDogadjaji.add(dogadjaj);
								}
							}
							TextUtility.setTextF(taDnevniPregled, dnevniDogadjaji);
						}
					});
					label.setOnMouseEntered(new EventHandler<Event>() {
						@Override
						public void handle(Event event) {
							label.setStyle(label.getStyle() + " -fx-background-color: lightgray");
						}
					});
					label.setOnMouseExited(new EventHandler<Event>() {

						@Override
						public void handle(Event event) {
							String[] styles = label.getStyle().split("[;]");
							String s = "";
							for (int i = 0; i < styles.length - 1; i++) {
								s += styles[i] + ";";
							}
							label.setStyle(s);
						}
					});
					labele[i][j].setCursor(Cursor.HAND);
				} else {
					label.setStyle(label.getStyle() + " -fx-text-fill: #e1e1d0;");
				}
				calendarDate = calendarDate.plusDays(1);
			}

		}
	}

	private void reset() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				labele[i][j].setOnMouseClicked(null);
				labele[i][j].setOnMouseEntered(null);
				labele[i][j].setOnMouseExited(null);
				labele[i][j].setDisable(false);
				labele[i][j].setText("");
				labele[i][j].setCursor(Cursor.DEFAULT);
				labele[i][j].setStyle(BASE_STYLE);
			}
		}
	}

	private static final String BASE_STYLE = "-fx-alignment: center; -fx-font-size: 30; -fx-border-color: black; -fx-font-weight:bold;";
	private static final String[] MJESECI = { "JANUAR", "FEBRUAR", "MART", "APRIL", "MAJ", "JUN", "JUL", "AVGUST",
			"SEPTEMBAR", "OKTOBAR", "NOVEMBAR", "DECEMBAR" };
	private YearMonth currentYearMonth;
	private List<DogadjajDTO> dogadjaji;
	private Label[][] labele = new Label[6][7];
}
