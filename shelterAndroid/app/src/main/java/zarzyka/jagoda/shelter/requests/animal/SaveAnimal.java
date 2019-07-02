package zarzyka.jagoda.shelter.requests.animal;

import com.android.volley.Request;
import com.android.volley.Response;
import zarzyka.jagoda.shelter.requests.BaseRequest;
import zarzyka.jagoda.shelter.utils.UrlChanger;

public class SaveAnimal extends BaseRequest {

    public SaveAnimal(String sessionId, String body, Response.Listener listener,
            Response.ErrorListener errorListener) {
        super(Request.Method.POST, UrlChanger.URL + "/animal?sessionId=" + sessionId, body, listener,
                errorListener);
    }
}
