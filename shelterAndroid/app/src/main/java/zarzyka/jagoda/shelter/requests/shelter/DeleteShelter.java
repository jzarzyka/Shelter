package zarzyka.jagoda.shelter.requests.shelter;

import com.android.volley.Response;
import zarzyka.jagoda.shelter.requests.BaseRequest;
import zarzyka.jagoda.shelter.utils.UrlChanger;

public class DeleteShelter extends BaseRequest {

    public DeleteShelter(String sessionId, String shelterId, Response.Listener listener,
            Response.ErrorListener errorListener) {
        super(Method.DELETE, UrlChanger.URL + "/shelter/" + shelterId + "?sessionId=" + sessionId, null, listener,
                errorListener);
    }
}
