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
        String pathBook =  "https://media-dev.thuvien.edu.vn/v_lib/upload/79776317/ebook/2024/3/65eecef546c6d1001d17d10d.pdf";
        openBook = findViewById(R.id.clickButton);
        openBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ducNQ", "onClickss: ");
                startActivity(BookActivity.newInstance(MainActivity.this,pathBook));
            }
        });
    }
}
