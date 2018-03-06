package shawn_cheng.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AbstractMainController {
    @FXML
    void appointmentsButtonHandler(ActionEvent event) {
        ScreenDisplays.displayMonthlyCalendarScreen();
    }

    @FXML
    void customersButtonHandler(ActionEvent event) {
        ScreenDisplays.displayCustomerScreen();
    }

    @FXML
    void exitButtonHandler(ActionEvent event) {
        ScreenDisplays.displayExitConfirmation();
    }

    @FXML
    void reportsButtonHandler(ActionEvent event) {
        ScreenDisplays.displayReportApptTypeScreen();
    }
}
