package com.kvaradi.app.chartbuilder;

import com.kvaradi.app.Calculation;
import com.kvaradi.app.Constants;
import org.jfree.ui.ApplicationFrame;

public abstract class AbstractChartBuilder extends ApplicationFrame {

    private static final long serialVersionUID = -6359175313085101703L;
    protected final transient Calculation calculation = new Calculation();
    public AbstractChartBuilder() {
        super(Constants.APPLICATION_TITLE);
    }

    public abstract void generateChart();
}