package zarzyka.jagoda.shelter.requests.shelter;

import com.android.volley.Request;
import com.android.volley.Response;
import zarzyka.jagoda.shelter.requests.BaseRequest;
import zarzyka.jagoda.shelter.utils.UrlChanger;

public class SaveShelter extends BaseRequest {

    public SaveShelter(String sessionId, String body, Response.Listener listener,
            Response.ErrorListener errorListener) {
        super(Request.Method.POST, UrlChanger.URL + "/shelter?sessionId=" + sessionId, body, listener,
                errorListener);
    }
}
