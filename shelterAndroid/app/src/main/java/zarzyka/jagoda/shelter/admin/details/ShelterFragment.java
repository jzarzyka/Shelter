package zarzyka.jagoda.shelter.admin.details;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONObject;
import zarzyka.jagoda.shelter.R;
import zarzyka.jagoda.shelter.admin.AdminActivity;
import zarzyka.jagoda.shelter.admin.add.EditActivity;
import zarzyka.jagoda.shelter.lists.CustomListAdapter;
import zarzyka.jagoda.shelter.models.LoggedInUser;
import zarzyka.jagoda.shelter.models.ResponseItem;
import zarzyka.jagoda.shelter.models.Shelter;
import zarzyka.jagoda.shelter.models.User;
import zarzyka.jagoda.shelter.requests.shelter.DeleteShelter;
import zarzyka.jagoda.shelter.requests.shelter.GetOneShelter;
import zarzyka.jagoda.shelter.requests.shelter.GetShelterUsers;

public class ShelterFragment extends Fragment {

    private CustomListAdapter adapter;
    private Button edit, delete;
    private Gson gson = new Gson();
    private ArrayList<ResponseItem> listItems = new ArrayList<>();
    private ListView listView;
    private TextView name, vacancy, address;
    private Shelter shelter = null;
    private String shelterId;

    public static ShelterFragment newInstance(String id) {
        ShelterFragment fragment = new ShelterFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        fragment.setArguments(args);

        return fragment;
    }

    public ShelterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shelterId = getArguments() != null ? getArguments().getString("id") : "";
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shelter, container, false);

        name = root.findViewById(R.id.name);
        vacancy = root.findViewById(R.id.vacancy);
        address = root.findViewById(R.id.address);
        edit = root.findViewById(R.id.edit);
        delete = root.findViewById(R.id.delete);
        listView = root.findViewById(R.id.list);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        edit.setOnClickListener(view -> {
            Intent details = new Intent(getContext(), EditActivity.class);
            details.putExtra("fragmentType", 0);
            details.putExtra("item", shelter);
            startActivity(details);
        });

        delete.setOnClickListener(view -> delete());

        adapter = new CustomListAdapter(Objects.requireNonNull(getContext()), listItems);
        listView.setAdapter(adapter);

        getDetails();
        getUsers();
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

        DeleteShelter getShelterRequest = new DeleteShelter(LoggedInUser.getSessionId(getActivity()),
                shelterId,
                responseListener,
                responseErrorListener
        );
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(getShelterRequest);
    }

    private void getDetails() {
        Response.Listener<JSONObject> responseListener = response -> {
            shelter = gson.fromJson(response.toString(), Shelter.class);
            edit.setEnabled(true);

            name.setText(getResources().getString(R.string.name_input, shelter.getName()));
            vacancy.setText(getResources().getString(R.string.vacancy_input, shelter.getAnimalVacancy()));
            address.setText(getResources().getString(R.string.address_input, shelter.getAddress().toString()));
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

    private void getUsers() {
        listItems.clear();

        Response.Listener<JSONArray> responseListener = response -> {
            Type listType = new TypeToken<ArrayList<User>>() {}.getType();
            ArrayList<ResponseItem> list = gson.fromJson(response.toString(), listType);
            listItems.addAll(list);
            adapter.notifyDataSetChanged();
        };

        Response.ErrorListener responseErrorListener = error -> Toast.makeText(
                getContext(),
                getContext().getString(R.string.error_happened),
                Toast.LENGTH_SHORT
        ).show();

        GetShelterUsers getShelterUsersRequest = new GetShelterUsers(LoggedInUser.getSessionId(getActivity()),
                shelterId,
                responseListener,
                responseErrorListener
        );
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(getShelterUsersRequest);
    }
}
