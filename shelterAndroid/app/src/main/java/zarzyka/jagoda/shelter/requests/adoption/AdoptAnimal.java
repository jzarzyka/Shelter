package zarzyka.jagoda.shelter.requests.adoption;

import com.android.volley.Request;
import com.android.volley.Response;
import zarzyka.jagoda.shelter.requests.BaseRequest;
import zarzyka.jagoda.shelter.utils.UrlChanger;

public class AdoptAnimal extends BaseRequest {

    public AdoptAnimal(String sessionId, String body, Response.Listener listener,
            Response.ErrorListener errorListener) {
        super(Request.Method.POST, UrlChanger.URL + "/adoption?sessionId=" + sessionId, body, listener,
                errorListener);
    }
}
