package ru.innopolis.askar.habrarss;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import ru.innopolis.askar.habrarss.model.Article;

public class ArticleDetailes extends AppCompatActivity {
    TextView tvTitle;
    TextView tvGuid;
    TextView tvDescription;

    Article article;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detailes);
        tvTitle = (TextView) findViewById(R.id.tvTitleValueArticle);
        tvGuid = (TextView) findViewById(R.id.tvGuidValueArticle);
        tvDescription = (TextView) findViewById(R.id.tvDescriptionValueArticle);

        article = (Article) getIntent().getSerializableExtra("article");
        tvGuid.setText(article.getGuid());
        tvDescription.setText(article.getDescription());
    }
}
