package shawn_cheng.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportsHistoryCustomerController extends AbstractReportController {
    @FXML
    private TableColumn<?, ?> columnDate;

    @FXML
    private TableColumn<?, ?> columnTitle;

    @FXML
    private TableColumn<?, ?> columnType;

    @FXML
    private TableColumn<?, ?> columnConsultant;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("URL value is: " + url.toString());
    }

    @Override @FXML
    public void HandlerRunReport(ActionEvent event) {

    }

}
