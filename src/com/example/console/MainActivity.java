package com.example.console;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends Activity {

	Thread demoThread;
	PlaceholderFragment placeholderFragment;
	Console console;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    	placeholderFragment = new PlaceholderFragment();
        getFragmentManager().beginTransaction()
                .add(R.id.container, placeholderFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {

		private TextView consoleTextView;

		@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        	consoleTextView = (TextView) rootView.findViewById(R.id.consoleTextView);
        	consoleTextView.setMovementMethod(new ScrollingMovementMethod());
            return rootView;
        }
        
		public TextView getConsoleTextView() {
			return consoleTextView;
		}

    }
    
    @Override
    protected void onStart() {
    	super.onStart();
        console = new Console(placeholderFragment.getConsoleTextView());
    	demoThread = new Thread() {
    		@Override
    		public void run() {
    			for (;;) {
    				try {
						Thread.sleep(1000);
						String line = Long.toString(System.currentTimeMillis());
						console.appendLine(line);
					} catch (InterruptedException e) {
						return;
					}
    			}
    		}
    	};
    	demoThread.start();
    }


 
}
