package zarzyka.jagoda.shelter.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;
import org.json.JSONArray;
import zarzyka.jagoda.shelter.R;
import zarzyka.jagoda.shelter.admin.add.EditActivity;
import zarzyka.jagoda.shelter.lists.CustomListAdapter;
import zarzyka.jagoda.shelter.models.LoggedInUser;
import zarzyka.jagoda.shelter.models.ResponseItem;
import zarzyka.jagoda.shelter.models.Shelter;
import zarzyka.jagoda.shelter.models.User;
import zarzyka.jagoda.shelter.requests.shelter.GetAllShelters;
import zarzyka.jagoda.shelter.requests.user.GetAllUsers;

class PlaceholderFragment extends Fragment {

    private CustomListAdapter adapter;
    private FloatingActionButton add;
    private Gson gson = new Gson();
    private int index = 100;
    private ArrayList<ResponseItem> listItems = new ArrayList<>();
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = getArguments() != null ? getArguments().getInt("section_number") : 100;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list, container, false);
        listView = root.findViewById(R.id.list);
        add = root.findViewById(R.id.add);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        add.setOnClickListener(view -> {
            switch (index) {
                case 0:
                    Intent editShelter = new Intent(getContext(), EditActivity.class);
                    editShelter.putExtra("fragmentType", 0);
                    startActivity(editShelter);
                    break;
                case 1:
                    Intent editUser = new Intent(getContext(), EditActivity.class);
                    editUser.putExtra("fragmentType", 1);
                    startActivity(editUser);
                    break;
                default:
                    break;
            }
        });

        adapter = new CustomListAdapter(Objects.requireNonNull(getContext()), listItems);
        listView.setAdapter(adapter);

        switch (index) {
            case 0:
                getShelters();
                break;
            case 1:
                getUsers();
                break;
            default:
                break;
        }
    }

    private void getShelters() {
        listItems.clear();

        Response.Listener<JSONArray> responseListener = response -> {
            Type listType = new TypeToken<ArrayList<Shelter>>() {}.getType();
            ArrayList<ResponseItem> list = gson.fromJson(response.toString(), listType);
            listItems.addAll(list);
            adapter.notifyDataSetChanged();
        };

        Response.ErrorListener responseErrorListener = error -> Toast.makeText(
                getContext(),
                getContext().getString(R.string.error_happened),
                Toast.LENGTH_SHORT
        ).show();

        GetAllShelters getSheltersRequest = new GetAllShelters(LoggedInUser.getSessionId(getActivity()),
                responseListener,
                responseErrorListener
        );
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(getSheltersRequest);
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

        GetAllUsers getUsersRequest = new GetAllUsers(LoggedInUser.getSessionId(getActivity()),
                responseListener,
                responseErrorListener
        );
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(getUsersRequest);
    }

    static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("section_number", index);
        fragment.setArguments(bundle);
        return fragment;
    }
}