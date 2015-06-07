package projet.lassayah.objet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;

import projet.lassayah.adapter.BookAdapter;
import projet.lassayah.utils.CacheHenriPotier;


/**
 * Created by LaMarseillaise on 5/28/2015.
 */
public class Book {

    private String title;
    private String image;
    private Bitmap bitmap;
    private String isbn;
    private double price;
    private BookAdapter adapter;

    public Book()
    {
        adapter = null;
    }


    public Book(String title, String image, String isbn, double price)
    {
        this.title = title;
        this.image = image;
        this.isbn = isbn;
        this.price = price;
        adapter = null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    // permet de télécharger les couvertures depuis les liens donnés dans le web service
    public void loadImage(BookAdapter adapter) {
        this.adapter = adapter;
        if (image != null && !image.equals("")) {
            new DownloadAsyncTask().execute(image);
        }
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // telechargement des covers
    private class DownloadAsyncTask extends AsyncTask<String, String, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
            //load image directly
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(new java.net.URL(image).openStream());
                return bitmap;
            } catch (IOException e) {
                // TODO: handle exception
                e.printStackTrace();
                return null;

            }


        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            if (result != null) {
                bitmap = result;

            }
            if (adapter != null) {
                adapter.notifyDataSetChanged();
                CacheHenriPotier.getInstance().addBitmapToMemoryCache(getIsbn(), bitmap);

            }
        }
    }


}
