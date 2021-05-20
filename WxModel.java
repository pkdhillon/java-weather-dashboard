package files;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;


public class WxModel {
  private JsonElement jse;
  String Latitude;
  String Longitude;

  private final String apiKey = "a7199e21caf1144c0ff3ebdaec774bf3";

  public boolean getWx(String cityname)
  {
    try
    {

      URL wxURL = new URL("http://api.openweathermap.org/data/2.5/weather?q="
              + cityname
              + "&appid=" + apiKey);





      // Open connection
      InputStream is = wxURL.openStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(is));

      // Read the results into a JSON Element
      jse = new JsonParser().parse(br);

      System.out.println(jse.toString());

      // Close connection
      is.close();
      br.close();
    }
    catch (java.io.UnsupportedEncodingException uee)
    {
      uee.printStackTrace();
      return false;
    }
    catch (java.net.MalformedURLException mue)
    {
      mue.printStackTrace();
      return false;
    }
    catch (java.io.IOException ioe)
    {
      return false;
    }
    catch (NullPointerException npe)
    {
      npe.printStackTrace();
      return false;
    }

    // Check to see if the zip code was valid.
    return isValid();
  }

  public boolean isValid()
  {
    //  If the zip is not valid we will get an error field in the JSON
    try {
      String error = jse.getAsJsonObject().get("cod").getAsString();
      System.out.println(error);
      int check = Integer.parseInt(error);
      if (check == 200)
      {
        return true;
      }
      else
      {
        return false;
      }

    }

    catch (NullPointerException npe)
    {
      // We did not see error so this is a valid zip
      return false;
    }
  }

  public String getLocation()
  {
    System.out.println(jse.getAsJsonObject().get("name").getAsString());
    return jse.getAsJsonObject().get("name").getAsString();
  }

  public String getTime()
  {
    String dt = jse.getAsJsonObject().get("dt").getAsString();
    int dt1=Integer.parseInt(dt);
    java.util.Date time1=new java.util.Date((long)dt1*1000);
    String transformedDate = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(time1);
    System.out.println(transformedDate);
    return transformedDate;
  }

  public String getWeather()
  {
    JsonArray obs = jse.getAsJsonObject().get("weather").getAsJsonArray();
    String conditions = obs.get(0)
            .getAsJsonObject().get("description").getAsString();
    return conditions;
  }

  public double getTemperature()
  {
    String temp = jse.getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString();
    double kelvin = Double.parseDouble(temp);
    double faren = ((1.8) * (kelvin - 273) + 32);
    String b = String.format("%.1f", faren);
    return Double.parseDouble(b);
  }

  public String getWindSpeed() {
    String wind2 = jse.getAsJsonObject().get("wind").getAsJsonObject().get("speed").getAsString();
    double d = Double.parseDouble(wind2);
    String c = String.format("%.1f", d);
    return c;
  }

  public String getWindDirection()
  {
    String windDirections = jse.getAsJsonObject().get("wind").getAsJsonObject().get("deg").getAsString();
    double wind = Double.parseDouble(windDirections);
    String directions[] = {"N", "NE", "E", "SE", "S", "SW", "W", "NW", "N"};
    return directions[ (int)Math.round((  ((double)wind % 360) / 45)) ];

  }

  public String getPressure()
  {
    String pressure = jse.getAsJsonObject().get("main").getAsJsonObject().get("pressure").getAsString();
    int pressure1 = Integer.parseInt(pressure);
    double convert = pressure1 * 0.02953;
    String a = String.format("%.2f", convert);
    return (a);
  }

  public String getHumidity()
  {
    return jse.getAsJsonObject().get("main").getAsJsonObject().get("humidity").getAsString();
  }

  public String getLatitude()
  {
    System.out.println(jse.getAsJsonObject().get("coord").getAsJsonObject().get("lon").getAsString());
    Latitude = jse.getAsJsonObject().get("coord").getAsJsonObject().get("lon").getAsString();
    return jse.getAsJsonObject().get("coord").getAsJsonObject().get("lon").getAsString();
  }

  public String getLongitude()
  {
    System.out.println(jse.getAsJsonObject().get("coord").getAsJsonObject().get("lat").getAsString());
    Longitude = jse.getAsJsonObject().get("coord").getAsJsonObject().get("lat").getAsString();
  //  getAirPollution();
    return jse.getAsJsonObject().get("coord").getAsJsonObject().get("lat").getAsString();
  }

  public Image getImage() {
    JsonArray icon = jse.getAsJsonObject().get("weather").getAsJsonArray();
    String icon2 = icon.get(0)
            .getAsJsonObject().get("icon").getAsString();
    String icon3 = "http://openweathermap.org/img/wn/" + icon2 + ".png";
    System.out.println(icon3);
    return new Image(icon3);
  }


public boolean getAirPollution(String lat,String longitude) {


    try
    {

      URL wxURL = new URL("http://api.openweathermap.org/data/2.5/air_pollution?lat="
              + lat
              + "&lon="
              + longitude
              + "&appid=" + apiKey);


      // Open connection
      InputStream is = wxURL.openStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(is));

      // Read the results into a JSON Element
      jse = new JsonParser().parse(br);

      System.out.println(jse.toString());

      // Close connection
      is.close();
      br.close();
    }
    catch (java.io.UnsupportedEncodingException uee)
    {
      uee.printStackTrace();
      return false;
    }
    catch (java.net.MalformedURLException mue)
    {
      mue.printStackTrace();
      return false;
    }
    catch (java.io.IOException ioe)
    {
      return false;
    }
    catch (NullPointerException npe)
    {
      npe.printStackTrace();
      return false;
    }

    // Check to see if the zip code was valid.
    return true;
  }

  public String getCo()
  {
      System.out.println(jse.getAsJsonObject().get("list").getAsJsonArray().get(0).getAsJsonObject().get("components").getAsJsonObject().get("co").getAsString());
      return jse.getAsJsonObject().get("list").getAsJsonArray().get(0).getAsJsonObject().get("components").getAsJsonObject().get("co").getAsString();
  }

  public String getNo()
  {
    System.out.println(jse.getAsJsonObject().get("list").getAsJsonArray().get(0).getAsJsonObject().get("components").getAsJsonObject().get("no").getAsString());
    return jse.getAsJsonObject().get("list").getAsJsonArray().get(0).getAsJsonObject().get("components").getAsJsonObject().get("no").getAsString();
  }

  public String getNo2()
  {
    System.out.println(jse.getAsJsonObject().get("list").getAsJsonArray().get(0).getAsJsonObject().get("components").getAsJsonObject().get("no2").getAsString());
    return jse.getAsJsonObject().get("list").getAsJsonArray().get(0).getAsJsonObject().get("components").getAsJsonObject().get("no2").getAsString();
  }

  public String getO3()
  {
    System.out.println(jse.getAsJsonObject().get("list").getAsJsonArray().get(0).getAsJsonObject().get("components").getAsJsonObject().get("o3").getAsString());
    return jse.getAsJsonObject().get("list").getAsJsonArray().get(0).getAsJsonObject().get("components").getAsJsonObject().get("o3").getAsString();
  }

  public String getSo2()
  {
    System.out.println(jse.getAsJsonObject().get("list").getAsJsonArray().get(0).getAsJsonObject().get("components").getAsJsonObject().get("so2").getAsString());
    return jse.getAsJsonObject().get("list").getAsJsonArray().get(0).getAsJsonObject().get("components").getAsJsonObject().get("so2").getAsString();
  }

  public String getPm2()
  {
    System.out.println(jse.getAsJsonObject().get("list").getAsJsonArray().get(0).getAsJsonObject().get("components").getAsJsonObject().get("pm2_5").getAsString());
    return jse.getAsJsonObject().get("list").getAsJsonArray().get(0).getAsJsonObject().get("components").getAsJsonObject().get("pm2_5").getAsString();
  }

  public String getPm10()
  {
    System.out.println(jse.getAsJsonObject().get("list").getAsJsonArray().get(0).getAsJsonObject().get("components").getAsJsonObject().get("pm10").getAsString());
    return jse.getAsJsonObject().get("list").getAsJsonArray().get(0).getAsJsonObject().get("components").getAsJsonObject().get("pm10").getAsString();
  }

  public String getNh3()
  {
    System.out.println(jse.getAsJsonObject().get("list").getAsJsonArray().get(0).getAsJsonObject().get("components").getAsJsonObject().get("nh3").getAsString());
    return jse.getAsJsonObject().get("list").getAsJsonArray().get(0).getAsJsonObject().get("components").getAsJsonObject().get("nh3").getAsString();
  }

  public String getAqi() {
    System.out.println(jse.getAsJsonObject().get("list").getAsJsonArray().get(0).getAsJsonObject().get("main").getAsJsonObject().get("aqi").getAsString());
    return jse.getAsJsonObject().get("list").getAsJsonArray().get(0).getAsJsonObject().get("main").getAsJsonObject().get("aqi").getAsString();
  }


    // 7 day forecast

  public boolean getForecast(String lat,String longitude) {

    try
    {

      URL wxURL = new URL("https://api.openweathermap.org/data/2.5/onecall?lat="
              + lat
              + "&lon="
              + longitude
              + "&units=imperial"
              + "&exclude=hourly&appid=" + apiKey);


      // Open connection
      InputStream is = wxURL.openStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(is));

      // Read the results into a JSON Element
      jse = new JsonParser().parse(br);

      System.out.println(jse.toString());

      // Close connection
      is.close();
      br.close();
    }
    catch (java.io.UnsupportedEncodingException uee)
    {
      uee.printStackTrace();
      return false;
    }
    catch (java.net.MalformedURLException mue)
    {
      mue.printStackTrace();
      return false;
    }
    catch (java.io.IOException ioe)
    {
      return false;
    }
    catch (NullPointerException npe)
    {
      npe.printStackTrace();
      return false;
    }

    // Check to see if the zip code was valid.
    return true;
  }

//  public String getDescription()
//  {
//    JsonArray obs = jse.getAsJsonObject().get("weather").getAsJsonArray();
//    String conditions = obs.get(0)
//            .getAsJsonObject().get("description").getAsString();
//    return conditions;
//  }

  public Image getIconSunday() {
    JsonArray icon = jse.getAsJsonObject().get("daily").getAsJsonArray();
    String icon2 = icon.get(0)
            .getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString();
    System.out.println(icon2);
    String icon3 = "http://openweathermap.org/img/wn/" + icon2 + ".png";
    return new Image(icon3);
  }

  public Image getIconMonday() {
    JsonArray icon = jse.getAsJsonObject().get("daily").getAsJsonArray();
    String icon2 = icon.get(1)
            .getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString();
    System.out.println(icon2);
    String icon3 = "http://openweathermap.org/img/wn/" + icon2 + ".png";
    return new Image(icon3);
  }

  public Image getIconTuesday() {
    JsonArray icon = jse.getAsJsonObject().get("daily").getAsJsonArray();
    String icon2 = icon.get(2)
            .getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString();
    System.out.println(icon2);
    String icon3 = "http://openweathermap.org/img/wn/" + icon2 + ".png";
    return new Image(icon3);
  }

  public Image getIconWednesday() {
    JsonArray icon = jse.getAsJsonObject().get("daily").getAsJsonArray();
    String icon2 = icon.get(3)
            .getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString();
    System.out.println(icon2);
    String icon3 = "http://openweathermap.org/img/wn/" + icon2 + ".png";
    return new Image(icon3);
  }


  public Image getIconThursday() {
    JsonArray icon = jse.getAsJsonObject().get("daily").getAsJsonArray();
    String icon2 = icon.get(4)
            .getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString();
    System.out.println(icon2);
    String icon3 = "http://openweathermap.org/img/wn/" + icon2 + ".png";
    return new Image(icon3);
  }

  public Image getIconFriday() {
    JsonArray icon = jse.getAsJsonObject().get("daily").getAsJsonArray();
    String icon2 = icon.get(5)
            .getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString();
    System.out.println(icon2);
    String icon3 = "http://openweathermap.org/img/wn/" + icon2 + ".png";
    return new Image(icon3);
  }

  public Image getIconSaturday() {
    JsonArray icon = jse.getAsJsonObject().get("daily").getAsJsonArray();
    String icon2 = icon.get(6)
            .getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString();
    System.out.println(icon2);
    String icon3 = "http://openweathermap.org/img/wn/" + icon2 + ".png";
    return new Image(icon3);
  }

  public String getMinSunday() {
    String temp = jse.getAsJsonObject().get("daily").getAsJsonArray().get(0).getAsJsonObject().get("temp").getAsJsonObject().get("min").getAsString();
//    System.out.println(temp);
//    double kelvin = Double.parseDouble(temp);
//    double min = ((1.8) * (kelvin - 273) + 32);
//    String b = String.format("%.1f", min);
//    return b;
    double temp2 = Double.parseDouble(temp);
    temp2 = -temp2;
    return String.valueOf(temp2);
  }

  public String getMaxSunday() {
    String maxSun = jse.getAsJsonObject().get("daily").getAsJsonArray().get(0).getAsJsonObject().get("temp").getAsJsonObject().get("max").getAsString();
    Double temp2 = Double.parseDouble(maxSun);
    temp2 = -temp2;
    return String.valueOf(temp2);
  }

  public String getMinMonday() {
    String minMon = jse.getAsJsonObject().get("daily").getAsJsonArray().get(1).getAsJsonObject().get("temp").getAsJsonObject().get("min").getAsString();
    Double temp2 = Double.parseDouble(minMon);
    temp2 = -temp2;
    return String.valueOf(temp2);
  }

  public String getMaxMonday() {
    String maxMon = jse.getAsJsonObject().get("daily").getAsJsonArray().get(1).getAsJsonObject().get("temp").getAsJsonObject().get("max").getAsString();
    Double temp2 = Double.parseDouble(maxMon);
    temp2 = -temp2;
    return String.valueOf(temp2);
  }

  public String getMinTuesday() {
    String minTues = jse.getAsJsonObject().get("daily").getAsJsonArray().get(2).getAsJsonObject().get("temp").getAsJsonObject().get("min").getAsString();
    Double temp2 = Double.parseDouble(minTues);
    temp2 = -temp2;
    return String.valueOf(temp2);
  }

  public String getMaxTuesday() {
    String maxTues = jse.getAsJsonObject().get("daily").getAsJsonArray().get(2).getAsJsonObject().get("temp").getAsJsonObject().get("max").getAsString();
    Double temp2 = Double.parseDouble(maxTues);
    temp2 = -temp2;
    return String.valueOf(temp2);
  }

  public String getMinWednesday() {
    String minWed = jse.getAsJsonObject().get("daily").getAsJsonArray().get(3).getAsJsonObject().get("temp").getAsJsonObject().get("min").getAsString();
    Double temp2 = Double.parseDouble(minWed);
    temp2 = -temp2;
    return String.valueOf(temp2);
  }

  public String getMaxWednesday() {
    String maxWed = jse.getAsJsonObject().get("daily").getAsJsonArray().get(3).getAsJsonObject().get("temp").getAsJsonObject().get("max").getAsString();
    Double temp2 = Double.parseDouble(maxWed);
    temp2 = -temp2;
    return String.valueOf(temp2);
  }

  public String getMinThursday() {
    String minThur = jse.getAsJsonObject().get("daily").getAsJsonArray().get(4).getAsJsonObject().get("temp").getAsJsonObject().get("min").getAsString();
    Double temp2 = Double.parseDouble(minThur);
    temp2 = -temp2;
    return String.valueOf(temp2);
  }

  public String getMaxThursday() {
    String maxThur = jse.getAsJsonObject().get("daily").getAsJsonArray().get(4).getAsJsonObject().get("temp").getAsJsonObject().get("max").getAsString();
    Double temp2 = Double.parseDouble(maxThur);
    temp2 = -temp2;
    return String.valueOf(temp2);
  }

  public String getMinFriday() {
    String minFrid = jse.getAsJsonObject().get("daily").getAsJsonArray().get(5).getAsJsonObject().get("temp").getAsJsonObject().get("min").getAsString();
    Double temp2 = Double.parseDouble(minFrid);
    temp2 = -temp2;
    return String.valueOf(temp2);
  }

  public String getMaxFriday() {
    String maxFrid = jse.getAsJsonObject().get("daily").getAsJsonArray().get(5).getAsJsonObject().get("temp").getAsJsonObject().get("max").getAsString();
    Double temp2 = Double.parseDouble(maxFrid);
    temp2 = -temp2;
    return String.valueOf(temp2);
  }

  public String getMinSaturday() {
    String minSat = jse.getAsJsonObject().get("daily").getAsJsonArray().get(6).getAsJsonObject().get("temp").getAsJsonObject().get("min").getAsString();
    Double temp2 = Double.parseDouble(minSat);
    temp2 = -temp2;
    return String.valueOf(temp2);
  }

  public String getMaxSaturday() {
    String maxSat = jse.getAsJsonObject().get("daily").getAsJsonArray().get(6).getAsJsonObject().get("temp").getAsJsonObject().get("max").getAsString();
    Double temp2 = Double.parseDouble(maxSat);
    temp2 = -temp2;
    return String.valueOf(temp2);
  }





}

//zip code
//http://api.openweathermap.org/data/2.5/weather?zip=95747,us&appid=a7199e21caf1144c0ff3ebdaec774bf3

//city name
//http://api.openweathermap.org/data/2.5/weather?q=london&appid=a7199e21caf1144c0ff3ebdaec774bf3

//air pollution
//https://api.openweathermap.org/data/2.5/onecall?lat=1&lon=1&exclude=hourly&appid=a7199e21caf1144c0ff3ebdaec774bf3

//7 day forecast
//https://api.openweathermap.org/data/2.5/onecall?lat={lat}&lon={lon}&exclude={part}&appid=a7199e21caf1144c0ff3ebdaec774bf3