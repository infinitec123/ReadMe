
package infinitec.eleventh.remindme.data;

import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

/**
 * @author Sharath Pandeshwar Custom implementation of {@link Loader} to read
 *         from {@link RemindMeSQLiteOpenHelper}
 */
public class SQLiteLoader extends AsyncTaskLoader<Cursor> {

    private static final String TAG = "SQLiteLoader";

    /**
     * Cursor loaded from the SQLiteDatabase
     */
    private Cursor mCursor;

    private final boolean mDistinct;

    private final String mTable;

    private final String[] mColumns;

    private final String mSelection;

    private final String[] mSelectionArgs;

    private final String mGroupBy;

    private final String mHaving;

    private final String mOrderBy;

    private final String mLimit;

    /**
     * Construct a loader to load from the database
     * 
     * @param context Reference to a {@link Context}
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
    public SQLiteLoader(final Context context, final boolean distinct, final String table,
            final String[] columns, final String selection, final String[] selectionArgs,
            final String groupBy, final String having, final String orderBy, final String limit) {
        super(context);
        mDistinct = distinct;
        mTable = table;
        mColumns = columns;
        mSelection = selection;
        mSelectionArgs = selectionArgs;
        mGroupBy = groupBy;
        mHaving = having;
        mOrderBy = orderBy;
        mLimit = limit;
    }

    @Override
    public Cursor loadInBackground() {
        return RemindMeSQLiteOpenHelper
                .getInstance(getContext())
                .query(mDistinct, mTable, mColumns, mSelection, mSelectionArgs, mGroupBy, mHaving,
                        mOrderBy, mLimit);
    }

    @Override
    public void deliverResult(final Cursor data) {

        if (isReset()) {
            if (data != null) {
                data.close();
            }
            return;
        }
        final Cursor oldCursor = mCursor;
        mCursor = data;

        if (isStarted()) {
            super.deliverResult(data);
        }

        if ((oldCursor != null) && (oldCursor != data) && !oldCursor.isClosed()) {
            oldCursor.close();
        }
    }

    @Override
    public void onCanceled(final Cursor data) {
        if ((data != null) && !data.isClosed()) {
            data.close();
        }
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if ((mCursor != null) && !mCursor.isClosed()) {
            mCursor.close();
        }
        mCursor = null;
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onStartLoading() {
        if (mCursor != null) {
            deliverResult(mCursor);
        }

        if (takeContentChanged() || (mCursor == null)) {
            forceLoad();
        }
    }

}
