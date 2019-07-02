package zarzyka.jagoda.shelter.requests.animal;

import com.android.volley.Request;
import com.android.volley.Response;
import zarzyka.jagoda.shelter.requests.BaseRequest;
import zarzyka.jagoda.shelter.utils.UrlChanger;

public class GetAllAnimals extends BaseRequest {

    public GetAllAnimals(String sessionId, Response.Listener listener, Response.ErrorListener errorListener) {
        super(Request.Method.GET, UrlChanger.URL + "/animal?sessionId=" + sessionId, null, listener,
                errorListener);
    }
}
