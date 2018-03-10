package shawn_cheng.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Abstract Controller for the main navigation items.
 * All controllers that utilize the main navigation need to extend this.
 * @author Shawn Cheng
 */
public class AbstractMainController {
    /**
     * Appointment button handler
     * @param event
     */
    @FXML
    void appointmentsButtonHandler(ActionEvent event) {
        ScreenDisplays.displayMonthlyCalendarScreen();
    }

    /**
     * Customers button handler
     * @param event
     */
    @FXML
    void customersButtonHandler(ActionEvent event) {
        ScreenDisplays.displayCustomerScreen();
    }

    /**
     * Exit button handler
     * @param event
     */
    @FXML
    void exitButtonHandler(ActionEvent event) {
        ScreenDisplays.displayExitConfirmation();
    }

    /**
     * Reports button handler
     * @param event
     */
    @FXML
    void reportsButtonHandler(ActionEvent event) {
        ScreenDisplays.displayReportApptTypeScreen();
    }
}
