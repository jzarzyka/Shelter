package zarzyka.jagoda.shelter.admin.details;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import zarzyka.jagoda.shelter.R;

public class DetailsActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final int fragmentType = getIntent().getIntExtra("fragmentType", 100);
        final String itemId = getIntent().getStringExtra("itemId");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (fragmentType) {
            case 0:
                fragmentTransaction.add(R.id.fragment_container, ShelterFragment.newInstance(itemId));
                break;

            case 1:
                fragmentTransaction.add(R.id.fragment_container, UserFragment.newInstance(itemId));
                break;

            default:
                break;
        }

        fragmentTransaction.commit();
    }
}
