package zarzyka.jagoda.shelter.admin.add;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import zarzyka.jagoda.shelter.R;
import zarzyka.jagoda.shelter.models.ResponseItem;
import zarzyka.jagoda.shelter.models.Shelter;
import zarzyka.jagoda.shelter.models.User;

public class EditActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        final int fragmentType = getIntent().getIntExtra("fragmentType", 100);
        final ResponseItem item = (ResponseItem) getIntent().getSerializableExtra("item");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (fragmentType) {
            case 0:
                fragmentTransaction.add(R.id.fragment_container, ShelterEdit.newInstance((Shelter) item));
                break;

            case 1:
                fragmentTransaction.add(R.id.fragment_container, UserEdit.newInstance((User) item));
                break;

            default:
                break;
        }

        fragmentTransaction.commit();
    }
}
