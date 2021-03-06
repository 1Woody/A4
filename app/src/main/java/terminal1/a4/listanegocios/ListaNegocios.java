package terminal1.a4.listanegocios;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.os.Bundle;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import terminal1.a4.loginui.R;
import terminal1.a4.loginui.mapa;
import terminal1.a4.loginui.servicios;
import terminal1.a4.tarjeta_embarque.Tembarque;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.BreakIterator;

public class ListaNegocios extends AppCompatActivity {

    private TextView mTextViewResult;
    private RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_negocios);

        mTextViewResult = findViewById(R.id.text_view_result);
        Button buttonParse = findViewById(R.id.button_parse);

        Button buttonPost = findViewById(R.id.button_post);

        mQueue = Volley.newRequestQueue(this);
        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        });

        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonPost();
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_vuelos:
                        Intent intent3 = new Intent(ListaNegocios.this, Tembarque.class);
                        startActivity(intent3);
                        break;
                    case R.id.ic_mapa:
                        Intent intent1 = new Intent(ListaNegocios.this, mapa.class);
                        startActivity(intent1);
                        break;
                    case R.id.ic_servicios:
                        Intent intent2 = new Intent(ListaNegocios.this, servicios.class);
                        startActivity(intent2);
                        break;
                    case R.id.ic_tiendas:
                        break;
                    case R.id.ic_perfil:
                        Intent intent4 = new Intent(ListaNegocios.this, Perfil.class);
                        startActivity(intent4);
                        break;
                }
                return true;
            }
        });
    }

    private void jsonPost() {
        String url = "http://192.168.0.29:3001/pospasajeros";
        //mTextViewResult.setText("Todo Ready!");
        JSONObject pasajero = new JSONObject();
        try {
            pasajero.put("id_pasajero", "fatima@hotmail.com");
            pasajero.put("posicionx", -40.567);
            pasajero.put("posiciony", 3.567);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //mTextViewResult.setText("Response: " + pasajero.toString());

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, pasajero, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //nada
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //si entra aqui esta bien ya que en el post no hay json.
            }
        });
        /*
        StringRequest stringRequest = new StringRequest (Request.Method.POST, url, new Response.Listener<String>() {
            public void onResponse(String response) {
                mTextViewResult.setText("Post Done!");
                response = pasajero.toString();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                mTextViewResult.setText(error.getMessage());
            }
        });*/

        mQueue.add(jsonRequest);
    }

    private void jsonParse() {
        String url = "http://192.168.0.29:3001/pospasajeros";
        mTextViewResult.setText("");
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //mTextViewResult.setText("Response: " + response.toString());
                        try {
                            //JSONArray jsonArray = response.getJSONArray("pospasajeros");
                            //JSONObject id_pasajero = response.getJSONObject("id_pasajero");
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject pasajero = response.getJSONObject(i);
                                String id = pasajero.getString("id_pasajero");
                                double posx = pasajero.getDouble("posicionx");
                                double posy = pasajero.getDouble("posiciony");
                                mTextViewResult.append(id + ", " + String.valueOf(posx) + ", " + String.valueOf(posy) + "\n\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                mTextViewResult.setText(error.getMessage());
            }
        });
        mQueue.add(request);
    }
}
