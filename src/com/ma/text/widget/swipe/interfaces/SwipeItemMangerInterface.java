package com.ma.text.widget.swipe.interfaces;

import java.util.List;

import com.ma.text.widget.swipe.Mode;
import com.ma.text.widget.swipe.SwipeLayout;

public interface SwipeItemMangerInterface {

    public void openItem(int position);

    public void closeItem(int position);

    public void closeAllExcept(SwipeLayout layout);

    public List<Integer> getOpenItems();

    public List<SwipeLayout> getOpenLayouts();

    public void removeShownLayouts(SwipeLayout layout);

    public boolean isOpen(int position);

    public Mode getMode();

    public void setMode(Mode mode);
}
