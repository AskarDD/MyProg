package ru.innopolis.askar.habrarss;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.webkit.WebView;
import android.widget.TextView;

import java.net.Proxy;

import ru.innopolis.askar.habrarss.model.Article;

public class ArticleDetailes extends AppCompatActivity {
    TextView tvTitle;
    TextView tvGuid;
    WebView wvDesc;

    Article article;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detailes);
        tvTitle = (TextView) findViewById(R.id.tvTitleValueArticle);
        tvGuid = (TextView) findViewById(R.id.tvGuidValueArticle);
        wvDesc = (WebView) findViewById(R.id.wvDescriptionValueArticle);
        wvDesc.getSettings().setJavaScriptEnabled(true);

        article = (Article) getIntent().getSerializableExtra("article");
        tvTitle.setText(article.getTitle());
        tvGuid.setText(article.getGuid());
        wvDesc.loadData(article.getDescription(), "text/html", "UTF_8");
    }
}
