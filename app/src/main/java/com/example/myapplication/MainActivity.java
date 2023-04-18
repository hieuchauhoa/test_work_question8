package com.example.myapplication;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> items = new ArrayList<>();
    private int currentItems = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        listView = findViewById(R.id.list_view);
        Button loadMoreButton = findViewById(R.id.btn_load_more);

        // Add items to the list
        for(int i = 1; i <= currentItems; i++) {
            items.add("Item " + i);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        // Handle click events on list items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
            }
        });

        // Handle refresh event
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshListView();
            }
        });

        // Handle load more button click
        loadMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMoreItems();
            }
        });
    }

    // Refresh the list view
    private void refreshListView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                items.clear();
                currentItems = 10;
                for(int i = 1; i <= currentItems; i++) {
                    items.add("Item " + i);
                }
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    // Load more items to the list view
    private void loadMoreItems() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i = currentItems + 1; i <= currentItems + 10; i++) {
                    items.add("Item " + i);
                }
                currentItems += 10;
                adapter.notifyDataSetChanged();
            }
        }, 2000);
    }
}
