package com.github.axet.bookreader.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.github.axet.bookreader.R;

public class MainActivity extends AppCompatActivity {
    private TextView openBook;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main1);
        String pathBook = "https://media.thuvien.edu.vn/v_lib/upload/30297405/ebook/2024/4/661758c4e7f342001df15c97.pdf";
               // "https://media.thuvien.edu.vn/v_lib/upload/30297405/electure/2024/5/663587a2e7f342001df16773/index.html";
        openBook = findViewById(R.id.clickButton);
        openBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(
                //        BookActivity.newInstance(MainActivity.this, pathBook, "Book demo", "", 10));
            }
        });
    }
}
