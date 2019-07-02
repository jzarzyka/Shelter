package zarzyka.jagoda.shelter.requests.user;

import com.android.volley.Request;
import com.android.volley.Response;
import zarzyka.jagoda.shelter.requests.BaseRequest;
import zarzyka.jagoda.shelter.utils.UrlChanger;

public class GetAllUsers extends BaseRequest {

    public GetAllUsers(String sessionId, Response.Listener listener, Response.ErrorListener errorListener) {
        super(Request.Method.GET, UrlChanger.URL + "/user/all?sessionId=" + sessionId, null, listener,
                errorListener);
    }
}
