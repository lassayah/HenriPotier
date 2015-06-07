package projet.lassayah.com.henripotier;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import projet.lassayah.adapter.BagAdapter;
import projet.lassayah.objet.MyBag;

/**
 * Created by LaMarseillaise on 6/1/2015.
 */
public class MyBagActivityFragment extends Fragment {

    public MyBagActivityFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bag, container, false);
        ListView bagList = (ListView) v.findViewById(R.id.bagList);
        // layout quand il  n'y a pas de livres sauvegardés
        bagList.setEmptyView(v.findViewById(R.id.noItems));
        bagList.setAdapter(new BagAdapter(getActivity(), (TextView)v.findViewById(R.id.price)));
        if (MyBag.getInstance().getBooks().size() != 0)
        {
            MyBag.getInstance().findBestPrice(null, (TextView) v.findViewById(R.id.price));
        }
        return v;
    }

}
