package ru.innopolis.askar.habrarss;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.innopolis.askar.habrarss.model.Article;
import ru.innopolis.askar.habrarss.presenter.loaders.LoadLentaRss;

public class MainCheckList extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>> {
    public static final int LOAD_RSS = 1;
    private EditText etURL;
    private Button btnLoadRss;
    private RecyclerView rvRecords;
    private RecyclerViewAdapter adapter;
    private LinearLayoutManager layoutManager;
    private RecyclerView.ItemAnimator animator;
    private Context context;
    private String url;

    List<Article> articleList;
    List<Article> showList;
    Loader<List<Article>> loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_check_list);
        etURL = (EditText) findViewById(R.id.etURL);
        btnLoadRss = (Button) findViewById(R.id.btnLoadRss);
        rvRecords = (RecyclerView) findViewById(R.id.rvRssList);

        articleList = new ArrayList<>();
        showList = new ArrayList<>();

        adapter = new RecyclerViewAdapter(showList);
        layoutManager = new LinearLayoutManager(this);
        animator = new DefaultItemAnimator();
        context = this;
        inflateRecycler(showList);

        btnLoadRss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRss();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void loadRss() {
        loader = getSupportLoaderManager().initLoader(LOAD_RSS, new Bundle(), this);
        loader.forceLoad();
    }

    private void inflateRecycler(List<Article> articles) {
        showList.removeAll(showList);
        showList.addAll(articles);

        if (showList.size() > 0){
            rvRecords.setAdapter(adapter);
            rvRecords.setLayoutManager(layoutManager);
            rvRecords.setItemAnimator(animator);
        }

    }

    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {
        Loader<List<Article>> loader = null;
        if (id == LOAD_RSS)
            loader = new LoadLentaRss(this, etURL.getText().toString());
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> data) {
        if (data.size() > 0){
            inflateRecycler(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {

    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
        private List<Article> records;
        private int countRecords = 0;

        public RecyclerViewAdapter(List<Article> showList) {
            countRecords = 0;
            this.records = showList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
            return new RecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            countRecords = position;
            Article article = records.get(position);

            if (countRecords % 2 == 0)
                holder.rlRecordRecycler.setBackgroundColor(Color.WHITE);
            else
                holder.rlRecordRecycler.setBackgroundColor(Color.rgb(205, 255, 205));

            holder.tvTitle.setText(article.getTitle());
            holder.relativeLayoutPar.setArticle(article);
        }

        @Override
        public int getItemCount() {
            return records.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tvTitle;
            private RelativeLayout rlRecordRecycler;
            private RelativeLayoutPar relativeLayoutPar;


            public ViewHolder(View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.tvTitleItem);
                rlRecordRecycler = itemView.findViewById(R.id.rlRssListItem);
                relativeLayoutPar = new RelativeLayoutPar(itemView);

                rlRecordRecycler.setOnClickListener(relativeLayoutPar);
            }
        }

        private class RelativeLayoutPar implements View.OnClickListener {
            private Article article;
            private View view;
            private int position;

            public void setArticle(Article article) {
                this.article = article;
            }

            public RelativeLayoutPar(View view) {
                this.view = view;
            }

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainCheckList.this, ArticleDetailes.class);
                intent.putExtra("article", article);
                startActivity(intent);
            }
        }
    }

    private static Display getDefaultDisplay(Context context) {
        return ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    }

    private static Point getScreenSize(Context context){
        Point screenSize = new Point();
        getDefaultDisplay(context).getSize(screenSize);
        return screenSize;
    }

    public int getTextTitleWidth(String text, Paint paint){
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.left + bounds.width() + 80;
    }
}
