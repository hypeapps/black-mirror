package pl.mirror.black.blackmirror.ui.widget;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by jplee on 8/7/2016.
 */
public class ScrollingLinearLayoutManager extends LinearLayoutManager {
    private final int duration;

    public ScrollingLinearLayoutManager(Context context, int orientation, boolean reverseLayout, int duration) {
        super(context, orientation, reverseLayout);
        this.duration = duration;
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state,
                                       int position) {
        View firstVisibleChild = recyclerView.getChildAt(0);
        int itemHeight = firstVisibleChild.getHeight();
        int currentPosition = recyclerView.getChildLayoutPosition(firstVisibleChild);
        int distanceInPixels = Math.abs((currentPosition - position) * itemHeight);
        if (distanceInPixels == 0) {
            distanceInPixels = (int) Math.abs(firstVisibleChild.getY());
        }
        SmoothScroller smoothScroller = new SmoothScroller(recyclerView.getContext(), distanceInPixels, duration);
        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }

    private class SmoothScroller extends LinearSmoothScroller {
        private final float distanceInPixels;
        private final float duration;

        public SmoothScroller(Context context, int distanceInPixels, int duration) {
            super(context);
            this.distanceInPixels = distanceInPixels;
            this.duration = duration;
        }

        @Override
        public PointF computeScrollVectorForPosition(int targetPosition) {
            return ScrollingLinearLayoutManager.this
                    .computeScrollVectorForPosition(targetPosition);
        }

        @Override
        protected int calculateTimeForScrolling(int dx) {
            float proportion = (float) dx / distanceInPixels;
            return (int) (duration * proportion);
        }
    }
}
