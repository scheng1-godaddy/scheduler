package shawn_cheng.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

public class ReportsApptTypeController extends AbstractReportController {

    @FXML
    private TableColumn<?, ?> columnCount;

    @FXML
    private TableColumn<?, ?> columnReportType;

    @Override @FXML
    public void HandlerRunReport(ActionEvent event) {
        // Generate report
    }

}
