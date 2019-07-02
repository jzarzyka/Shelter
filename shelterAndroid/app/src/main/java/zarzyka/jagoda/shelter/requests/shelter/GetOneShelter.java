package zarzyka.jagoda.shelter.requests.shelter;

import com.android.volley.Request;
import com.android.volley.Response;
import zarzyka.jagoda.shelter.requests.BaseRequest;
import zarzyka.jagoda.shelter.utils.UrlChanger;

public class GetOneShelter extends BaseRequest {

    public GetOneShelter(String sessionId, String shelterId, Response.Listener listener,
            Response.ErrorListener errorListener) {
        super(Request.Method.GET,
                UrlChanger.URL + "/shelter/" + shelterId + "?sessionId=" + sessionId, null,
                listener,
                errorListener);
    }
}
