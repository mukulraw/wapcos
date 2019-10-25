package com.nsez.wapcos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nsez.wapcos.singleComplaintPOJO.Data;
import com.nsez.wapcos.singleComplaintPOJO.singleComplaintBean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ComplaintDetailsVendors extends AppCompatActivity {
    Toolbar toolbar;
    TextView comment;

    TextView name, category, date, ack, closure, status, handled, complaint, vname, category1, email, phone, altemail, company, address , acknowledge , open;
    LinearLayout attachment;
    String cid, title , vid;
    ProgressBar progress;

    Button addclosure;

    File f1;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_details_vendors);

        cid = getIntent().getStringExtra("cid");

        title = getIntent().getStringExtra("title");

        comment = findViewById(R.id.comment);
        toolbar = findViewById(R.id.toolbar);
        name = findViewById(R.id.name);
        category = findViewById(R.id.category);
        date = findViewById(R.id.date);
        ack = findViewById(R.id.ack);
        closure = findViewById(R.id.closure);
        status = findViewById(R.id.status);
        complaint = findViewById(R.id.complaint);
        vname = findViewById(R.id.vname);
        handled = findViewById(R.id.handled);
        category1 = findViewById(R.id.category1);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        altemail = findViewById(R.id.altemail);
        acknowledge = findViewById(R.id.acknowledge);
        company = findViewById(R.id.company);
        address = findViewById(R.id.address);
        attachment = findViewById(R.id.attachment);
        progress = findViewById(R.id.progress);
        open = findViewById(R.id.open);
        addclosure = findViewById(R.id.addclosure);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.ic_left_arrow);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(title);

        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ComplaintDetailsVendors.this, Comments2.class);
                intent.putExtra("cid" , cid);
                intent.putExtra("vid" , vid);
                intent.putExtra("title" , title);
                startActivity(intent);

            }
        });



        loadData();


        addclosure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(ComplaintDetailsVendors.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.date_dialog);
                dialog.show();


                final DatePicker picker = dialog.findViewById(R.id.date);
                Button ok = dialog.findViewById(R.id.ok);

                long now = System.currentTimeMillis() - 1000;
                picker.setMinDate(now);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int year = picker.getYear();
                        int month = picker.getMonth();
                        int day = picker.getDayOfMonth();

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day);

                        SimpleDateFormat format = new SimpleDateFormat("d-M-Y");
                        String strDate = format.format(calendar.getTime());

                        Log.d("dddd" , strDate);

                        dialog.dismiss();

                        progress.setVisibility(View.VISIBLE);

                        Bean b = (Bean) getApplicationContext();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.baseurl)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                        Call<String> call = cr.addClosure(strDate , cid);

                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {


                                progress.setVisibility(View.GONE);
                                Toast.makeText(ComplaintDetailsVendors.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();

                                //finish();
                                loadData();


                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                progress.setVisibility(View.GONE);
                            }
                        });





                    }
                });

            }
        });


        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (open.getText().toString().equals("OPEN"))
                {

                    progress.setVisibility(View.VISIBLE);

                    Bean b = (Bean) getApplicationContext();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.baseurl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                    Call<String> call = cr.changeStatus("Open" , cid);

                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {


                            progress.setVisibility(View.GONE);
                            Toast.makeText(ComplaintDetailsVendors.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();

                            finish();


                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });

                }
                else
                {


                    final CharSequence[] items = {"Take Photo from Camera",
                            "Choose from Gallery",
                            "Cancel"};
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ComplaintDetailsVendors.this);
                    builder.setTitle("Add Photo!");
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            if (items[item].equals("Take Photo from Camera")) {
                                final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Folder/";
                                File newdir = new File(dir);
                                try {
                                    newdir.mkdirs();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                String file = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".jpg";


                                f1 = new File(file);
                                try {
                                    f1.createNewFile();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                uri = FileProvider.getUriForFile(Objects.requireNonNull(ComplaintDetailsVendors.this), BuildConfig.APPLICATION_ID + ".provider", f1);

                                Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                getpic.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                startActivityForResult(getpic, 1);
                                dialog.dismiss();
                            } else if (items[item].equals("Choose from Gallery")) {
                                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(intent, 2);
                                dialog.dismiss();
                            } else if (items[item].equals("Cancel")) {
                                dialog.dismiss();
                            }
                        }
                    });
                    builder.show();


                }

            }
        });


        acknowledge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                progress.setVisibility(View.VISIBLE);

                Bean b = (Bean) getApplicationContext();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(b.baseurl)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                Call<String> call = cr.changeStatus("Acknowledge" , cid);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {


                        progress.setVisibility(View.GONE);
                        Toast.makeText(ComplaintDetailsVendors.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();

                        finish();


                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });


            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            uri = data.getData();

            Log.d("uri" , String.valueOf(uri));

            String ypath = getPath(ComplaintDetailsVendors.this , uri);
            assert ypath != null;
            f1 = new File(ypath);

            Log.d("path" , ypath);

            MultipartBody.Part body = null;

            try {

                RequestBody reqFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), f1);
                body = MultipartBody.Part.createFormData("file_close", f1.getName(), reqFile1);


            } catch (Exception e1) {
                e1.printStackTrace();
            }



            progress.setVisibility(View.VISIBLE);

            Bean b = (Bean) getApplicationContext();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(b.baseurl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

            Call<String> call = cr.close("Close" , cid , body);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    progress.setVisibility(View.GONE);
                    Toast.makeText(ComplaintDetailsVendors.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();

                    finish();

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                    Log.d("failure" , t.toString());
                }
            });



        } else if (requestCode == 1 && resultCode == RESULT_OK) {

            Log.d("uri" , String.valueOf(uri));

            MultipartBody.Part body = null;

            try {

                RequestBody reqFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), f1);
                body = MultipartBody.Part.createFormData("file_close", f1.getName(), reqFile1);


            } catch (Exception e1) {
                e1.printStackTrace();
            }

            progress.setVisibility(View.VISIBLE);

            Bean b = (Bean) getApplicationContext();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(b.baseurl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

            Call<String> call = cr.close("Close" , cid , body);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    progress.setVisibility(View.GONE);
                    Toast.makeText(ComplaintDetailsVendors.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();

                    finish();

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                    Log.d("failure" , t.toString());
                }
            });

        }


    }

    private static Bitmap decodeUriToBitmap(Context mContext, Uri sendUri) {
        Bitmap getBitmap = null;
        try {
            InputStream image_stream;
            try {
                image_stream = mContext.getContentResolver().openInputStream(sendUri);
                getBitmap = BitmapFactory.decodeStream(image_stream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBitmap;
    }


    private static String getPath(final Context context, final Uri uri) {

        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {

        final String column = "_data";
        final String[] projection = {
                column
        };
        try (Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                null)) {
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        }
        return null;
    }

    void loadData()
    {
        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<singleComplaintBean> call = cr.getComplainById1(cid);

        call.enqueue(new Callback<singleComplaintBean>() {
            @Override
            public void onResponse(Call<singleComplaintBean> call, Response<singleComplaintBean> response) {

                if (response.body().getStatus().equals("1")) {

                    final Data item = response.body().getData();

                    name.setText(item.getName());
                    category.setText(item.getCategory());
                    date.setText(item.getCreatedDate());
                    ack.setText(item.getAkdDate());
                    closure.setText(item.getExpClDate());
                    status.setText(item.getStatus());
                    handled.setText(item.getHandled());
                    complaint.setText(item.getComplain());
                    vname.setText(item.getVname());
                    category1.setText(item.getCategory());
                    email.setText(item.getEmail());
                    phone.setText(item.getPhone());
                    altemail.setText(item.getAlternateemail());

                    company.setText(item.getCompany());
                    address.setText(item.getAddress());

                    vid = item.getVid();

                    attachment.removeAllViews();

                    for (int i = 0; i < item.getImages().size(); i++) {

                        View view = View.inflate(getApplicationContext(), R.layout.attachment, null);
                        TextView tit = view.findViewById(R.id.title);

                        tit.setText(item.getImages().get(i).getImage());


                        final int finalI = i;
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                int downloadId = PRDownloader.download("http://www.nsezwapcos.com/admin/upload/" + item.getImages().get(finalI).getImage(), Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() , item.getImages().get(finalI).getImage())
                                        .build()
                                        .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                                            @Override
                                            public void onStartOrResume() {

                                            }
                                        })
                                        .setOnPauseListener(new OnPauseListener() {
                                            @Override
                                            public void onPause() {

                                            }
                                        })
                                        .setOnCancelListener(new OnCancelListener() {
                                            @Override
                                            public void onCancel() {

                                            }
                                        })
                                        .setOnProgressListener(new OnProgressListener() {
                                            @Override
                                            public void onProgress(Progress progress) {

                                                Log.d("progress" , String.valueOf(progress.totalBytes));

                                            }
                                        })
                                        .start(new OnDownloadListener() {
                                            @Override
                                            public void onDownloadComplete() {

                                                Log.d("completed" , "completed");
                                                Toast.makeText(ComplaintDetailsVendors.this, "Successfully downloaded in Downloads", Toast.LENGTH_SHORT).show();

                                            }

                                            @Override
                                            public void onError(Error error) {

                                                Log.d("error" , error.getConnectionException().toString());

                                            }

                                        });

                            }
                        });

                        attachment.addView(view);

                    }

                    if (item.getAkdDate().equals("")) {
                        acknowledge.setVisibility(View.VISIBLE);
                    } else {
                        acknowledge.setVisibility(View.GONE);
                    }


                    if (item.getExpClDate().equals("")) {
                        closure.setVisibility(View.GONE);
                        addclosure.setVisibility(View.VISIBLE);
                    } else {
                        closure.setVisibility(View.VISIBLE);
                        addclosure.setVisibility(View.GONE);
                    }


                    if (item.getStatus().equals("Open") || item.getStatus().equals("ReOpen"))
                    {
                        open.setText("CLOSE");
                    }
                    else
                    {
                        open.setText("OPEN");
                    }


                }


                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<singleComplaintBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });
    }

}
