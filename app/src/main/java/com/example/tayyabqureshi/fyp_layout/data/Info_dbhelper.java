package com.example.tayyabqureshi.fyp_layout.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tayyabqureshi.fyp_layout.data.info_contract.DeviceEntry;
import com.example.tayyabqureshi.fyp_layout.data.info_contract.FilesEntry;
import com.example.tayyabqureshi.fyp_layout.data.info_contract.InterestEntry;
import com.example.tayyabqureshi.fyp_layout.data.info_contract.NeighborEntry;
import com.example.tayyabqureshi.fyp_layout.data.info_contract.TagsEntry;
import com.example.tayyabqureshi.fyp_layout.data.info_contract.device_interest_jun_Entry;
import com.example.tayyabqureshi.fyp_layout.data.info_contract.device_neighbor_jun_Entry;
import com.example.tayyabqureshi.fyp_layout.data.info_contract.files_tags_jun_Entry;
import com.example.tayyabqureshi.fyp_layout.data.info_contract.neighbor_devices_interest_jun_Entry;
import com.example.tayyabqureshi.fyp_layout.data.info_contract.tags_interest_jun_Entry;


/**
 * Created by Hamza on 7/11/2018.
 */

public class Info_dbhelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "info.db";

    private static final int DATABASE_VERSION = 1;


    public Info_dbhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {



        String SQL_CREATE_Device_TABLE =  "CREATE TABLE " + DeviceEntry.TABLE_NAME + " ("
                + DeviceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DeviceEntry.COLUMN_Mac_ID + " TEXT NOT NULL, "
                + DeviceEntry.COLUMN_Last_connection_time + " TEXT, "
                + DeviceEntry.COLUMN_Last_connection_date + " TEXT);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_Device_TABLE);



        String SQL_CREATE_Interest_TABLE =  "CREATE TABLE " + InterestEntry.TABLE_NAME + " ("

                + InterestEntry.COLUMN_interest_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + InterestEntry.COLUMN_interest_name + " TEXT);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_Interest_TABLE);

        String SQL_CREATE_Neighbor_Device_TABLE =  "CREATE TABLE " + NeighborEntry.TABLE_NAME + " ("
                + NeighborEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NeighborEntry.COLUMN_Neighbor_Mac_ID + "  TEXT NOT NULL, "
                + NeighborEntry.COLUMN_Last_connection_time + " TEXT, "
                + NeighborEntry.COLUMN_Last_connection_date + " TEXT);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_Neighbor_Device_TABLE);

        String SQL_CREATE_Files_TABLE =  "CREATE TABLE " + FilesEntry.TABLE_NAME + " ("
                + FilesEntry.COLUMN_File_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FilesEntry.COLUMN_File_name + " TEXT, "
                + FilesEntry.COLUMN_File_type + " TEXT, "
                + FilesEntry.COLUMN_File_path + " TEXT, "
                + FilesEntry.COLUMN_File_size + " TEXT, "
                + FilesEntry.COLUMN_myFile + " TEXT);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_Files_TABLE);

        String SQL_CREATE_Tags_TABLE =  "CREATE TABLE " + TagsEntry.TABLE_NAME + " ("
                + TagsEntry.COLUMN_tag_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TagsEntry.COLUMN_tag_name1 + " TEXT, "
                + TagsEntry.COLUMN_tag_name2 + " TEXT, "
                + TagsEntry.COLUMN_tag_name3 + " TEXT);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_Tags_TABLE);



        // JUNCTION TABLES STARTING FROM HERE



        String SQL_CREATE_device_interest_junc_TABLE =  "CREATE TABLE " + device_interest_jun_Entry.TABLE_NAME + " ("
                + device_interest_jun_Entry.COLUMN_device_mac_add + " TEXT, "
                + device_interest_jun_Entry.COLUMN_interest_id + " TEXT,"
                + " FOREIGN KEY ("+ device_interest_jun_Entry.COLUMN_device_mac_add+") REFERENCES "+ DeviceEntry.TABLE_NAME +"("+ DeviceEntry.COLUMN_Mac_ID+")ON UPDATE CASCADE,"
                + " FOREIGN KEY ("+ device_interest_jun_Entry.COLUMN_interest_id+") REFERENCES "+ InterestEntry.TABLE_NAME +"("+ InterestEntry.COLUMN_interest_ID+")ON UPDATE CASCADE);";


        // Execute the SQL statement
        db.execSQL(SQL_CREATE_device_interest_junc_TABLE);


        String SQL_CREATE_device_neighbor_device_junc_TABLE =  "CREATE TABLE " + device_neighbor_jun_Entry.TABLE_NAME + " ("
                + device_neighbor_jun_Entry.COLUMN_device_mac_add + " TEXT, "
                + device_neighbor_jun_Entry.COLUMN_neighbor_device_mac_add + " TEXT,"
                + " FOREIGN KEY ("+ device_neighbor_jun_Entry.COLUMN_device_mac_add+") REFERENCES "+ DeviceEntry.TABLE_NAME +"("+ DeviceEntry.COLUMN_Mac_ID+")ON UPDATE CASCADE,"
                + " FOREIGN KEY ("+ device_neighbor_jun_Entry.COLUMN_neighbor_device_mac_add+") REFERENCES "+ NeighborEntry.TABLE_NAME +"("+ NeighborEntry.COLUMN_Neighbor_Mac_ID+")ON UPDATE CASCADE);";


        // Execute the SQL statement
        db.execSQL(SQL_CREATE_device_neighbor_device_junc_TABLE);


        String SQL_CREATE_neighbor_devices_interest_jun_TABLE =  "CREATE TABLE " + neighbor_devices_interest_jun_Entry.TABLE_NAME + " ("
                + neighbor_devices_interest_jun_Entry.COLUMN_neighbor_device_mac_add + " TEXT, "
                + neighbor_devices_interest_jun_Entry.COLUMN_Interest_id + " TEXT,"
                + " FOREIGN KEY ("+ neighbor_devices_interest_jun_Entry.COLUMN_neighbor_device_mac_add+") REFERENCES "+ NeighborEntry.TABLE_NAME +"("+ NeighborEntry.COLUMN_Neighbor_Mac_ID+")ON UPDATE CASCADE,"
                + " FOREIGN KEY ("+ neighbor_devices_interest_jun_Entry.COLUMN_Interest_id+") REFERENCES "+ InterestEntry.TABLE_NAME +"("+ InterestEntry.COLUMN_interest_ID+")ON UPDATE CASCADE);";


        // Execute the SQL statement
        db.execSQL(SQL_CREATE_neighbor_devices_interest_jun_TABLE);

        String SQL_CREATE_tags_interest__jun_TABLE =  "CREATE TABLE " + tags_interest_jun_Entry.TABLE_NAME + " ("
                + tags_interest_jun_Entry.COLUMN_Tag_id + " TEXT, "
                + tags_interest_jun_Entry.COLUMN_Interest_id + " TEXT,"
                + " FOREIGN KEY ("+ tags_interest_jun_Entry.COLUMN_Tag_id+") REFERENCES "+ TagsEntry.TABLE_NAME +"("+ TagsEntry.COLUMN_tag_ID+")ON UPDATE CASCADE,"
                + " FOREIGN KEY ("+ tags_interest_jun_Entry.COLUMN_Interest_id+") REFERENCES "+ InterestEntry.TABLE_NAME +"("+ InterestEntry.COLUMN_interest_ID+")ON UPDATE CASCADE);";


        // Execute the SQL statement
        db.execSQL(SQL_CREATE_tags_interest__jun_TABLE);


        String SQL_CREATE_files_tags_jun_TABLE =  "CREATE TABLE " + files_tags_jun_Entry.TABLE_NAME + " ("
                + files_tags_jun_Entry.COLUMN_file_id + " TEXT, "
                + files_tags_jun_Entry.COLUMN_Tag_id + " TEXT,"
                + " FOREIGN KEY ("+ files_tags_jun_Entry.COLUMN_file_id+") REFERENCES "+ FilesEntry.TABLE_NAME +"("+ FilesEntry.COLUMN_File_id+")ON UPDATE CASCADE,"
                + " FOREIGN KEY ("+ files_tags_jun_Entry.COLUMN_Tag_id+") REFERENCES "+ TagsEntry.TABLE_NAME +"("+ TagsEntry.COLUMN_tag_ID+")ON UPDATE CASCADE);";


        // Execute the SQL statement
        db.execSQL(SQL_CREATE_files_tags_jun_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
