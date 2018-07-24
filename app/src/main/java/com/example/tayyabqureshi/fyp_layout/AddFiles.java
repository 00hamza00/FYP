package com.example.tayyabqureshi.fyp_layout;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tayyabqureshi.fyp_layout.data.Info_dbhelper;
import com.example.tayyabqureshi.fyp_layout.data.info_contract;

import java.io.File;

import static android.content.Intent.ACTION_GET_CONTENT;

public class AddFiles extends AppCompatActivity {

    Button btnSelectFile,btnAddTag;
    Intent intent;


    EditText tag1,tag2,tag3;
    TextView filename;
    String t1,t2,t3,fileType,fileName,Path;
    double s,fsize;






    public Info_dbhelper mDbHelper;
    Cursor cursor,cursor1,cursor2;
    ContentValues values1,values,values2;
    Uri newUri,newUri1,newUri2;
    String fileid,tagid;
    private final String query_fileid = "SELECT file_id FROM files WHERE file_name=?";
    private final String query_tagid = "SELECT tag_id FROM tags WHERE tag_name1=?";


    private final String query = "SELECT * FROM files";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_files);

        btnSelectFile=(Button) findViewById(R.id.btn_selectfile);

        btnAddTag=(Button)   findViewById(R.id.btn_addtag);
        mDbHelper = new Info_dbhelper(getApplicationContext());

        filename = (TextView)findViewById(R.id.txt_filename);
        tag1=(EditText)  findViewById(R.id.edt_tag1);
        tag2=(EditText)  findViewById(R.id.edt_tag2);
        tag3=(EditText)  findViewById(R.id.edt_tag3);

        btnSelectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ACTION_GET_CONTENT);
                Log.i("info","Nokia Phone"+ACTION_GET_CONTENT);
               // intent.setType("*/*"); nokia
                Intent intent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
                 intent.putExtra("CONTENT_TYPE", "*/*");
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                startActivityForResult(intent, 7);

            }
        });

        btnAddTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                t1 = tag1.getText().toString();
                t2 = tag2.getText().toString();
                t3 = tag2.getText().toString();
                save();
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 7:
                if (resultCode == RESULT_OK) {
                    Path = data.getData().getPath();
                    try {
                        File file = new File(Path);
                        // Toast.makeText(this, "File Selected", Toast.LENGTH_LONG).show();
                        Log.i("info", " File " + file);
                        if (file != null) {
                            Log.i("info", "file Path :" + Path);
                            fileName = file.getName();
                            filename.setText(fileName);
                            Log.i("info", "file Name :" + fileName);
                            //  int file_size = Integer.parseInt(String.valueOf(file.length()/1024));
                            fileType= getExt(Path);
                            //type.setText(fileType);
                            Log.i("info", "file type :" + getExt(Path));

                            s=file.length()/1024;
                            fsize=s/1024;
                            Log.i("info", "file Size :" + s);
                            //  size.setText(""+fsize);
                        }
                    } catch (Exception e) {
                    }
                    break;
                }
        }
    }
    public String getExt(String filePath){
        int strLength = filePath.lastIndexOf(".");
        if(strLength > 0)
            return filePath.substring(strLength + 1).toLowerCase();
        return null;
    }
    public void save(){


        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        values = new ContentValues();
        values.put(info_contract.FilesEntry.COLUMN_File_name, fileName);
        values.put(info_contract.FilesEntry.COLUMN_File_type, fileType);
        values.put(info_contract.FilesEntry.COLUMN_File_path, Path);
        values.put(info_contract.FilesEntry.COLUMN_File_size, fsize);



        newUri = getContentResolver().insert(info_contract.FilesEntry.CONTENT_URI, values);

        values1 = new ContentValues();
        values1.put(info_contract.TagsEntry.COLUMN_tag_name1, t1);
        values1.put(info_contract.TagsEntry.COLUMN_tag_name2, t2);
        values1.put(info_contract.TagsEntry.COLUMN_tag_name3, t3);

        newUri1 = getContentResolver().insert(info_contract.TagsEntry.CONTENT_URI, values1);



        // SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Log.i("infoo","database is: "+ db);


        cursor1= db.rawQuery(query_fileid,new String[]{String.valueOf(fileName)});
        int fileid_ColumnIndex = cursor1.getColumnIndex(info_contract.FilesEntry.COLUMN_File_id);


        cursor1.moveToNext();
        // Use that index to extract the String or Int value of the word
        // at the current row the cursor is on.

        fileid = cursor1.getString(fileid_ColumnIndex);

        Log.i("infoo","id is: "+ fileid);


        cursor= db.rawQuery(query_tagid,new String[]{String.valueOf(t1)});
        int tagid_ColumnIndex = cursor.getColumnIndex(info_contract.TagsEntry.COLUMN_tag_ID);


        cursor.moveToNext();
        // Use that index to extract the String or Int value of the word
        // at the current row the cursor is on.

        tagid = cursor.getString(tagid_ColumnIndex);


        values2 = new ContentValues();
        values2.put(info_contract.files_tags_jun_Entry.COLUMN_file_id, fileid);
        values2.put(info_contract.files_tags_jun_Entry.COLUMN_Tag_id, tagid);



        newUri2 = getContentResolver().insert(info_contract.files_tags_jun_Entry.CONTENT_URI, values2);



        if (newUri == null && newUri1 ==null && newUri2==null) {

            Toast.makeText(this, "Did not entered", Toast.LENGTH_SHORT).show();


        } else {

            Toast.makeText(this, "Data entered", Toast.LENGTH_SHORT).show();
            values.clear();
            values1.clear();
        }

        cursor2 = db.rawQuery(query, null);
        int Fileid_ColumnIndex = cursor1.getColumnIndex(info_contract.FilesEntry.COLUMN_File_id);
        int name = cursor2.getColumnIndex(info_contract.FilesEntry.COLUMN_File_name);
        int type = cursor2.getColumnIndex(info_contract.FilesEntry.COLUMN_File_type);
        int path = cursor2.getColumnIndex(info_contract.FilesEntry.COLUMN_File_path);
        int size = cursor2.getColumnIndex(info_contract.FilesEntry.COLUMN_File_size);
        int myfiles = cursor2.getColumnIndex(info_contract.FilesEntry.COLUMN_myFile);

        while (cursor2.moveToNext()) {

            // Use that index to extract the String or Int value of the word
            // at the current row the cursor is on.
            String id = cursor2.getString(Fileid_ColumnIndex);
            String n = cursor2.getString(name);
            String t = cursor2.getString(type);
            String p = cursor2.getString(path);
            String s = cursor2.getString(size);
            String my = cursor2.getString(myfiles);

            Log.i("DB", "my files table contains: " + id + " name: " +n+  " type: " +t+ " path: " +p+ " size: " +s+ " myfile: " +my);


        }

    }




}


