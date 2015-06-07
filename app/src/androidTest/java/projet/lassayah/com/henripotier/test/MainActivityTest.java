package projet.lassayah.com.henripotier.test;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import projet.lassayah.com.henripotier.MainActivity;
import projet.lassayah.com.henripotier.MyBagActivityFragment;
import projet.lassayah.com.henripotier.R;


/**
 * Created by LaMarseillaise on 6/6/2015.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity activity_test;
    private Fragment bag_fragment;
    private TextView test_mybag_textview;

    public MainActivityTest()
    {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        activity_test = getActivity();
        bag_fragment = new MyBagActivityFragment();
    }

    public void testPreConditions() {

        assertNotNull(activity_test);
        assertNotNull(bag_fragment);
    }

    public void startFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = activity_test.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.mainLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getInstrumentation().waitForIdleSync();
    }


    public void testBagFragment()
    {
        startFragment(bag_fragment);
    }

    public void testTextViewTitle_label()
    {
        final String expected = activity_test.getString(R.string.my_bag);
        test_mybag_textview = (TextView)bag_fragment.getView().findViewById(R.id.bagTitle);
        final String actual = test_mybag_textview.getText().toString();
        assertEquals(expected, actual);
    }


    public void tearDown() throws Exception {

    }

 /*
 autres tests à effectuer :
 - tests de reponse des web services et de gestion des exceptions
 - test de deleteBook()
  */
}