package com.photo.comicsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.photo.comicsapplication.home.HomeActivity;
import com.photo.comicsapplication.model.CategoriesModel;
import com.photo.comicsapplication.model.ChapterModel;
import com.photo.comicsapplication.model.ComicModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.photo.comicsapplication.Constants.BASE_URL;
import static com.photo.comicsapplication.Constants.CATEGORIES;
import static com.photo.comicsapplication.Constants.CHAPTER;
import static com.photo.comicsapplication.Constants.COMIC;
import static com.photo.comicsapplication.Constants.DATABASE_NAME;
import static com.photo.comicsapplication.Constants.URL_FILE;

public class MainActivity extends AppCompatActivity {
    ProgressDialog pd;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference(DATABASE_NAME);

        // Update database
//        myRef.getRoot().child("comics").setValue(null);
//        new JsonTask().execute("http://128.199.169.10:8080/home/");

        Intent i = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
    }


    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()) {
                pd.dismiss();
            }

            writeDataToFirebase(Constants.CHILD_KIEM_HIEP, result);
            writeDataToFirebase(Constants.CHILD_RANK, result);
            writeDataToFirebase(Constants.CHILD_NGON_TINH, result);

//            Intent i = new Intent(MainActivity.this, HomeActivity.class);
//            startActivity(i);
//            finish();
        }
    }


    private class LoadAllChapterTask extends AsyncTask<String, String, String> {

        String comicId;

        public LoadAllChapterTask(String idComic) {
            this.comicId = idComic;
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            updateChapterComicToFirebase(result, comicId);
        }
    }

    private String replaceStringValidate(String value) {
        if (TextUtils.isEmpty(value)) {
            return value;
        }
        String result = value.replace("{", "").replace("}", "").replace(".", "");
        result = result.replace("#", "").replace("$", "").replace("[", "").replace("]", "");
        return result;
    }


    private void writeDataToFirebase(String theloaitruyen, String result) {
        if (result != null) {
            JSONObject json = null;
            try {
                json = new JSONObject(result);
                JSONArray jsonArray = json.getJSONArray(theloaitruyen);

                for (int i = 0; i < jsonArray.length() - 1; i++) {
                    JSONObject oneObject = jsonArray.getJSONObject(i);
                    String theloai = "", motatruyen = "", total = "", tacgia = "", trangthai = "", danhgia = "", txttentruyen = "", tentruyen = "", urlimage = "";

                    try {
                        theloai = oneObject.getString("the-loai");
                        txttentruyen = oneObject.getString("txt-ten-truyen");
                        tentruyen = oneObject.getString("ten-truyen");
                        urlimage = oneObject.getString("url-image");
                        motatruyen = oneObject.getString("mo-ta-truyen");
                        total = String.valueOf(oneObject.getInt("total"));
                        tacgia = oneObject.getString("tac-gia");
                        trangthai = oneObject.getString("trang-thai");
                        danhgia = oneObject.getString("danh-gia");
                    } catch (Exception e) {
                        Log.d("huonghhh", "writeDataToFirebase: "+ e);
                    }

                    CategoriesModel categoriesModel = new CategoriesModel(theloai, urlimage, motatruyen);



//                    Map<String, Object> childTheLoai = new HashMap<>();
//
//                    childTheLoai.put("TenTheLoai", theloai);
//                    childTheLoai.put("IconLink", urlimage);
//                    childTheLoai.put("TenTruyen", tentruyen);
//                    childTheLoai.put("txtTenTruyen", txttentruyen);
//                    childTheLoai.put("MoTa", motatruyen);
//                    childTheLoai.put("Total", total);
//                    childTheLoai.put("TacGia", tacgia);
//                    childTheLoai.put("TrangThai", trangthai);
//                    childTheLoai.put("DanhGia", danhgia);

                    myRef.child(CATEGORIES).child(replaceStringValidate(theloai)).setValue(categoriesModel.toMap());
//                    myRef.child("theloai").child(theloaitruyen).child(replaceStringValidate(tentruyen)).updateChildren(childTheLoai);

                    ComicModel comicModel =
                            new ComicModel(txttentruyen, tentruyen, theloai, motatruyen, urlimage, total, tacgia, trangthai, danhgia);

                    myRef.child(COMIC).child(txttentruyen.trim()).setValue(comicModel.toMap());

                    new LoadAllChapterTask(txttentruyen).execute(BASE_URL + "load-all-chuong/" + txttentruyen.trim()); // ex: bach-ho-tinh-quan


                }


            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("huonghhh", "writeDataToFirebasewqwqw: "+ e);
            }

        }
    }

    private void updateChapterComicToFirebase(String truyen, String comicId) {
        if (truyen != null) {
            JSONObject json = null;
            try {
                json = new JSONObject(truyen);
                JSONArray jsonArray = json.getJSONArray("all-chuong-theo-truyen");

                for (int i = 0; i < jsonArray.length() - 1; i++) {
                    JSONObject oneObject = jsonArray.getJSONObject(i);
                    String  txttenchuong = "", tenchuong = "", tentruyen = "";

                    try {
                        txttenchuong = oneObject.getString("txt-ten-chuong");
                        tenchuong = oneObject.getString("ten-chuong");
                        tentruyen = oneObject.getString("ten-truyen");
                    } catch (Exception e) {
                        Log.d("huonghhh", "writeDataToFirebase: "+ e);
                    }

//                    Map<String, Object> childChuong = new HashMap<>();
//
//                    childChuong.put("TenChuong", tenchuong);
//                    childChuong.put("TenTruyen", tentruyen);
//                    childChuong.put("txtTenChuong", txttenchuong);

                    ChapterModel chapterModel = new ChapterModel(URL_FILE, "description", tenchuong, getIntChapter(txttenchuong.trim()));

                    myRef.child(CHAPTER).child(comicId).child(txttenchuong.trim()).setValue(chapterModel.toMap());
//                    myRef.child("theloai").child(theloaitruyen).child(replaceStringValidate(tentruyen)).updateChildren(childTheLoai);
                }


            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("huonghhh", "writeDataToFirebasewqwqw: "+ e);
            }

        }
    }

    private static String getIntChapter(String value) {
        String[] parts = value.split("-");
        String chapter = parts[1];
        return chapter;
    }
}
