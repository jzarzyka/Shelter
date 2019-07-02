package zarzyka.jagoda.shelter.requests.adoption;

import com.android.volley.Request;
import com.android.volley.Response;
import zarzyka.jagoda.shelter.requests.BaseRequest;
import zarzyka.jagoda.shelter.utils.UrlChanger;

public class GetAdoption extends BaseRequest {

    public GetAdoption(String sessionId, String adoptionId, Response.Listener listener,
            Response.ErrorListener errorListener) {
        super(Request.Method.GET,
                UrlChanger.URL + "/adoption/" + adoptionId + "?sessionId=" + sessionId, null,
                listener,
                errorListener);
    }
}
