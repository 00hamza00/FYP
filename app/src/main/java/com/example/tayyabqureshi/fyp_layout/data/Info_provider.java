package com.example.tayyabqureshi.fyp_layout.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Hamza on 7/11/2018.
 */

public class Info_provider extends ContentProvider {

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = Info_provider.class.getSimpleName();

    /**
     * URI matcher code for the content URI for the pets table
     */
    private static final int Device = 100;
    private static final int Interest = 200;
    private static final int Neighbors = 300;
    private static final int Files = 400;
    private static final int Tags = 500;
    private static final int Device_Interest = 600;
    private static final int file_tag = 700;

    /**
     * URI matcher code for the content URI for a single pet in the pets table
     */
    private static final int Device_ID = 101;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        // The content URI of the form "content://com.example.android.pets/pets" will map to the
        // integer code {@link #PETS}. This URI is used to provide access to MULTIPLE rows
        // of the pets table.
        sUriMatcher.addURI(info_contract.CONTENT_AUTHORITY, info_contract.PATH_Device, Device);
        sUriMatcher.addURI(info_contract.CONTENT_AUTHORITY, info_contract.PATH_Interest, Interest);
        sUriMatcher.addURI(info_contract.CONTENT_AUTHORITY, info_contract.PATH_NeighbourDevice, Neighbors);
        sUriMatcher.addURI(info_contract.CONTENT_AUTHORITY, info_contract.PATH_Files, Files);
        sUriMatcher.addURI(info_contract.CONTENT_AUTHORITY, info_contract.PATH_Tags, Tags);
        sUriMatcher.addURI(info_contract.CONTENT_AUTHORITY, info_contract.PATH_device_interest_junc, Device_Interest);
        sUriMatcher.addURI(info_contract.CONTENT_AUTHORITY, info_contract.PATH_file_tag_junc, file_tag);

        // The content URI of the form "content://com.example.android.pets/pets/#" will map to the
        // integer code {@link #PET_ID}. This URI is used to provide access to ONE single row
        // of the pets table.
        //
        // In this case, the "#" wildcard is used where "#" can be substituted for an integer.
        // For example, "content://com.example.android.pets/pets/3" matches, but
        // "content://com.example.android.pets/pets" (without a number at the end) doesn't match.
        sUriMatcher.addURI(info_contract.CONTENT_AUTHORITY, info_contract.PATH_Device + "/#", Device_ID);
    }


    /**
     * Initialize the provider and the database helper object.
     */
    private Info_dbhelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new Info_dbhelper(getContext());
        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();


        SQLiteQueryBuilder mQueryBuilder = new SQLiteQueryBuilder();
        // This cursor will hold the result of the query
        Cursor cursor;
        String joins = " JOIN devices_interests_junc ON devices.device_mac_address = devices_interests_junc.device_mac_address " +
                "JOIN interests ON interests.interest_id = devices_interests_junc.interest_id";// WHERE devices.device_mac_address ="+"HAMZA_DEVICE";


        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case Device:
                cursor = database.query(info_contract.DeviceEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            case Device_ID:
                selection = info_contract.DeviceEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(info_contract.DeviceEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case Interest:
                cursor = database.query(info_contract.InterestEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            case Neighbors:
                cursor = database.query(info_contract.NeighborEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            case Files:
                cursor = database.query(info_contract.FilesEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            case Tags:
                cursor = database.query(info_contract.TagsEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            case Device_Interest:

                mQueryBuilder.setTables(info_contract.DeviceEntry.TABLE_NAME + joins);
                cursor = mQueryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);


                break;

            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }


    /**
     * Insert new data into the provider with the given ContentValues.
     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case Device:
                return insertDevice(uri, contentValues);
            case Interest:
                return insertInterest(uri, contentValues);
            case Neighbors:
                return insertNeighbors(uri, contentValues);
            case Files:
                return insertFiles(uri, contentValues);
            case Tags:
                return insertTags(uri, contentValues);
            case Device_Interest:
                return insertDevice_interest(uri, contentValues);
            case file_tag:
                return insertTag_file(uri,contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Insert a pet into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertDevice(Uri uri, ContentValues values) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(info_contract.DeviceEntry.TABLE_NAME, null, values);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);

            return null;
        }

        // Notify all listeners that the data has changed for the pet content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        return ContentUris.withAppendedId(uri, id);
    }


    private Uri insertInterest(Uri uri, ContentValues values) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(info_contract.InterestEntry.TABLE_NAME, null, values);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);

            return null;
        }

        // Notify all listeners that the data has changed for the pet content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        return ContentUris.withAppendedId(uri, id);
    }

    private Uri insertNeighbors(Uri uri, ContentValues values) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(info_contract.NeighborEntry.TABLE_NAME, null, values);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);

            return null;
        }

        // Notify all listeners that the data has changed for the pet content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        return ContentUris.withAppendedId(uri, id);
    }

    private Uri insertFiles(Uri uri, ContentValues values) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(info_contract.FilesEntry.TABLE_NAME, null, values);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);

            return null;
        }

        // Notify all listeners that the data has changed for the pet content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        return ContentUris.withAppendedId(uri, id);
    }

    private Uri insertTags(Uri uri, ContentValues values) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(info_contract.TagsEntry.TABLE_NAME, null, values);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);

            return null;
        }

        // Notify all listeners that the data has changed for the pet content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        return ContentUris.withAppendedId(uri, id);
    }

    private Uri insertDevice_interest(Uri uri, ContentValues values) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(info_contract.device_interest_jun_Entry.TABLE_NAME, null, values);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);

            return null;
        }

        // Notify all listeners that the data has changed for the pet content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        return ContentUris.withAppendedId(uri, id);
    }


    private Uri insertTag_file(Uri uri, ContentValues values) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(info_contract.files_tags_jun_Entry.TABLE_NAME, null, values);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);

            return null;
        }

        // Notify all listeners that the data has changed for the pet content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        return ContentUris.withAppendedId(uri, id);
    }


    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case Device:
                return updateDevice(uri, contentValues, selection, selectionArgs);
            case Device_ID:
                // For the PET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = info_contract.DeviceEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateDevice(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /**
     * Update pets in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more pets).
     * Return the number of rows that were successfully updated.
     */
    private int updateDevice(Uri uri, ContentValues values, String selection, String[] selectionArgs) {


        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        getContext().getContentResolver().notifyChange(uri, null);
        // Returns the number of database rows affected by the update statement
        return database.update(info_contract.DeviceEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Track the number of rows that were deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case Device:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(info_contract.DeviceEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case Interest:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(info_contract.InterestEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case Neighbors:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(info_contract.NeighborEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case Files:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(info_contract.FilesEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case Tags:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(info_contract.TagsEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case Device_ID:
                // Delete a single row given by the ID in the URI
                selection = info_contract.DeviceEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(info_contract.DeviceEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;
    }

    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(Uri uri) {
        return null;
    }
}


