package com.emergence.study_app.Adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.Activity.OpenPdf;
import com.emergence.study_app.newTech.retrofit.model.Notes.DataItem;
import com.example.study_app.R;

import java.util.List;

public class Notes_Adapter extends RecyclerView.Adapter<Notes_Adapter.Notes_ViewHolder> {
    List<DataItem> notes;
    Context context;

    public Notes_Adapter(List<DataItem> notes, Context context) {
        this.notes = notes;
        this.context = context;
    }

    @NonNull
    @Override
    public Notes_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.item_notes,parent,false);
        return new Notes_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Notes_ViewHolder holder, int position) {
        holder.name.setText(notes.get(position).getTitle());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
//                browserIntent.setDataAndType(Uri.parse("http://ayubi.emergenceinfotech.in/assets/upload/notes/"+notes.get(position).getAttachment()), "application/pdf");
//                Intent chooser = Intent.createChooser(browserIntent, "Select to Open");
//                chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // optional
//                context.startActivity(chooser);

                Intent intent=new Intent(context, OpenPdf.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("url",notes.get(position).getAttachment());
                context.startActivity(intent);

//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse("http://galaxy.ayubiclasses.in/API/openfile.php?file=20211459142485.pdf"));
//                context.startActivity(i);

//                URL url = null;
//                try {
//                    url = new URL("http://galaxy.ayubiclasses.in/API/openfile.php?file=20211459142485.pdf");
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                }
//                HttpURLConnection con = null;
//                try {
//                    con = (HttpURLConnection) url.openConnection();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    con.setRequestMethod("GET");
//                } catch (ProtocolException e) {
//                    e.printStackTrace();
//                }
//
//            }




            }
        });
        holder.iv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notes.get(position).getDownload().equalsIgnoreCase("on")){
                    String titels= notes.get(position).getTitle()+String.valueOf(System.currentTimeMillis());
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse("https://ayubiclasses.in/ayubi/assets/upload/notes/"+notes.get(position).getAttachment()));
                    request.setTitle(titels);
                    Toast.makeText(context, "Downloading...", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    }
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,titels+".pdf");
                    DownloadManager  manager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
                    request.setMimeType("application/pdf");
                    request.allowScanningByMediaScanner();
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE|DownloadManager.Request.NETWORK_WIFI);
                    manager.enqueue(request);
                }else {
                    Toast.makeText(context, "You can not download this Note \n Please contact to Admin", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class Notes_ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        LinearLayout layout;
        ImageView iv_download;
        public Notes_ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_download=itemView.findViewById(R.id.iv_download);
            name=itemView.findViewById(R.id.l_name);
            layout=itemView.findViewById(R.id.lyt_not);
        }
    }
}
