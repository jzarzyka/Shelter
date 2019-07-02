package zarzyka.jagoda.shelter.requests.user;

import com.android.volley.Request;
import com.android.volley.Response;
import zarzyka.jagoda.shelter.requests.BaseRequest;
import zarzyka.jagoda.shelter.utils.UrlChanger;

public class UpdateUser extends BaseRequest {

    public UpdateUser(String sessionId, String body, Response.Listener listener,
            Response.ErrorListener errorListener) {
        super(Request.Method.PUT, UrlChanger.URL + "/user?sessionId=" + sessionId, body, listener,
                errorListener);
    }
}
