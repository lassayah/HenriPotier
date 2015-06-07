package projet.lassayah.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import projet.lassayah.com.henripotier.R;
import projet.lassayah.objet.MyBag;

/**
 * Created by LaMarseillaise on 6/3/2015.
 */
public class BagAdapter extends BaseAdapter {
    static class ViewHolder {
        public TextView bagTitle;
        public Button bagDelete;
    }

    private LayoutInflater mInflater;
    private Context context;
    private TextView price;


    public BagAdapter(Context context, TextView price)
    {
        this.context = context;
        this.price = price;
    }

    @Override
    public int getCount() {
        return MyBag.getInstance().getBooks().size();
    }

    @Override
    public Object getItem(int position) {
        return MyBag.getInstance().getBooks().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        // Si la vue n'est pas recyclée
        if (convertView == null) {
            // On récupère le layout
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.bag_item, null);
            holder = new ViewHolder();
            holder.bagDelete = (Button)convertView.findViewById(R.id.bagDelete);
            holder.bagTitle = (TextView)convertView.findViewById(R.id.bagTitle);
            convertView.setTag(holder);
        }
        else {
            // Si on recycle la vue, on récupère son holder en tag
            holder = (ViewHolder)convertView.getTag();
        }
        String title = MyBag.getInstance().getBooks().get(position).getTitle();
        // permet de supprimer des livres choisis
        holder.bagDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyBag.getInstance().deleteBook(position);
                MyBag.getInstance().findBestPrice(null, price);
                notifyDataSetChanged();
            }
        });
        holder.bagTitle.setText(title);

        return convertView;
    }
}
