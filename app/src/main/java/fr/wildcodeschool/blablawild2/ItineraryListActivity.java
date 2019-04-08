package fr.wildcodeschool.blablawild2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ItineraryListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary_list);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tripRef = database.getReference("trips");

        TripModel tripModel = getIntent().getParcelableExtra(ItinerarySearchActivity.EXTRA_TRIP);
        this.setTitle(String.format(getString(R.string.departure_to_destination), tripModel.getDeparture(), tripModel.getDestination()));

        ListView listTrip = findViewById(R.id.list_trip);
        final ArrayList<TripModel> tripList = new ArrayList<>();

        tripRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TripModel newTrip = dataSnapshot.getValue(TripModel.class);
                tripList.add(newTrip);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ItineraryListActivity.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });

        tripList.add(new TripModel("Eric", "Cartman", "21/02/2017"));
        tripList.add(new TripModel("Stan", "Marsh", "21/02/2017"));
        tripList.add(new TripModel("Kenny", "Broflovski", "21/02/2017"));
        tripList.add(new TripModel("Kyle", "McCormick", "21/02/2017"));
        tripList.add(new TripModel("Wendy", "Testaburger", "21/02/2017"));

        TripAdapter adapter = new TripAdapter(this, tripList);
        listTrip.setAdapter(adapter);
    }
}
