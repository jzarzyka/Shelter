package zarzyka.jagoda.shelter.requests.user;

import com.android.volley.Request;
import com.android.volley.Response;
import zarzyka.jagoda.shelter.requests.BaseRequest;
import zarzyka.jagoda.shelter.utils.UrlChanger;

public class GetOneUser extends BaseRequest {

    public GetOneUser(String sessionId, String userLogin, Response.Listener listener,
            Response.ErrorListener errorListener) {
        super(Request.Method.GET,
                UrlChanger.URL + "/user/" + userLogin + "?sessionId=" + sessionId, null,
                listener,
                errorListener);
    }
}
