package homework.jimho.simpletodo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private List<String> items;
    private ArrayAdapter<String> list_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readItems();

        list_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);

        ListView list_view = (ListView) findViewById(R.id.listView);
        list_view.setAdapter(list_adapter);
        list_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick (AdapterView<?> parent, View view, int position, long id)
            {
                items.remove(position);
                list_adapter.notifyDataSetChanged();
                writeItems();

                return true;
            }

        });
    }

    public void onAddItem (View view)
    {
        EditText input = (EditText) findViewById(R.id.editText);

        String input_value = input.getText().toString();

        // validate input value
        if (input_value == null || "".equals(input_value)) {
            return;
        }

        // update item list with input value
        items.add(input_value);
        list_adapter.notifyDataSetChanged();
        writeItems();

        // reset input
        input.setText("");
    }

    private void readItems ()
    {
        File dir = getFilesDir();
        File todo_file = new File(dir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todo_file));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems ()
    {
        File dir = getFilesDir();
        File todo_file = new File(dir, "todo.txt");
        try {
            FileUtils.writeLines(todo_file, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
