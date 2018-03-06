package shawn_cheng.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

public abstract class AbstractReportController extends AbstractMainController {

    @FXML
    protected ComboBox<?> dropDown;

    @FXML
    protected TableView<?> tableView;

    @FXML
    protected void HandlerApptTypeButton(ActionEvent event) {
        ScreenDisplays.displayReportApptTypeScreen();
    }

    @FXML
    protected void HandlerConsultantSchedule(ActionEvent event) {

    }

    @FXML
    protected void HandlerHistoryPerCustomer(ActionEvent event) {

    }

    @FXML
    protected abstract void HandlerRunReport(ActionEvent event);

}
