package pl.hypeapps.blackmirror.ui.widget;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import pl.hypeapps.blackmirror.R;
import pl.hypeapps.blackmirror.model.news.News;
import pl.hypeapps.blackmirror.ui.features.home.NewsRecyclerAdapter;

public class NewsWidgetView extends ConstraintLayout {

    public RecyclerView tvnNewsRecyclerView = null;

    public RecyclerView polsatNewsRecyclerView = null;

    private NewsRecyclerAdapter polsatNewsAdapter;

    private NewsRecyclerAdapter tvnNewsAdapter;

    private TextView tvnNewsTitle;

    private TextView polsatNewsTitle;

    public NewsWidgetView(Context context) {
        super(context);
        init();
    }

    public NewsWidgetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NewsWidgetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_news_widget, this);
        this.polsatNewsRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_polsat_news);
        this.tvnNewsRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_tvn_news);
        this.polsatNewsTitle = (TextView) findViewById(R.id.polsat_text);
        this.tvnNewsTitle = (TextView) findViewById(R.id.tvn_text);
        this.polsatNewsAdapter = new NewsRecyclerAdapter(getContext());
        this.tvnNewsAdapter = new NewsRecyclerAdapter(getContext());
        tvnNewsRecyclerView.setLayoutManager(new ScrollingLinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false, 5000));
        tvnNewsRecyclerView.setAdapter(tvnNewsAdapter);
        polsatNewsRecyclerView.setAdapter(polsatNewsAdapter);
        polsatNewsRecyclerView.setLayoutManager(new ScrollingLinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false, 11000));
        this.setVisibility(GONE);
    }

    /**
     * Wypełnia widok modelem podanym w parametrze
     *
     * @param news model wiadomości kanału polsat
     */
    public void setPolsatNews(List<News> news) {
        this.setVisibility(View.VISIBLE);
        this.polsatNewsAdapter.addItems(news);
        this.polsatNewsRecyclerView.setVisibility(VISIBLE);
        startScrolling();
        this.setVisibility(VISIBLE);
        this.polsatNewsTitle.setVisibility(VISIBLE);
    }

    /**
     * Wypełnia widok modelem podanym w parametrze
     * @param news model wiadomości kanału tvn
     */
    public void setTvnNews(List<News> news) {
        this.setVisibility(View.VISIBLE);
        this.tvnNewsAdapter.addItems(news);
        startScrolling();
        this.setVisibility(VISIBLE);
        this.tvnNewsRecyclerView.setVisibility(VISIBLE);
        this.tvnNewsTitle.setVisibility(VISIBLE);
    }


    /**
     * Metoda ukrywająca widżet z animacją
     */
    public void hide() {
        this.setVisibility(View.GONE);
        this.tvnNewsTitle.setVisibility(GONE);
        this.tvnNewsRecyclerView.setVisibility(GONE);
        this.polsatNewsRecyclerView.setVisibility(GONE);
        this.polsatNewsTitle.setVisibility(GONE);
    }

    private void startScrolling() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                if (tvnNewsRecyclerView.getAdapter().getItemCount() != 0) {
                    tvnNewsRecyclerView.smoothScrollToPosition(tvnNewsRecyclerView.getAdapter().getItemCount());
                }
                if (polsatNewsRecyclerView.getAdapter().getItemCount() != 0) {
                    polsatNewsRecyclerView.smoothScrollToPosition(polsatNewsRecyclerView.getAdapter().getItemCount());
                }
            }
        }, 3000);
    }
}
