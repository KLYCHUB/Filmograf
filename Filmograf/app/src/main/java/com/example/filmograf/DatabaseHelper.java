    package com.example.filmograf;

    import android.content.ContentValues;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;
    import android.util.Log;

    public class DatabaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "filmograf.db";
        private static final int DATABASE_VERSION = 1;

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public static final String databaseName = "SignLog.db";

        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE users(email TEXT PRIMARY KEY, password TEXT, first_name TEXT, last_name TEXT)");
            db.execSQL("CREATE TABLE movies(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT, rating REAL, image_uri TEXT, user_email TEXT, FOREIGN KEY(user_email) REFERENCES users(email))");
        }
        public boolean insertMovie(String title, String description, float rating, String imageUri, String userEmail) {
            SQLiteDatabase MyDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("title", title);
            contentValues.put("description", description);
            contentValues.put("rating", rating);
            contentValues.put("image_uri", imageUri);
            contentValues.put("user_email", userEmail);

            long result = MyDatabase.insert("movies", null, contentValues);
            return result != -1;
        }

        public Cursor getUserMovies(String email) {
            SQLiteDatabase MyDatabase = this.getReadableDatabase();
            return MyDatabase.rawQuery("SELECT * FROM movies WHERE user_email = ?", new String[]{email});
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS movies");
            db.execSQL("DROP TABLE IF EXISTS users");
            onCreate(db);
        }

        public Cursor getAllUsers() {
            SQLiteDatabase MyDatabase = this.getReadableDatabase();
            return MyDatabase.rawQuery("SELECT * FROM users", null);
        }

        public int deleteMovie(String title) {
            SQLiteDatabase db = getWritableDatabase();
            return db.delete("movies", "title = ?", new String[]{title});
        }

        public Cursor getEmailData(String email) {
            SQLiteDatabase MyDatabase = this.getReadableDatabase();
            return MyDatabase.rawQuery("SELECT email, first_name, last_name FROM users WHERE email LIKE ?", new String[]{"%" + email + "%"});
        }

        public Boolean insertData(String email, String password, String firstName, String lastName){
            SQLiteDatabase MyDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("email", email);
            contentValues.put("password", password);
            contentValues.put("first_name", firstName);
            contentValues.put("last_name", lastName);

            long result = MyDatabase.insert("users", null, contentValues);
            if (result == -1) {
                Log.e("DatabaseHelper", "Insert failed for email: " + email);
                return false;
            } else {
                Log.d("DatabaseHelper", "Insert successful for email: " + email);
                return true;
            }
        }

        public Boolean checkEmail(String email){
            SQLiteDatabase MyDatabase = this.getWritableDatabase();
            Cursor cursor = MyDatabase.rawQuery("Select * from users where email = ?", new String[]{email});

            if(cursor.getCount() > 0) {
                return true;
            }else {
                return false;
            }
        }

        public Boolean checkEmailPassword(String email, String password){
            SQLiteDatabase MyDatabase = this.getWritableDatabase();
            Cursor cursor = MyDatabase.rawQuery("Select * from users where email = ? and password = ?", new String[]{email, password});

            if (cursor.getCount() > 0) {
                return true;
            }else {
                return false;
            }
        }
    }

