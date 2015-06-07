package projet.lassayah.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

import projet.lassayah.com.henripotier.R;
import projet.lassayah.objet.Book;
import projet.lassayah.objet.MyBag;

/**
 * Created by LaMarseillaise on 5/28/2015.
 */
public class BookAdapter extends BaseAdapter {

    static class ViewHolder {
        public TextView titleBook;
        public String url;
        public ImageView coverBook;
        public Button panier;
        public TextView priceBook;
    }

    private List<Book> bookList;
    private LayoutInflater mInflater;
    private Context context;

    public BookAdapter()
    {
        super();
    }

    public BookAdapter(Context context, List<Book> bookList)
    {
        super();
        this.context = context;
        this.bookList = bookList;
    }

    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        // Si la vue n'est pas recyclée
        if (convertView == null)
        {
            // On récupère le layout
            mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView  = mInflater.inflate(R.layout.book_item, null);
            holder = new ViewHolder();
            // On place les widgets de notre layout dans le holder
            holder.titleBook = (TextView)convertView.findViewById(R.id.book_title);
            holder.coverBook = (ImageView)convertView.findViewById(R.id.cover);
            holder.panier = (Button)convertView.findViewById(R.id.panier);
            holder.priceBook = (TextView)convertView.findViewById(R.id.bookPrice);
            // puis on insère le holder en tant que tag dans le layout
            convertView.setTag(holder);
        }
        else {
            // Si on recycle la vue, on récupère son holder en tag
            holder = (ViewHolder)convertView.getTag();
        }
        // Dans tous les cas, on récupère le livre concerné
        final Book b = (Book)getItem(position);
        // Si cet élément existe vraiment…
        if(b != null) {
            // On place dans le holder les informations sur le livre
            holder.titleBook.setText(b.getTitle());
            holder.priceBook.setText(String.valueOf(b.getPrice()) + " euros");
            holder.url = b.getImage();
            if (b.getBitmap() != null)
                holder.coverBook.setImageBitmap(b.getBitmap());
            holder.panier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyBag bag  = MyBag.getInstance();
                    bag.addBook(b);
                    Toast bookAdded = Toast.makeText(context, b.getTitle() + " ajouté au panier", Toast.LENGTH_SHORT);
                    bookAdded.show();
                }
            });
        }
        return convertView;
    }

}
