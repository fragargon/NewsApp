package com.example.android.newsapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Here is the source code for this EmptyRecyclerView
 * https://gist.github.com/adelnizamutdinov/31c8f054d1af4588dc5c
 * It's an implementation of an AdapterDataObserver that allows
 * to set a View as the default empty layout for recyclerView.
 * It's a subClass of RecyclerView.
 */

public class EmptyRecyclerView extends RecyclerView {

    private View emptyView;

    /**
     * Calls checkIfEmpty every time a changed occurs and
     *  observe the content of the adapter.
     */
    final private AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkIfEmpty();
        }
    };

    public EmptyRecyclerView(Context context) {
        super(context);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs,
                             int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * This method checks if both the empty view and adapter are not null.
     * If the item count provided by the adapter is equal to zero the empty
     * view is shown and the EmptyRecyclerView is hidden.
     * If the item count provided by the adapter is not zero then the empty
     * view is hidden and the EmptyRecyclerView is shown.
     */
    void checkIfEmpty() {
        if (emptyView != null && getAdapter() != null) {
            final boolean emptyViewVisible =
                    getAdapter().getItemCount() == 0;
            emptyView.setVisibility(emptyViewVisible ? VISIBLE : GONE);
            setVisibility(emptyViewVisible ? GONE : VISIBLE);
        }
    }

    /**
     *
     * @param adapter override the method setAdapter of its superclass
     *                and register an AdapterObserver whenever an adapter is set.
     *                It also unregisters the observer whenever the adapter is changed or unset.
     *                The AdapterObserver calls checkIfEmpty() every time it observes an event that
     *                changes the content of the adapter.
     *                CheckIfEmpty() is also called when the adapter or empty view are set.
     */
    @Override
    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }

        checkIfEmpty();
    }

    /**
     * Set an emptyView
     * @param emptyView refers to the adapter empty state.
     */
    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        checkIfEmpty();
    }
}
