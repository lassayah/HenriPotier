package projet.lassayah.com.henripotier;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.app.Fragment;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import projet.lassayah.adapter.BookAdapter;
import projet.lassayah.objet.Book;
import projet.lassayah.utils.CacheHenriPotier;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    private List<Book> bs;
    private ListView books;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);

        books = (ListView)v.findViewById(R.id.books);
        bs = new ArrayList<Book>();
        callHttp(null);

        // todo : detach case

        return v;
    }


// fonction qui permet d'appeler le web service de la liste de livres en utilisant la bibliothèque AsyncHttpClient
    public void callHttp(RequestParams params)
    {
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://henri-potier.xebia.fr/books", params, new JsonHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(int i, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray

            }
            // cas de réponse du serveur sans erreur
            @Override
            public void onSuccess(int i, Header[] headers, JSONArray response) {
                try {
                    JSONObject jsonObject;
                    // récupération des valeurs des champs du json
                    for (int j = 0; j < response.length(); j++) {
                        jsonObject = response.getJSONObject(j);
                        Book book = new Book(jsonObject.getString("title"), jsonObject.getString("cover"), jsonObject.getString("isbn"), jsonObject.getDouble("price"));
                        bs.add(book);
                    }
                    BookAdapter bookAdapter = new BookAdapter(getActivity(), bs);
                    books.setAdapter(bookAdapter);
                    // téléchargement de l'image soit par web service, soit directement depuis le cache
                    for (Book b : bs)
                    {
                        if (CacheHenriPotier.getInstance().getBitmapFromMemCache(b.getIsbn()) == null) {
                            b.loadImage(bookAdapter);

                        }
                        else
                            b.setBitmap(CacheHenriPotier.getInstance().getBitmapFromMemCache(b.getIsbn()));

                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block

                    e.printStackTrace();

                }
            }


        });
    }



}
