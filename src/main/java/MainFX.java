import ctrl.MainFXMLcontroller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


/** Pana Joi:
 *   -repo cu baze de date
 *   -server <> client</>*/
public class MainFX extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainFXML.fxml"));

            Parent root = loader.load();

            //MainFXMLcontroller ctrl = loader.getController();
            //ctrl.setService(getService());
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Initial Scene");
            primaryStage.show();


        }catch(Exception e){

            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            System.out.println(e);

            alert.setContentText(""+e);
            alert.showAndWait();


        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
