package zarzyka.jagoda.shelter.requests.shelter;

import com.android.volley.Request;
import com.android.volley.Response;
import zarzyka.jagoda.shelter.requests.BaseRequest;
import zarzyka.jagoda.shelter.utils.UrlChanger;

public class GetAllShelters extends BaseRequest {

    public GetAllShelters(String sessionId, Response.Listener listener, Response.ErrorListener errorListener) {
        super(Request.Method.GET, UrlChanger.URL + "/shelter/all?sessionId=" + sessionId, null, listener,
                errorListener);
    }
}
