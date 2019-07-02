package zarzyka.jagoda.shelter.requests.animal;

import com.android.volley.Request;
import com.android.volley.Response;
import zarzyka.jagoda.shelter.requests.BaseRequest;
import zarzyka.jagoda.shelter.utils.UrlChanger;

public class UpdateAnimal extends BaseRequest {

    public UpdateAnimal(String sessionId, String body, Response.Listener listener,
            Response.ErrorListener errorListener) {
        super(Request.Method.PUT, UrlChanger.URL + "/animal?sessionId=" + sessionId, body, listener,
                errorListener);
    }
}
