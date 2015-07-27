package edu.rosehulman.pickupsports;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;


public class ProfileActivity extends Activity {
    private TabHost mTabHost;
    private TabWidget mTabWidget;
    private FrameLayout mFrameLayoutTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        mTabHost = (TabHost) findViewById(R.id.tabHost);
        mTabHost.setup();

        mTabWidget = mTabHost.getTabWidget();
        mFrameLayoutTabs = mTabHost.getTabContentView();

        TextView[] originalTextViews = new TextView[mTabWidget.getTabCount()];
        for (int i = 0; i < mTabWidget.getTabCount(); i++){
            originalTextViews[i] = (TextView) mTabWidget.getChildTabViewAt(i);
        }
        mTabWidget.removeAllViews();

        for (int i = 0; i < mFrameLayoutTabs.getChildCount(); i++){
            mFrameLayoutTabs.getChildAt(i).setVisibility(View.GONE);
        }

        for (int i = 0; i < originalTextViews.length; i++){
            final TextView tabWidgetTextView = originalTextViews[i];
            final View tabContentView = mFrameLayoutTabs.getChildAt(i);
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec((String) tabWidgetTextView.getTag());
            tabSpec.setContent(new TabHost.TabContentFactory() {
                @Override
                public View createTabContent(String tag) {
                    return tabContentView;
                }
            });
            if (tabWidgetTextView.getBackground() == null){
                tabSpec.setIndicator(tabWidgetTextView.getText());
            } else {
                tabSpec.setIndicator(tabWidgetTextView.getText(), tabWidgetTextView.getBackground());
            }
            mTabHost.addTab(tabSpec);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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
