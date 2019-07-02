package zarzyka.jagoda.shelter.requests.user;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.google.gson.JsonObject;
import zarzyka.jagoda.shelter.requests.BaseRequest;
import zarzyka.jagoda.shelter.utils.UrlChanger;

public class Login extends BaseRequest {
    public Login(final JsonObject requestBody, final Listener listener, final ErrorListener errorListener) {
        super(Request.Method.POST, UrlChanger.URL + "/user", requestBody.toString(), listener, errorListener);
    }
}
