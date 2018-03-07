package shawn_cheng.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public abstract class AbstractReportController extends AbstractMainController implements Initializable {

    protected final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);

    @FXML
    protected void HandlerApptTypeButton(ActionEvent event) {
        ScreenDisplays.displayReportApptTypeScreen();
    }

    @FXML
    protected void HandlerConsultantSchedule(ActionEvent event) {
        ScreenDisplays.displayReportConsultantScheduleScreen();
    }

    @FXML
    protected void HandlerHistoryPerCustomer(ActionEvent event) {
        ScreenDisplays.displayReportHistoryPerCustomerScreen();
    }

    @FXML
    protected abstract void HandlerRunReport(ActionEvent event);

}
