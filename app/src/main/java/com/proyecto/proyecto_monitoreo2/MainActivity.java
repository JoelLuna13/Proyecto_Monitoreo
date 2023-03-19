package com.proyecto.proyecto_monitoreo2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ekn.gruzer.gaugelibrary.ArcGauge;
import com.proyecto.proyecto_monitoreo2.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {

    ArcGauge Medidor1, Medidor2, Medidor3, Medidor4;
    com.ekn.gruzer.gaugelibrary.Range Rango_1,Rango_2,Rango_3,R1,R2,R3, Rt1,Rt2,Rt3, Rt0,Rh0, Rh1, Rh2, Rh3; //Estos rangos son para los colores
    TextView txttit, txtmen, txtr1,txtr2,txth1, txth2,txtt1, txtt2;

    SwipeRefreshLayout swipe;

    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipe = (SwipeRefreshLayout)findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recreate();
                Toast.makeText(MainActivity.this, "Datos Actualizados", Toast.LENGTH_SHORT).show();
            }
        });
        txttit = (TextView)findViewById(R.id.txtgas);
        txtmen = (TextView)findViewById(R.id.txt1);
        txtr1 = (TextView)findViewById(R.id.txtruido);
        txtr2 = (TextView)findViewById(R.id.txt2);
        txtt1 = (TextView)findViewById(R.id.txttemp);
        txtt2 = (TextView)findViewById(R.id.txt3);
        txth1 = (TextView)findViewById(R.id.txthum);
        txth2 = (TextView)findViewById(R.id.txt4);

        //plot = (XYPlot) principal.findViewById(R.id.plot);

        Medidor1 = (ArcGauge)findViewById(R.id.Medgas);
        Medidor2 = (ArcGauge)findViewById(R.id.Medruido);
        Medidor3 = (ArcGauge)findViewById(R.id.Medtemp);
        Medidor4 = (ArcGauge)findViewById(R.id.Medhum);

        Rango_1 = new com.ekn.gruzer.gaugelibrary.Range();
        Rango_1.setColor(Color.GREEN);
        Rango_1.setFrom(0);
        Rango_1.setTo(70);

        Rango_2 = new com.ekn.gruzer.gaugelibrary.Range();
        Rango_2.setColor(Color.YELLOW);
        Rango_2.setFrom(70);
        Rango_2.setTo(350);

        Rango_3 = new com.ekn.gruzer.gaugelibrary.Range();
        Rango_3.setColor(Color.RED);
        Rango_3.setFrom(350);
        Rango_3.setTo(1000);

        Medidor1.addRange(Rango_1);
        Medidor1.addRange(Rango_2);
        Medidor1.addRange(Rango_3);

        Medidor1.setMinValue(0);
        Medidor1.setMaxValue(1000);
        Medidor1.setValue(0);

        R1 = new com.ekn.gruzer.gaugelibrary.Range();
        R1.setColor(Color.GREEN);
        R1.setFrom(0);
        R1.setTo(70);

        R2 = new com.ekn.gruzer.gaugelibrary.Range();
        R2.setColor(Color.YELLOW);
        R2.setFrom(70);
        R2.setTo(85);

        R3 = new com.ekn.gruzer.gaugelibrary.Range();
        R3.setColor(Color.RED);
        R3.setFrom(85);
        R3.setTo(100);

        Medidor2.addRange(R1);
        Medidor2.addRange(R2);
        Medidor2.addRange(R3);

        Medidor2.setMinValue(0);
        Medidor2.setMaxValue(100);
        Medidor2.setValue(0);

        Rt0 = new com.ekn.gruzer.gaugelibrary.Range();
        Rt0.setColor(Color.BLUE);
        Rt0.setFrom(0);
        Rt0.setTo(20);

        Rt1 = new com.ekn.gruzer.gaugelibrary.Range();
        Rt1.setColor(Color.GREEN);
        Rt1.setFrom(0);
        Rt1.setTo(24);

        Rt2 = new com.ekn.gruzer.gaugelibrary.Range();
        Rt2.setColor(Color.YELLOW);
        Rt2.setFrom(24);
        Rt2.setTo(30);

        Rt3 = new com.ekn.gruzer.gaugelibrary.Range();
        Rt3.setColor(Color.RED);
        Rt3.setFrom(30);
        Rt3.setTo(50);

        Medidor3.addRange(Rt1);
        Medidor3.addRange(Rt2);
        Medidor3.addRange(Rt3);
        //Medidor3.addRange(Rt0);

        Medidor3.setMinValue(0);
        Medidor3.setMaxValue(50);
        Medidor3.setValue(0);

        Rh0 = new com.ekn.gruzer.gaugelibrary.Range();
        Rh0.setColor(Color.BLUE);
        Rh0.setFrom(0);
        Rh0.setTo(20);

        Rh1 = new com.ekn.gruzer.gaugelibrary.Range();
        Rh1.setColor(Color.GREEN);
        Rh1.setFrom(0);
        Rh1.setTo(60);

        Rh2 = new com.ekn.gruzer.gaugelibrary.Range();
        Rh2.setColor(Color.YELLOW);
        Rh2.setFrom(60);
        Rh2.setTo(70);

        Rh3 = new com.ekn.gruzer.gaugelibrary.Range();
        Rh3.setColor(Color.RED);
        Rh3.setFrom(70);
        Rh3.setTo(85);

        Medidor4.addRange(Rh1);
        Medidor4.addRange(Rh2);
        Medidor4.addRange(Rh3);
        //Medidor4.addRange(Rh0);

        Medidor4.setMinValue(0);
        Medidor4.setMaxValue(85);
        Medidor4.setValue(0);



        getData();
        getRuido();
        getTemp();
        getHum();


         //Medidor1 info = findViewById(R.id.info);
        Medidor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), Grafgas.class);
                startActivityForResult(intent, 0);
            }
        });
        Medidor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), activity_grafruido.class);
                startActivityForResult(intent, 0);
            }
        });
        Medidor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), Graftemp.class);
                startActivityForResult(intent, 0);
            }
        });
        Medidor4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), Grafhum.class);
                startActivityForResult(intent, 0);
            }
        });
        ejecutar();

    }
    private void ejecutar(){
        final Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //recreate();//llamamos nuestro metodo
                getTemp();
                getRuido();
                getHum();
                getData();
                handler.postDelayed(this,5000);//se ejecutara cada 5 segundos
            }
        },5000);//empezara a ejecutarse después de 5 segundos
    }

    public void getData(){

        String sql = "https://dry-fjord-94565.herokuapp.com/valor";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url = null;
        HttpURLConnection conn;
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
            int men = 0;
            for(int i = 0;i<jsonArr.length();i++){
                JSONObject jsonObject = jsonArr.getJSONObject(i);
                men= jsonObject.getInt("gas");
                mensaje = jsonObject.getString("gas") +" PPM";
                //dato = jsonObject.getDouble("gas");

            }
            txttit.setText(mensaje);
            if (men>=0 &&  men<= 55)
            {
                Medidor1.setValue(men);
                //Medidor.setSecondValue(0);
                txtmen.setText("El aire esta limpio");
                //txttit.setTextColor(Color.GREEN);
                //txtmen.setTextColor(Color.GREEN);
            }
            else
            {
                if (men>55 &&  men<= 70)
                {
                    //mensaje = "Aire limpio con poca presencia de dioxido de carbono";
                    Medidor1.setValue(men);
                    //Medidor.setValue(0);
                    txtmen.setText("Hay poca presencia de dioxido de carbono en el ambiente");
                    //txttit.setTextColor(Color.GREEN);
                    //txtmen.setTextColor(Color.GREEN);

                }
                else
                {
                    if (men>70 &&  men<= 350)
                    {
                        Medidor1.setValue(men);
                        txtmen.setText("Peligro: Presencia de dioxido de carbono en el ambiente");

                        //txttit.setTextColor(Color.BLUE);
                        //txtmen.setTextColor(Color.BLUE);
                        //  AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        //Toast.makeText(MainActivity, "Aire contaminado, presencia de dioxido de carbono", Toast.LENGTH_SHORT).show();
                       // Toast.makeText(this, "Aire contaminado, presencia de dioxido de carbono", Toast.LENGTH_LONG).show();
                        /*2. Chain together various setter methods to set the dialog characteristics
                        builder.setMessage("Aire contaminado, presencia de dioxido de carbono")
                                .setTitle("Advertencia");
                        builder.setPositiveButton("Enterado", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                            }
                        });
                        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                        });*/
                        // builder.show();

                    }
                    else
                    {
                        if (men>350)
                        {
                            Medidor1.setValue(men);
                            txtmen.setText("Peligro: Presencia de gases toxicos en el ambiente");
                            //txttit.setTextColor(Color.RED);
                            //txtmen.setTextColor(Color.RED);

                            //Toast.makeText(getActivity(), "Aire contaminado, presencia de gases tóxicos", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(this, "Aire contaminado, presencia de gases toxicos (Propano, Butano)", Toast.LENGTH_SHORT).show();
                            //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                            /*2. Chain together various setter methods to set the dialog characteristics
                           builder.setMessage("Aire contaminado, presencia de gases toxicos (Propano, Butano)")
                                    .setTitle("Advertencia");
                            builder.setPositiveButton("Enterado", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User clicked OK button
                                }
                            });

                                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                                }
                            });*/
                            //builder.show();

                        }
                    }
                }

            }
            //sal.setText(mensaje);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void getRuido(){
        String sql = "https://dry-fjord-94565.herokuapp.com/valor";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url = null;
        HttpURLConnection conn;

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
            int men = 0;
            for(int i = 0;i<jsonArr.length();i++){
                JSONObject jsonObject = jsonArr.getJSONObject(i);

                //*****Todo esto muestra los datos  de la api solo en textview ya que esta como string
                /*Log.d("nombre",jsonObject.optString("nombre"));
                mensaje += "Fecha "+i+" "+jsonObject.optString("fecha")+"\n";
                Log.d("nombre",jsonObject.optString("nombre"));
                mensaje += "Gas "+i+" "+jsonObject.optString("gas")+"\n";
                Log.d("nombre",jsonObject.optString("nombre"));
                mensaje += "Humo "+i+" "+jsonObject.optString("hum")+"\n";
                Log.d("nombre",jsonObject.optString("nombre"));
                mensaje += "Ruido "+i+" "+jsonObject.optString("ruido")+"\n";
                Log.d("nombre",jsonObject.optString("nombre"));
                mensaje += "Temperatura "+i+" "+jsonObject.optString("temp")+"\n";
                 Bundle medgas = new Bundle();
                medgas.putInt("gas", men);
                Gas.instantiate(medgas);*/
                men = jsonObject.getInt("ruido");
                mensaje = jsonObject.getString("ruido")+ " dB";
            }
            Medidor2.setValue(men);
            //sal.setText(mensaje);
            txtr1.setText(mensaje);
            if (men>=0 &&  men<= 70)
            {
                txtr2.setText("El ruido en la sala se encuentra dentro del limite");

            }
            else
            {
                if (men>70 &&  men<= 85)
                {

                    txtr2.setText("¡Cuidado!, El ruido en la sala esta llegando al estado critico");
                    //Toast.makeText(this, "El ruido en la sala esta llegando al estado critico", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (men>85)
                    {

                        txtr2.setText("¡ADVERTENCIA! El ruido de la sala puede causar daños auditivos");
                        //Toast.makeText(this, "El ruido de la sala puede causar problemas auditivos", Toast.LENGTH_SHORT).show();
                        //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

// 2. Chain together various setter methods to set the dialog characteristics
                      /*  builder.setMessage("El ruido de la sala puede causar daños auditivos")
                                .setTitle("¡ADVERTENCIA!");
                        builder.setPositiveButton("Enterado", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                            }
                        });
               /* builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });*/
                        //builder.show();
                    }
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void getTemp(){
        String sql = "https://dry-fjord-94565.herokuapp.com/valor";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url = null;
        HttpURLConnection conn;

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
            String mensaje = "", mensaje2 = "";

            int men = 0, men2 = 0;
            for(int i = 0;i<jsonArr.length();i++){
                JSONObject jsonObject = jsonArr.getJSONObject(i);

                //*****Todo esto muestra los datos  de la api solo en textview ya que esta como string
                /*Log.d("nombre",jsonObject.optString("nombre"));
                mensaje += "Fecha "+i+" "+jsonObject.optString("fecha")+"\n";
                Log.d("nombre",jsonObject.optString("nombre"));
                mensaje += "Gas "+i+" "+jsonObject.optString("gas")+"\n";
                Log.d("nombre",jsonObject.optString("nombre"));
                mensaje += "Humo "+i+" "+jsonObject.optString("hum")+"\n";
                Log.d("nombre",jsonObject.optString("nombre"));
                mensaje += "Ruido "+i+" "+jsonObject.optString("ruido")+"\n";
                Log.d("nombre",jsonObject.optString("nombre"));
                mensaje += "Temperatura "+i+" "+jsonObject.optString("temp")+"\n";
                 Bundle medgas = new Bundle();
                medgas.putInt("gas", men);
                Gas.instantiate(medgas);*/
                men = jsonObject.getInt("temp");
                Log.d("nombre",jsonObject.optString("nombre"));
                mensaje = jsonObject.getString("temp") + " ºC"+"\n";
            }

            Medidor3.setValue(men);
            //sal.setText(mensaje);
            txtt1.setText(mensaje);
                if (men < 20) {
                    txtt2.setText("¡CUIDADO! La temperatura esta bajando");
                    //Toast.makeText(this, "La temperatura esta bajando", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (men >= 20 && men <= 24) {
                        txtt2.setText("La temperatura en la sala es adecuada");
                    } else {
                        if (men > 24 && men <= 30) {

                            txtt2.setText("¡Cuidado!, La temperatura en la sala esta llegando al estado critico");
                            //Toast.makeText(this, "La temperatura esta llegando al estado critico", Toast.LENGTH_SHORT).show();
                        } else {
                            if (men > 30 ) {

                                txtt2.setText("¡ADVERTENCIA! La temperatura de la sala puede causar daños en la salud");
                                //Toast.makeText(this, "La temperatura en la sala puede causar daños en la salud", Toast.LENGTH_SHORT).show();
                        /*AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

// 2. Chain together various setter methods to set the dialog characteristics
                        builder.setMessage("La temperatura de la sala puede causar daños en la salud")
                                .setTitle("¡ADVERTENCIA!");
                        builder.setPositiveButton("Enterado", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                            }
                        });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                        builder.show();*/
                            }
                        }
                    }
                }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getHum() {
        String sql = "https://dry-fjord-94565.herokuapp.com/valor";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url = null;
        HttpURLConnection conn;

        try {
            url = new URL(sql);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            conn.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine;

            StringBuffer response = new StringBuffer();

            String json = "";

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            json = response.toString();

            JSONArray jsonArr = null;

            jsonArr = new JSONArray(json);
            String mensaje = "", mensaje2 = "";

            int men = 0, men2 = 0;
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObject = jsonArr.getJSONObject(i);

                //*****Todo esto muestra los datos  de la api solo en textview ya que esta como string
                /*Log.d("nombre",jsonObject.optString("nombre"));
                mensaje += "Fecha "+i+" "+jsonObject.optString("fecha")+"\n";
                Log.d("nombre",jsonObject.optString("nombre"));
                mensaje += "Gas "+i+" "+jsonObject.optString("gas")+"\n";
                Log.d("nombre",jsonObject.optString("nombre"));
                mensaje += "Humo "+i+" "+jsonObject.optString("hum")+"\n";
                Log.d("nombre",jsonObject.optString("nombre"));
                mensaje += "Ruido "+i+" "+jsonObject.optString("ruido")+"\n";
                Log.d("nombre",jsonObject.optString("nombre"));
                mensaje += "Temperatura "+i+" "+jsonObject.optString("temp")+"\n";
                 Bundle medgas = new Bundle();
                medgas.putInt("gas", men);
                Gas.instantiate(medgas);*/
                men = jsonObject.getInt("hum");
                mensaje2 = jsonObject.getString("hum") + " %" + "\n";
            }
            Medidor4.setValue(men);
            //sal.setText(mensaje);
            txth1.setText(mensaje2);
            //Humedad
            if (men >= 20 && men <= 60) {

                txth2.setText("La Humedad en la sala es adecuada");
            } else {
                if (men > 60 && men <= 70) {

                    txth2.setText("¡Cuidado!, La Humedad en la sala esta llegando al estado critico");
                    //Toast.makeText(this, "La humedad en la sala esta llegando al estado critico", Toast.LENGTH_SHORT).show();
                } else {
                    if (men > 70 && men <= 85) {

                        txth2.setText("¡ADVERTENCIA! La Humedad de la sala puede causar daños en la salud");
                        //Toast.makeText(this, "La humedad en la sala puede causar daños en la salud", Toast.LENGTH_SHORT).show();


                        /*AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

// 2. Chain together various setter methods to set the dialog characteristics
                        builder.setMessage("La temperatura de la sala puede causar daños en la salud")
                                .setTitle("¡ADVERTENCIA!");
                        builder.setPositiveButton("Enterado", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                            }
                        });
               /* builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                        builder.show();*/
                    } else {
                        if (men < 20) {

                            txth2.setText("¡ADVERTENCIA! La Humedad de la sala puede causar daños en la salud");
                            //Toast.makeText(this, "La humedad en la sala puede causar daños en la salud", Toast.LENGTH_SHORT).show();


                            /*AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

// 2. Chain together various setter methods to set the dialog characteristics
                            builder.setMessage("La temperatura de la sala puede causar daños en la salud")
                                    .setTitle("¡ADVERTENCIA!");
                            builder.setPositiveButton("Enterado", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User clicked OK button
                                }
                            });
               /* builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                            builder.show();*/
                        }
                    }
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}