package am.dx.varsityspecials.www.varsityspecials;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import static am.dx.varsityspecials.www.varsityspecials.R.id;
import static am.dx.varsityspecials.www.varsityspecials.R.layout;

/**
 * Created by dylanmatthews on 2017/08/11.
 */

public class blod_array extends ArrayAdapter<Card>  {
        private static final String TAG = "CardArrayAdapter";
        private List<Card> cardList = new ArrayList<Card>();
     private  StorageReference storageRef = FirebaseStorage.getInstance().getReference();


    static class CardViewHolder {
            TextView line1;
            TextView line2;
            ImageView im;
        String imgUrl;


        }

        public blod_array(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public void add(Card object) {
            cardList.add(object);
            super.add(object);
        }

        @Override
        public int getCount() {
            return this.cardList.size();
        }

        @Override
        public Card getItem(int index) {
            return this.cardList.get(index);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
             View row = convertView;
            final CardViewHolder viewHolder;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(layout.blow_row, parent, false);
                viewHolder = new CardViewHolder();
                viewHolder.im = (ImageView) row.findViewById(id.post_image);
                viewHolder.line1 = (TextView) row.findViewById(id.post_title);
                viewHolder.line2 = (TextView) row.findViewById(id.post_des);

                row.setTag(viewHolder);
            } else {
                viewHolder = (CardViewHolder)row.getTag();
            }



                    viewHolder.line1.post(new Runnable() {
                        @Override
                        public void run() {
                            Card card = getItem(position);
                            viewHolder.line1.setText(card.getLine1());
                        }
                    });


                    viewHolder.line2.post(new Runnable() {
                        @Override
                        public void run() {
                            Card card = getItem(position);
                            viewHolder.line2.setText(card.getLine2());
                        }
                    });
                      Card card = getItem(position);
                    viewHolder.imgUrl= card.getImage();
                    //  Picasso picasso = Picasso.with(getContext());
                    // picasso.load(viewHolder.imgUrl).stableKey(viewHolder.imgUrl).tag(viewHolder).into(viewHolder.im);
                    // Picasso.with(getContext()).load( viewHolder.imgUrl).into(viewHolder.im);
                    // Log.i("shit",storageRef.child(viewHolder.imgUrl).toString() );
                    String ref = "blog/" + viewHolder.imgUrl;
                    storageRef.child(ref).getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            // Use the bytes to display the image
                          final  Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                            viewHolder.im.post(new Runnable() {
                                @Override
                                public void run() {
                                    viewHolder.im.setImageBitmap(bitmap);
                                }
                            }) ;


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.i("shit", exception.getMessage());
                        }
                    });

                    //URI uri = new URI("android.resource;//am.dx.varsityspecials.www.varsityspecials/" + R.mipmap.add_btn);
                    //viewHolder.im.setImageURI(Uri.parse("android.resource;//am.dx.varsityspecials.www.varsityspecials/" + mipmap.add_btn));
                    // AQuery aq =  new AQuery(etContext())).id(exploreViewHolder.getvProfilePic()).image(item.getUserProfilePicUrl().trim(), true, true, device_width, R.drawable.profile_background, aquery.getCachedImage(R.drawable.profile_background),0);


                    //r

            return row;
        }

        public Bitmap decodeToBitmap(byte[] decodedByte) {
            return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        }

    }
