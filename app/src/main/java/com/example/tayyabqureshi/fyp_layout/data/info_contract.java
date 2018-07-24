package com.example.tayyabqureshi.fyp_layout.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Hamza on 7/11/2018.
 */

public class info_contract {

    private info_contract(){}

    public static final String CONTENT_AUTHORITY = "com.example.tayyabqureshi.fyp_layout";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final String PATH_Device = "devices";

    public static final String PATH_NeighbourDevice = "neighbor_devices";

    public static final String PATH_Interest = "Interests";

    public static final String PATH_Files = "Files";

    public static final String PATH_Tags = "Tags";


    public static final String PATH_device_interest_junc = "devices_interests_junc";

    public static final String PATH_file_tag_junc = "files_tags_junc";



    public static final class DeviceEntry implements BaseColumns {


        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_Device);


        /** Name of database table for devices */

        public final static String TABLE_NAME = "devices";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_Mac_ID = "device_mac_address";


        public final static String COLUMN_Last_connection_time ="last_connection_time";


        public final static String COLUMN_Last_connection_date = "last_connection_date";

    }


    public static final class InterestEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_Interest);


        /** Name of database table for interest */

        public final static String TABLE_NAME = "interests";


        public final static String COLUMN_interest_ID = "interest_id";


        public final static String COLUMN_interest_name ="interest_name";

    }


    public static final class NeighborEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_NeighbourDevice);


        /** Name of database table for Neighbor devices */

        public final static String TABLE_NAME = "neighbor_devices";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_Neighbor_Mac_ID = "neighbor_device_mac_address";


        public final static String COLUMN_Last_connection_time ="last_connection_time";


        public final static String COLUMN_Last_connection_date = "last_connection_date";


    }

    public static final class FilesEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_Files);


        /** Name of database table for Files */

        public final static String TABLE_NAME = "files";


        public final static String COLUMN_File_id = "file_id";

        public final static String COLUMN_File_name = "file_name";


        public final static String COLUMN_File_type ="file_type";


        public final static String COLUMN_File_path = "file_path";

        public final static String COLUMN_File_size = "file_size";

        public final static String COLUMN_myFile = "my_file";


    }

    public static final class TagsEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_Tags);

        /** Name of database table for Tags */

        public final static String TABLE_NAME = "tags";


        public final static String COLUMN_tag_ID = "tag_id";

        public final static String COLUMN_tag_name1 = "tag_name1";

        public final static String COLUMN_tag_name2 = "tag_name2";

        public final static String COLUMN_tag_name3 = "tag_name3";


    }

    // JUNTCION TABLES CLASSES STARTING

    public static final class device_interest_jun_Entry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_device_interest_junc);

        public final static String TABLE_NAME = "devices_interests_junc";


        public final static String COLUMN_device_mac_add = "device_mac_address";

        public final static String COLUMN_interest_id = "interest_id";


    }

    public static final class device_neighbor_jun_Entry implements BaseColumns {



        public final static String TABLE_NAME = "devices_neighbors_devices_junc";


        public final static String COLUMN_device_mac_add = "device_mac_address";

        public final static String COLUMN_neighbor_device_mac_add = "neighbor_device_mac_address";


    }

    public static final class neighbor_devices_interest_jun_Entry implements BaseColumns {



        public final static String TABLE_NAME = "neighbors_devices_interest_junc";


        public final static String COLUMN_neighbor_device_mac_add = "neighbor_device_mac_address";

        public final static String COLUMN_Interest_id = "interest_id";


    }


    public static final class tags_interest_jun_Entry implements BaseColumns {



        public final static String TABLE_NAME = "tags_interests_junc";


        public final static String COLUMN_Tag_id = "tag_id";

        public final static String COLUMN_Interest_id = "interest_id";


    }

    public static final class files_tags_jun_Entry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_file_tag_junc);

        public final static String TABLE_NAME = "files_tags_junc";

        public final static String COLUMN_file_id = "file_id";

        public final static String COLUMN_Tag_id = "tag_id";




    }

}