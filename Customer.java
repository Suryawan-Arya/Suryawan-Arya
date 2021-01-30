package com.example.rent;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Customer extends User {


    private String name;
    private static String URL = "https://rent-123.000webhostapp.com/Rent/login.php";
    private static String URL_REGISTER = "https://rent-123.000webhostapp.com/registercustomer.php";
    private static String URL_USER = "https://rent-123.000webhostapp.com/userdata.php";


    public Customer() {
    }

    public Customer(String username, String name, String email, int phone, String password) {
        super(username, email, phone, password);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    void register(Context con, String userN, String customerName, String mail, String address, String phone, String pass, String location){
        final String userName = userN;
        final String name = customerName;
        final String email = mail;
        final String phoneNum = phone;
        final String password = pass;
        final String type = "c";
        final Context context = con;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println(response);
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(context,"Register Success", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(context, CustomerMenu.class);
                                context.startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context,"Register error " + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,"Register error 2 " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", userName);
                params.put("name", name);
                params.put("email", email);
                params.put("phone", phoneNum);
                params.put("password", password);
                params.put("type", type);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    ArrayList<String> getUserData(Context con, int userId){
    	ArrayList<String> userData = new ArrayList<>();

    	StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_USER,
    		new Response.Listener<String>(){
    			@Override
    			public void onResponse(String response){
    				try{
    					JSONObject jsonObject = new JSONObject(response);
    					String success = jsonObject.getString("success");
    				}catch(JSONException e){
    					e.printStackTrace();
    					Toast.makeText(context,"Register error " + e.toString(), Toast.LENGTH_LONG).show();
    				}
    			
    			}
    		},
    		new Response.ErrorListener(){
    			@Override
    			public void onErrorResponse(VerifyError error){
    				Toast.makeText(context,"Register error 2 " + error.toString(), Toast.LENGTH_LONG).show();
    			}
    		});
    	RequestQueue requestQueue = Volley.newRequestQueue(context);
    	requestQueue.add(stringRequest);

    	return userData;
    }
}