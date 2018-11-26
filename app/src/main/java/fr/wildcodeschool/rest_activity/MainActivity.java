package fr.wildcodeschool.rest_activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private final static String API_KEY = "7dc3f0fbd0bc80eef0350c219ffa9121";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Crée une file d'attente pour les requêtes vers l'API
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://api.openweathermap.org/data/2.5/forecast?q=Toulouse&APPID=" + API_KEY;

        // Création de la requête vers l'API, ajout des écouteurs pour les réponses et erreurs possibles
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray list = response.getJSONArray("list");
                            for (int i = 0; i < list.length(); i+=8) {
                                JSONObject listInfos = (JSONObject) list.get(i);
                                JSONArray weather = listInfos.getJSONArray("weather");
                                JSONObject weatherInfos = (JSONObject) weather.get(0);
                                String description = weatherInfos.getString("description");
                                Toast toast = Toast.makeText(MainActivity.this, description, Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0,i*15);
                                toast.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Afficher l'erreur
                        Log.d("VOLLEY_ERROR", "onErrorResponse: " + error.getMessage());
                    }
                }
        );

        // On ajoute la requête à la file d'attente
        requestQueue.add(jsonObjectRequest);

    }
}
