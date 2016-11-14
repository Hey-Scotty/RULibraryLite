package ru;

 /*
 Created by Sam Harrison on 11/7/2016 with help from the tutorial provided by Tonikami TV
  */

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class RegisterRequest extends StringRequest {
    public static final String REGISTER_REQUEST_URL = "https://php.radford.edu/~sharrison3/itec370/PHPScripts/Register.php";
    private Map<String, String> params;

    public RegisterRequest(String username, String password, String name, String email, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("username", username);
        params.put("password", password);
        params.put("name", name);
        params.put("email", email);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
