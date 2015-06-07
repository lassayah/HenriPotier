package projet.lassayah.objet;

import android.text.TextUtils;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by LaMarseillaise on 6/1/2015.
 */
public class MyBag {

    public static MyBag myBagInstance = null;
    private double price;
    private ArrayList<Book> books;

    private MyBag()
    {
        setBooks(new ArrayList<Book>());
        setPrice(0);
    }

    public static MyBag getInstance()
    {
        if (myBagInstance == null)
            myBagInstance = new MyBag();
        return myBagInstance;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public void addBook(Book book)
    {
        books.add(book);
    }

    public double getTotalPrice()
    {
        double total = 0;
        if (books.size() == 0)
            return total;
        else
        {
            for (Book b : books)
                total += b.getPrice();
            return total;
        }
    }

    public void deleteBook(int index)
    {
        books.remove(index);
    }


    // permet d'appeler le web service donnant la meilleure offre commerciale et définit le bon prix à afficher
    public void findBestPrice(RequestParams params, final TextView textView)
    {
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://henri-potier.xebia.fr/books/";
        url += TextUtils.join(",", books);
        url += "/commercialOffers";
        client.get(url, params, new JsonHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(int i, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    JSONArray offers = response.getJSONArray("offers");
                    int percentage = 0;
                    int minus = 0;
                    int slice = 0;
                    int sliceValue = 0;
                    for (int j = 0; j < offers.length(); j++)
                    {
                        JSONObject offer = offers.getJSONObject(j);
                        int value = offer.getInt("value");
                        String type = offer.getString("type");
                        if (type == "percentage")
                            percentage = value;
                        else if (type == "minus")
                            minus = value;
                        else if (type == "slice")
                        {
                            slice = value;
                            sliceValue = offer.getInt("sliceValue");
                        }
                        else
                            System.out.println("unknown parameter");

                    }

                    double totalPercentage = getTotalPrice() - (getTotalPrice() * percentage / 100);
                    double totalMinus = getTotalPrice() - minus;
                    double totalSlice = getTotalPrice() - Math.floor(getTotalPrice() / sliceValue == 0 ? 1 : sliceValue) * slice;

                    price = Math.min(totalPercentage, Math.min(totalMinus, totalSlice));
                    System.out.println("price : " + price);
                    textView.setText("Total : " + String.valueOf(price) + " euros");

                } catch (JSONException e) {
                    // TODO Auto-generated catch block

                    e.printStackTrace();

                }
            }

            @Override
            public void onSuccess(int i, Header[] headers, JSONArray response) {

            }


        });
    }
}
