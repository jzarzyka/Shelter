package zarzyka.jagoda.shelter.requests.animal;

import com.android.volley.Request;
import com.android.volley.Response;
import zarzyka.jagoda.shelter.requests.BaseRequest;
import zarzyka.jagoda.shelter.utils.UrlChanger;

public class GetOneAnimal extends BaseRequest {

    public GetOneAnimal(String sessionId, String animalId, Response.Listener listener,
            Response.ErrorListener errorListener) {
        super(Request.Method.GET,
                UrlChanger.URL + "/animal/" + animalId + "?sessionId=" + sessionId, null,
                listener,
                errorListener);
    }
}
