package matthewsware.lockstockandbarrell;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static matthewsware.lockstockandbarrell.R.id;

/**
 * Created by dylanmatthews on 2017/10/09.
 */

//this class makes the grid view, downloads the image

public class repairsArray extends ArrayAdapter<repairs> {

    private List<repairs> cardList = new ArrayList<repairs>();
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private Context co;

    public repairsArray(Context context, int resource) {
        super(context, resource);
        co = context;
    }

    static class CardViewHolder {

        ImageView im;
        String imgUrl;
        TextView tv;


    }

    @Override
    public void add(repairs object) {
        cardList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.cardList.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final CardViewHolder viewHolder;
        if (row == null) {
            Log.i("shitTest", "shitTespoiuytt");
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.grid_image, parent, false);

            viewHolder = new CardViewHolder();
            viewHolder.im = (ImageView) row.findViewById(id.grid_item_image);
            //  viewHolder.tv = (TextView) row.findViewById(id.grid_item_text);

            row.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder) row.getTag();
        }

        repairs card = getItem(position);
        viewHolder.imgUrl = card.getImgUrl();
        //downloads image
        //  viewHolder.tv.setText("jdshjds");
        String ref = "repairs/" + viewHolder.imgUrl;
        final File file = new File(co.getFilesDir(), viewHolder.imgUrl);
        File check = (co.getFilesDir());
        boolean imgNotExists = true;
        final File[] imgAr = check.listFiles();

        for (int i = 0; i < imgAr.length; i++) {

            if (viewHolder.imgUrl.equals(imgAr[i].getName())) {

                imgNotExists = false;

                // iv.setImageBitmap(decodeSampledBitmap(imgAr[i].getName()));
                //Toast.makeText(co, "image exists", Toast.LENGTH_SHORT).show();
                // Bitmap bit = BitmapFactory.decodeFile(imgAr[i].getAbsolutePath());
                //  viewHolder.im.setImageBitmap(bit);
                viewHolder.im.setImageURI(Uri.parse(imgAr[i].getAbsolutePath()));
                i = imgAr.length;


            }

        }


        if (imgNotExists) {
            storageRef.child(ref).getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bit = BitmapFactory.decodeFile(file.getAbsolutePath());
                    viewHolder.im.setImageBitmap(bit);
                    Toast.makeText(co, "download image", Toast.LENGTH_SHORT).show();


                }


            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Toast.makeText(co, "Failed to download image", Toast.LENGTH_SHORT).show();
                    Toast.makeText(co, "Failed to download image" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });



        }
        return row;

    }

}
