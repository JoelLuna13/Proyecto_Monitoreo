package com.proyecto.proyecto_monitoreo2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;

import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Arrays;

public class activity_grafruido extends AppCompatActivity {

    XYPlot plot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafruido);
        plot = (XYPlot)findViewById(R.id.plot2);

        getruido();
    }
    public void getruido(){
        String sql = "https://dry-fjord-94565.herokuapp.com/grafica";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url = null;
        HttpURLConnection conn;


        final Number[] domainLabels = {0, 1, 2, 3,4,5,6, 7, 8, 9};
        Number[] series1Numbers = {0,0,0,0,0,0,0,0,0,0};
        Number[] series2Numbers = {0,0,0,0,0,0,0,0,0,0};
        Number[] series3Numbers = {0,0,0,0,0,0,0,0,0,0};

        try {
            url = new URL(sql);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            conn.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine;

            StringBuffer response = new StringBuffer();

            String json = "";

            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }

            json = response.toString();

            JSONArray jsonArr = null;

            jsonArr = new JSONArray(json);
            String mensaje = "";
            Number dato, dato1, dato2, dato3;
            int men = 0;


            for(int i = 0;i<jsonArr.length();i++){
                JSONObject jsonObject = jsonArr.getJSONObject(i);

                //Log.d("nombre",jsonObject.optString("nombre"));
                //men = jsonObject.getInt("gas");
                dato = jsonObject.getLong("ruido");


                //vec[i] = dato.floatValue();
                series1Numbers[i] = dato.floatValue();
                series2Numbers[i] = dato.floatValue() + 5;
                series3Numbers[i] = dato.floatValue() - 5;




            }

            // turn the above arrays into XYSeries':
            // (Y_VALS_ONLY means use the element index as the x value)
            XYSeries series1 = new SimpleXYSeries(
                    Arrays.asList(series1Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Datos_Ruido");
            XYSeries series2 = new SimpleXYSeries(
                    Arrays.asList(series2Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "");
            XYSeries series3 = new SimpleXYSeries(
                    Arrays.asList(series3Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "");
            // create formatters to use for drawing a series using LineAndPointRenderer
            // and configure them from xml:
            LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.BLACK, Color.RED, Color.TRANSPARENT, null);
            LineAndPointFormatter series2Format = new LineAndPointFormatter(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, null);
            LineAndPointFormatter series3Format = new LineAndPointFormatter(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, null);


            // (optional) add some smoothing to the lines:
            // see: http://androidplot.com/smooth-curves-and-androidplot/
            series1Format.setInterpolationParams(
                    new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));
            series2Format.setInterpolationParams(
                    new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));
            series3Format.setInterpolationParams(
                    new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));

            // add a new series' to the xyplot:
            plot.addSeries(series1, series1Format);
            plot.addSeries(series2, series2Format);
            plot.addSeries(series3, series3Format);

            plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format() {
                @Override
                public StringBuffer format(Object obj, @NonNull StringBuffer toAppendTo, @NonNull FieldPosition pos) {
                    int j = Math.round(((Number) obj).floatValue());
                    return toAppendTo.append(domainLabels[j]);
                }
                @Override
                public Object parseObject(String source, @NonNull ParsePosition pos) {
                    return null;
                }
            });


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}