package com.weebly.httplexiconindustries.ru.HelperPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.weebly.httplexiconindustries.ru.ContentParsing.BookResult;
import java.util.LinkedList;

import ActivityPackages.R;

/**
 * Created by Scotty on 12/3/16.
 */

    public class GridAdapter extends BaseAdapter {
        private Context mContext;
        private LinkedList<BookResult> books;
        final int mCount;
        /**
         * Default constructor
         * @param c: specifies the Activity in which the grid is being created
         * @param _books: the list of books in which the grid will use to fill the grid elements
         */
        public GridAdapter(Context c, LinkedList<BookResult> _books ) {
            mContext = c;
            books = _books;
            mCount = books.size();

           /*
            mImageItems = new ArrayList<String>(mCount);
            // for small size of items it's ok to do it here, sync way
            for (String item : items) {
                // get separate string parts, divided by ,
                final String[] parts = item.split(",");
                // remove spaces from parts
                for (String part : parts) {
                    part.replace(" ", "");
                    mImageItems.add(part);
                }
            }
            */
        }

        /**
         * used to set the image and text using the data stored in the booklist object
         * book list object consists of two attributes String imgURL && name. picasso library used
         * to parse Image from the web
         * @param position used to determine the specific grid item
         * @param convertView
         * @param parent parent group being the entire grid
         * @return returns a view which is the grid element once its
         *         elements have been added (name & picasso image from the imgURL)!
         */
        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            View grid;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                grid = inflater.inflate(R.layout.gird_single, null);
            }
            else{ grid = (View) convertView; }
            TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);

            textView.setText(books.get(position).getName());
            System.out.println("Position: " + position  + " " + books.get(position).getName());
            Picasso.with(mContext).load(books.get(position).getImgURL()).into(imageView);
            return grid;
        }

        /**
         * required methods below
         */
        @Override
        public int getCount() {
            return mCount;
        }
        @Override
        public Object getItem(int i) {
            return null;
        }
        @Override
        public long getItemId(final int position) {
            return position;
        }


}
