package ro.tucn.assignment4.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ro.tucn.assignment4.Business.BaseProduct;
import ro.tucn.assignment4.Business.DeliveryService;
import ro.tucn.assignment4.Business.MenuItem;
import ro.tucn.assignment4.HelloApplication;

import java.io.IOException;
import java.util.regex.Pattern;

public class ModifyProductController {
    private int chooseButton = 0;
    private int mode;
    private BaseProduct product;
    @FXML
    private TextField titleField;

    @FXML
    private TextField ratingField;

    @FXML
    private TextField caloriesField;

    @FXML
    private TextField proteinField;

    @FXML
    private TextField fatField;

    @FXML
    private TextField sodiumField;

    @FXML
    private TextField priceField;

    @FXML
    private Button backButton;

    @FXML
    private Button enterButton;

    public void backPressed() throws IOException {
        Button currentButton;
        if(chooseButton == 0) currentButton = backButton;
        else{
            currentButton = enterButton;
            DeliveryService deliveryService = DeliveryService.getObject();
            MenuItem newProduct = new BaseProduct(
                    titleField.getText(),
                    Float.parseFloat(ratingField.getText()),
                    Integer.parseInt(caloriesField.getText()),
                    Integer.parseInt(proteinField.getText()),
                    Integer.parseInt(fatField.getText()),
                    Integer.parseInt(sodiumField.getText()),
                    Integer.parseInt(priceField.getText())
            );
            if(mode == 0){
                // add was pressed before
                deliveryService.getMenuItems().add(0, newProduct);
            }else{
                // edit was pressed before
                deliveryService.updateBaseProducts(this.product, (BaseProduct) newProduct);
            }
        }
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("admin-view.fxml"));
        Stage stage = (Stage) currentButton.getScene().getWindow();
        Scene scene = new Scene(loader.load(), 1154, 750);
        stage.setScene(scene);
        stage.setTitle("Admin");
        stage.show();
    }

    public void enterPressed() throws IOException {
        int errorCode = checkInput();
        if(errorCode == 0){
            chooseButton = 1;
            backPressed();
        }else if (errorCode == 1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Some data is missing");
            alert.showAndWait();
        }else if(errorCode == 2){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Some data is in wrong format, numbers should be entered");
            alert.showAndWait();
        }

    }

    public int checkInput(){
        if(titleField.getText() == "" || ratingField.getText() == "" || caloriesField.getText() == "" || proteinField.getText() == "" || fatField.getText() == "" || sodiumField.getText() == "" || priceField.getText() == "")
            return 1;
        String isNumber = "^[0-9]*$";
        Pattern pattern = Pattern.compile(isNumber);
        if (!pattern.matcher(caloriesField.getText()).matches() ||
            !pattern.matcher(proteinField.getText()).matches() ||
            !pattern.matcher(fatField.getText()).matches() ||
            !pattern.matcher(sodiumField.getText()).matches() ||
            !pattern.matcher(priceField.getText()).matches())
            return 2;
        return 0;
    }

    public void setData(BaseProduct baseProduct){
        titleField.setText(baseProduct.getTitle());
        ratingField.setText(String.valueOf(baseProduct.getRating()));
        caloriesField.setText(String.valueOf(baseProduct.getCalories()));
        proteinField.setText(String.valueOf(baseProduct.getProtein()));
        fatField.setText(String.valueOf(baseProduct.getFat()));
        sodiumField.setText(String.valueOf(baseProduct.getSodium()));
        priceField.setText(String.valueOf(baseProduct.getPrice()));
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setProduct(BaseProduct product) {
        this.product = product;
    }
}
