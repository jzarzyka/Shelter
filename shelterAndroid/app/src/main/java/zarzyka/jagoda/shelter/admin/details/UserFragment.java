package zarzyka.jagoda.shelter.admin.details;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import zarzyka.jagoda.shelter.R;
import zarzyka.jagoda.shelter.admin.AdminActivity;
import zarzyka.jagoda.shelter.admin.add.EditActivity;
import zarzyka.jagoda.shelter.models.LoggedInUser;
import zarzyka.jagoda.shelter.models.Shelter;
import zarzyka.jagoda.shelter.models.User;
import zarzyka.jagoda.shelter.requests.shelter.GetOneShelter;
import zarzyka.jagoda.shelter.requests.user.DeleteUser;
import zarzyka.jagoda.shelter.requests.user.GetOneUser;

public class UserFragment extends Fragment {

    private Button edit, delete;
    private Gson gson = new Gson();
    private TextView name, surname, address, shelterName, shelterVacancy, shelterAddress;
    private User user = null;
    private String userLogin;

    public static UserFragment newInstance(String id) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        fragment.setArguments(args);

        return fragment;
    }

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userLogin = getArguments() != null ? getArguments().getString("id") : "";
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user, container, false);

        name = root.findViewById(R.id.name);
        surname = root.findViewById(R.id.surname);
        address = root.findViewById(R.id.address);
        edit = root.findViewById(R.id.edit);
        delete = root.findViewById(R.id.delete);
        shelterName = root.findViewById(R.id.shelterName);
        shelterVacancy = root.findViewById(R.id.shelterVacancy);
        shelterAddress = root.findViewById(R.id.shelterAddress);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        edit.setOnClickListener(view -> {
            Intent details = new Intent(getContext(), EditActivity.class);
            details.putExtra("fragmentType", 1);
            details.putExtra("item", user);
            startActivity(details);
        });

        delete.setOnClickListener(view -> delete());

        getDetails();
    }

    private void delete() {
        Response.Listener<JSONArray> responseListener = response -> {
            Toast.makeText(
                    getContext(),
                    getContext().getString(R.string.success),
                    Toast.LENGTH_SHORT).show();

            Intent login = new Intent(getContext(), AdminActivity.class);
            startActivity(login);
        };

        Response.ErrorListener responseErrorListener = error -> {
            Intent login = new Intent(getContext(), AdminActivity.class);
            startActivity(login);
        };

        DeleteUser deleteUserRequest = new DeleteUser(LoggedInUser.getSessionId(getActivity()),
                userLogin,
                responseListener,
                responseErrorListener
        );
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(deleteUserRequest);
    }

    private void getDetails() {
        Response.Listener<JSONObject> responseListener = response -> {
            user = gson.fromJson(response.toString(), User.class);

            getShelter(user.getShelterId());

            name.setText(getResources().getString(R.string.name_input, user.getName()));
            surname.setText(getResources().getString(R.string.surname_input, user.getSurname()));
            address.setText(getResources().getString(R.string.address_input, user.getAddress().toString()));
        };

        Response.ErrorListener responseErrorListener = error -> Toast.makeText(
                getContext(),
                getContext().getString(R.string.error_happened),
                Toast.LENGTH_SHORT
        ).show();

        GetOneUser getShelterUsersRequest = new GetOneUser(LoggedInUser.getSessionId(getActivity()),
                userLogin,
                responseListener,
                responseErrorListener
        );
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(getShelterUsersRequest);
    }

    private void getShelter(String shelterId) {
        Response.Listener<JSONObject> responseListener = response -> {
            Shelter shelter = gson.fromJson(response.toString(), Shelter.class);

            shelterName.setText(getResources().getString(R.string.name_input, shelter.getName()));
            shelterVacancy.setText(getResources().getString(R.string.vacancy_input, shelter.getAnimalVacancy()));
            shelterAddress.setText(getResources().getString(R.string.address_input, shelter.getAddress().toString()));
        };

        Response.ErrorListener responseErrorListener = error -> Toast.makeText(
                getContext(),
                getContext().getString(R.string.error_happened),
                Toast.LENGTH_SHORT
        ).show();

        GetOneShelter getShelterUsersRequest = new GetOneShelter(LoggedInUser.getSessionId(getActivity()),
                shelterId,
                responseListener,
                responseErrorListener
        );
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(getShelterUsersRequest);
    }
}
