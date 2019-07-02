package zarzyka.jagoda.shelter.lists;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import zarzyka.jagoda.shelter.R;
import zarzyka.jagoda.shelter.admin.details.DetailsActivity;
import zarzyka.jagoda.shelter.models.Animal;
import zarzyka.jagoda.shelter.models.ResponseItem;
import zarzyka.jagoda.shelter.models.Shelter;
import zarzyka.jagoda.shelter.models.User;
import zarzyka.jagoda.shelter.worker.DetailsAnimal;

public class CustomListAdapter extends ArrayAdapter<ResponseItem> {

    private Context context;
    private List<ResponseItem> data;

    public CustomListAdapter(@NonNull Context context, ArrayList<ResponseItem> data) {
        super(context, 0, data);

        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) { listItem = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false); }

        ResponseItem item = data.get(position);

        TextView name = listItem.findViewById(R.id.text);
        name.setText(item.toString());

        name.setOnClickListener(view -> {
            if (item instanceof Shelter) {
                Intent details = new Intent(context, DetailsActivity.class);
                details.putExtra("fragmentType", 0);
                details.putExtra("itemId", ((Shelter) item).getId());
                context.startActivity(details);

            } else if (item instanceof User) {
                Intent details = new Intent(context, DetailsActivity.class);
                details.putExtra("fragmentType", 1);
                details.putExtra("itemId", ((User) item).getLogin());
                context.startActivity(details);

            } else if (item instanceof Animal) {
                Intent details = new Intent(context, DetailsAnimal.class);
                details.putExtra("animalId", ((Animal) item).getId());
                details.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(details);
            }
        });

        return listItem;
    }
}
