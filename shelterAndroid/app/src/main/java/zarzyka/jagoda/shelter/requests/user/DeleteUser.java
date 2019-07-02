package zarzyka.jagoda.shelter.requests.user;

import com.android.volley.Response;
import zarzyka.jagoda.shelter.requests.BaseRequest;
import zarzyka.jagoda.shelter.utils.UrlChanger;

public class DeleteUser extends BaseRequest {

    public DeleteUser(String sessionId, String userLogin, Response.Listener listener,
            Response.ErrorListener errorListener) {
        super(Method.DELETE, UrlChanger.URL + "/user/" + userLogin + "?sessionId=" + sessionId, null, listener,
                errorListener);
    }
}
