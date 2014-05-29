
package infinitec.eleventh.remindme.data;

import infinitec.eleventh.remindme.utils.Logger;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * @author Sharath Pandeshwar {@link SQLiteOpenHelper} to provide database
 *         connectivity for the application. Access these methods through the
 *         {@linkplain DBInterface} class
 */
public class RemindMeSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = "RemindMeSQLiteOpenHelper";

    /** Lock for synchronized methods */
    private static final Object LOCK = new Object();

    /** Database file name and version */
    private static final String DB_NAME = "remindme.sqlite";
    private static final int DB_VERSION = 1;

    /** SQLite Open Helper instance */
    private static RemindMeSQLiteOpenHelper sSQLiteOpenHelper;

    /**
     * Gets a reference to the SQLIte Open Helper for the app, creating it if
     * necessary. This method is thread-safe. The Methods of this class should
     * not be accessed directly. Access them through the
     * {@linkplain DBInterface} class
     * 
     * @param context The Context reference
     * @return the reference to {@link BarterLiSQLiteOpenHelper}
     */
    static RemindMeSQLiteOpenHelper getInstance(final Context context) {
        synchronized (LOCK) {
            if (sSQLiteOpenHelper == null) {
                synchronized (LOCK) {
                    sSQLiteOpenHelper = new RemindMeSQLiteOpenHelper(
                            context.getApplicationContext(), DB_NAME, null, DB_VERSION);
                }
            }
        }
        return sSQLiteOpenHelper;
    }

    /**
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    private RemindMeSQLiteOpenHelper(final Context context, final String name,
            final CursorFactory factory, final int version) {
        // Private so you need to use the getInstance() method
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        // Create tables
        TablePatterns.create(db);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion,
            final int newVersion) {
        // Upgrade tables
        TablePatterns.upgrade(db, oldVersion, newVersion);
    }
    
    
    /**
     * Query the given URL, returning a Cursor over the result set.
     * 
     * @param distinct <code>true</code> if dataset should be unique
     * @param table The table to query
     * @param columns The columns to fetch
     * @param selection The selection string, formatted as a WHERE clause
     * @param selectionArgs The arguments for the selection parameter
     * @param groupBy GROUP BY clause
     * @param having HAVING clause
     * @param orderBy ORDER BY clause
     * @param limit LIMIT clause
     * @return A {@link Cursor} over the dataset result
     */
    Cursor query(final boolean distinct, final String table,
                    final String[] columns, final String selection,
                    final String[] selectionArgs, final String groupBy,
                    final String having, final String orderBy,
                    final String limit) {

        final SQLiteDatabase database = getReadableDatabase();
        return database.query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }
    
    
    /**
     * Method for inserting rows into the database
     * 
     * @param table The table to insert into
     * @param nullColumnHack column names are known and an empty row can't be
     *            inserted. If not set to null, the nullColumnHack parameter
     *            provides the name of nullable column name to explicitly insert
     *            a NULL into in the case where your values is empty.
     * @param values The fields to insert
     * @param autoNotify Whethr to automatically notify a change on the table
     *            which was inserted into
     * @return The row Id of the newly inserted row, or -1 if unable to insert
     */
    long insert(final String table, final String nullColumnHack,
                    final ContentValues values) {
        final SQLiteDatabase database = getWritableDatabase();
        final long insertRowId = database.insert(table, nullColumnHack, values);
        Logger.v(TAG, "Inserted at " + insertRowId);
        return insertRowId;
    }
    
    
    /**
     * Updates the table with the given data
     * 
     * @param table The table to update
     * @param values The fields to update
     * @param whereClause The WHERE clause
     * @param whereArgs Arguments for the where clause
     * @param autoNotify Whether to automatically notify any changes to the
     *            table
     * @return The number of rows updated
     */
    int update(final String table, final ContentValues values,
                    final String whereClause, final String[] whereArgs,
                    final boolean autoNotify) {

        final SQLiteDatabase database = getWritableDatabase();
        final int updateCount = database
                        .update(table, values, whereClause, whereArgs);
        return updateCount;
    }
    
    /**
     * Delete rows from the database
     * 
     * @param table The table to delete from
     * @param whereClause The WHERE clause
     * @param whereArgs Arguments for the where clause
     * @param autoNotify Whether to automatically notify any changes to the
     *            table
     * @return The number of rows deleted
     */
    int delete(final String table, final String whereClause,
                    final String[] whereArgs, final boolean autoNotify) {

        final SQLiteDatabase database = getWritableDatabase();
        final int deleteCount = database.delete(table, whereClause, whereArgs);
        return deleteCount;
    }
    
}
