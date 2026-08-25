package files;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class WxController implements Initializable {

    @FXML
    private Button btnGetWx;

    @FXML
    private TextField txtZipcode;

    @FXML
    private ImageView IconWx;

    @FXML
    private Label lblCityState;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblWeather;

    @FXML
    private Label lblTemperature;

    @FXML
    private Label lblWindSpeed;

    @FXML
    private Label lblWind;

    @FXML
    private Label lblPressure;

    @FXML
    private Label lblHumidity;

    @FXML
    private Label aqi;

    @FXML
    private Label co;

    @FXML
    private Label no;

    @FXML
    private Label no2;

    @FXML
    private Label o3;

    @FXML
    private Label so2;

    @FXML
    private Label pm25;

    @FXML
    private Label pm10;

    @FXML
    private Label nh3;

    
    @FXML
    private void handleButtonAction(ActionEvent e) {
        // Create object to access the Model
        WxModel weather = new WxModel();
        String lat = "0";
        String longitude = "0";

        // Get zipcode
        String zipcode = txtZipcode.getText();

        // Use the model to get the weather information
        if (weather.getWx(zipcode)) {
            lblCityState.setText(weather.getLocation());
            lblTime.setText(weather.getTime());
            lblWeather.setText(weather.getWeather());
            lblTemperature.setText(String.valueOf(weather.getTemperature()));
            lblWindSpeed.setText(weather.getWindSpeed());
            lblWind.setText(weather.getWindDirection());
            lblPressure.setText(weather.getPressure());
            lblHumidity.setText(weather.getHumidity());
            IconWx.setImage(weather.getImage());
            lat = weather.getLatitude();
            longitude = weather.getLongitude();
//            lblLatitude.setText(weather.getLatitude());
//            lblLongitude.setText(weather.getLongitude());
        } else {
            lblCityState.setText("Invalid CityName");
            lblTime.setText("");
            lblWeather.setText("");
            lblTemperature.setText("");
            lblWindSpeed.setText("");
            lblWind.setText("");
            lblPressure.setText("");
            lblHumidity.setText("");
            IconWx.setImage(new Image("/badzipcode.png"));
        }

//    // method 2  // Create it in Wxmodel
        if (weather.getAirPollution(lat, longitude)) {
             aqi.setText(weather.getAqi());
             co.setText(weather.getCo());
             no.setText(weather.getNo());
             no2.setText(weather.getNo2());
             o3.setText(weather.getO3());
             so2.setText(weather.getSo2());
             pm25.setText(weather.getPm2());
             pm10.setText(weather.getPm10());
             nh3.setText(weather.getNh3());
        } else {
            aqi.setText("");
            co.setText("");
            no.setText("");
            no2.setText("");
            o3.setText("");
            so2.setText("");
            pm25.setText("");
            pm10.setText("");
            nh3.setText("");
        }

            }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
