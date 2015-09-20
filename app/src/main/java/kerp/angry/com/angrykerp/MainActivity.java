package kerp.angry.com.angrykerp;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get screen size
        Point ScreenPixel = new Point();
        getWindowManager().getDefaultDisplay().getSize(ScreenPixel);
        PrefData.screenHeight = ScreenPixel.x;
        PrefData.screenWeight = ScreenPixel.y;
        SharedPreferences CheckScrreenSize = getSharedPreferences("Screensize", 1);
        SharedPreferences.Editor editor = CheckScrreenSize.edit();
        editor.putFloat(PrefData.screenHeightTag, PrefData.screenHeight);
        editor.putFloat(PrefData.screenWeightTag, PrefData.screenWeight);
        editor.commit();
        Log.d("ScreenPixel Pref =", PrefData.screenHeight + "+" + PrefData.screenWeight);

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
