import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.util.ShapeUtils;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.axis.NumberAxis;


import java.text.DecimalFormat;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;


public class StatisticsChart extends JPanel {
    private static final long serialVersionUID = 1L; 
    ArrayList<Integer> releaseYearList, releaseYearCounter, startYearList, startYearCounter;
    Controller controller;
    
    public StatisticsChart(ArrayList<Integer> releaseYearList, ArrayList<Integer> releaseYearCounter, ArrayList<Integer> startYearList, ArrayList<Integer> startYearCounter, Controller controller) {
        this.releaseYearList = releaseYearList;
        this.releaseYearCounter = releaseYearCounter;
        this.startYearList = startYearList;
        this.startYearCounter = startYearCounter;
        this.controller = controller;
        create();
    }

    private void create() {
        IntervalXYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(225,145));
        chartPanel.setOpaque(false);
        
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        setPreferredSize(new Dimension(225,155));
        setOpaque(false);

        JLabel spacer = new JLabel(); 
        spacer.setPreferredSize(new Dimension(225,10));
        add(spacer);

        add(chartPanel);
    }

    private IntervalXYDataset createDataset() {
        
        XYSeries series1 = new XYSeries("Release Year");
        if (releaseYearList.size() == 1) releaseYearCounter.set(0,1);
        for (int i = 0; i < releaseYearList.size(); i++) {
            series1.add(releaseYearList.get(i),releaseYearCounter.get(i));
        }
        
        
        XYSeries series2 = new XYSeries("Start Year");
        if (startYearList.size() == 1) startYearCounter.set(0,1);
        for (int i = 0; i < startYearList.size(); i++) {
            series2.add(startYearList.get(i),startYearCounter.get(i));
        }
       
        
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);

        return dataset;
    }

    private JFreeChart createChart(final IntervalXYDataset dataset) {

        JFreeChart chart = ChartFactory.createHistogram("","","",dataset,PlotOrientation.VERTICAL,true,false,false);
        
        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesPaint(0, new Color(0,0,255,200));
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesShape(0, ShapeUtils.createDiamond(0));
        renderer.setSeriesPaint(1, Color.RED);
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));
        renderer.setSeriesShape(1, ShapeUtils.createDiamond(0));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(controller.getFieldColor("background2"));
        plot.setRangeGridlinesVisible(false);
        plot.getRangeAxis().setTickLabelPaint(controller.getFieldColor("text"));
        plot.getRangeAxis().setAxisLinePaint(controller.getFieldColor("text"));
        plot.getRangeAxis().setTickMarkPaint(controller.getFieldColor("text"));
        plot.getDomainAxis().setTickLabelPaint(controller.getFieldColor("text"));
        plot.getDomainAxis().setAxisLinePaint(controller.getFieldColor("text"));
        plot.getDomainAxis().setTickMarkPaint(controller.getFieldColor("text"));
        plot.setDomainGridlinesVisible(false);

        ((NumberAxis)plot.getDomainAxis()).setNumberFormatOverride(new DecimalFormat("####")); // removes commas in axis numbers

        chart.setTitle("");
        chart.setBackgroundPaint(controller.getFieldColor("background1"));
        chart.getLegend().setBackgroundPaint(controller.getFieldColor("background1"));
        chart.getLegend().setItemPaint(controller.getFieldColor("text"));
        chart.getLegend().setPosition(RectangleEdge.TOP);
        chart.getLegend().setItemFont(new Font("Dialog", Font.BOLD, 15));

        return chart;
    }
}
