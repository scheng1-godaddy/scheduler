package shawn_cheng.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * Abstract controller for navigation on report screens
 * @author Shawn Cheng
 */
public abstract class AbstractReportController extends AbstractMainController implements Initializable {

    /**
     * Handler for Appointment count by type button
     * @param event
     */
    @FXML
    protected void HandlerApptTypeButton(ActionEvent event) {
        ScreenDisplays.displayReportApptTypeScreen();
    }

    /**
     * Handler for consultant schedule button
     * @param event
     */
    @FXML
    protected void HandlerConsultantSchedule(ActionEvent event) {
        ScreenDisplays.displayReportConsultantScheduleScreen();
    }

    /**
     * Handler for History for customer button
     * @param event
     */
    @FXML
    protected void HandlerHistoryPerCustomer(ActionEvent event) {
        ScreenDisplays.displayReportHistoryPerCustomerScreen();
    }

    /**
     * Handler for Run Report button
     * @param event
     */
    @FXML
    protected abstract void HandlerRunReport(ActionEvent event);

}
