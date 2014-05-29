
package infinitec.eleventh.remindme.data;

import java.util.Locale;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

/**
 * @author Sharath Pandeshwar
 */
public class TablePatterns {
    private static final String TAG = "TablePatterns";

    public static final String NAME = "PATTERNS";

    public static void create(final SQLiteDatabase db) {

        final String columnDef = TextUtils
                .join(SQLConstants.COMMA,
                        new String[] {
                                String.format(Locale.US, SQLConstants.DATA_INTEGER_PK,
                                        BaseColumns._ID),
                                String.format(Locale.US, SQLConstants.DATA_TEXT,
                                        DatabaseColumns.PATTERN_NAME, ""),
                                String.format(Locale.US, SQLConstants.DATA_TEXT,
                                        DatabaseColumns.SMS_SENDER_NUMBER, ""),
                                String.format(Locale.US, SQLConstants.DATA_TEXT,
                                        DatabaseColumns.SMS_BODY, ""),
                                String.format(Locale.US, SQLConstants.DATA_INTEGER,
                                        DatabaseColumns.STATUS, 0),
                        });

        Log.d(TAG, columnDef);
        db.execSQL(String
                .format(Locale.US, SQLConstants.CREATE_TABLE, NAME, columnDef));

    }

    public static void upgrade(final SQLiteDatabase db, final int oldVersion,
            final int newVersion) {

        // Add any data migration code here. Default is to drop and rebuild the
        // table
        db.execSQL(String
                .format(Locale.US, SQLConstants.DROP_TABLE_IF_EXISTS, NAME));
        create(db);
    }
}
