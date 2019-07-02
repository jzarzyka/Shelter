package zarzyka.jagoda.shelter.requests.shelter;

import com.android.volley.Request;
import com.android.volley.Response;
import zarzyka.jagoda.shelter.requests.BaseRequest;
import zarzyka.jagoda.shelter.utils.UrlChanger;

public class GetShelterUsers extends BaseRequest {

    public GetShelterUsers(String sessionId, String shelterId, Response.Listener listener,
            Response.ErrorListener errorListener) {
        super(Request.Method.GET,
                UrlChanger.URL + "/shelter/users/" + shelterId + "?sessionId=" + sessionId, null,
                listener,
                errorListener);
    }
}
