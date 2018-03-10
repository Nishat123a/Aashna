package aashna.com.aashna.H_PCare;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import aashna.com.aashna.aashna_main.Card;
import aashna.com.aashna.R;

import java.util.ArrayList;
import java.util.List;

public class Pregnancy_Care_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Pregnancy_Care_Adapter Pcare_adapter;
    private List<Card> Pregnancy_Care_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_tips);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        Pregnancy_Care_list = new ArrayList<>();
        Pcare_adapter = new Pregnancy_Care_Adapter(this, Pregnancy_Care_list);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(Pcare_adapter);

        prepareAlbums();
    }

    /**
     * Adding few albums for testing
     */
    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.pcare1,
                R.drawable.pcare2,
                R.drawable.pcare3,
                R.drawable.pcare4,
                R.drawable.pcare5,
                R.drawable.pcare6};

        Card card = new Card("Healthy Pregnancy", covers[0]);
        Pregnancy_Care_list.add(card);

        card = new Card("Food Habits", covers[1]);
        Pregnancy_Care_list.add(card);

        card = new Card("Pregnancy Scan", covers[2]);
        Pregnancy_Care_list.add(card);

        card = new Card("Three Stages", covers[3]);
        Pregnancy_Care_list.add(card);

        card = new Card("Pregnancy Pains", covers[4]);
        Pregnancy_Care_list.add(card);

        card = new Card("Baby Appearance", covers[5]);
        Pregnancy_Care_list.add(card);

        Pcare_adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
