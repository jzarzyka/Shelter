package zarzyka.jagoda.shelter.requests.shelter;

import com.android.volley.Request;
import com.android.volley.Response;
import zarzyka.jagoda.shelter.requests.BaseRequest;
import zarzyka.jagoda.shelter.utils.UrlChanger;

public class UpdateShelter extends BaseRequest {

    public UpdateShelter(String sessionId, String body, Response.Listener listener,
            Response.ErrorListener errorListener) {
        super(Request.Method.PUT, UrlChanger.URL + "/shelter?sessionId=" + sessionId, body, listener,
                errorListener);
    }
}
