package zarzyka.jagoda.shelter.models;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class LoggedInUser {

    private String role = "";
    private String sessionId = "";

    public static String getSessionId(Activity activity) {
        SharedPreferences sharedPreferences = activity
                .getSharedPreferences("shelterPreferences", Context.MODE_PRIVATE);
        return sharedPreferences.getString("sessionId", "");
    }

    public static String getUserLogin(Activity activity) {
        SharedPreferences sharedPreferences = activity
                .getSharedPreferences("shelterPreferences", Context.MODE_PRIVATE);
        return sharedPreferences.getString("userLogin", "");
    }

    public String getRole() {
        return role;
    }

    public void saveSession(Activity activity) {
        SharedPreferences sharedPreferences = activity
                .getSharedPreferences("shelterPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("sessionId", sessionId);
        editor.apply();
    }

    public void saveUserLogin(Activity activity, String userLogin) {
        SharedPreferences sharedPreferences = activity
                .getSharedPreferences("shelterPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userLogin", userLogin);
        editor.apply();
    }


}
