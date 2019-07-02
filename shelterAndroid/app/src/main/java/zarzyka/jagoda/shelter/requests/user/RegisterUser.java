package zarzyka.jagoda.shelter.requests.user;

import com.android.volley.Request;
import com.android.volley.Response;
import zarzyka.jagoda.shelter.requests.BaseRequest;
import zarzyka.jagoda.shelter.utils.UrlChanger;

public class RegisterUser extends BaseRequest {

    public RegisterUser(String body, Response.Listener listener,
            Response.ErrorListener errorListener) {
        super(Request.Method.POST, UrlChanger.URL + "/user/register", body, listener,
                errorListener);
    }
}
