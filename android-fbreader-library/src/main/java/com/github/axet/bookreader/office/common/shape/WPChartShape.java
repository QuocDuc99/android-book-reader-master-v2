package com.github.axet.bookreader.office.common.shape;

import com.github.axet.bookreader.office.thirdpart.achartengine.chart.AbstractChart;

public class WPChartShape extends WPAutoShape
{

	public AbstractChart getAChart() 
	{
		return chart;
	}

	public void setAChart(AbstractChart chart) 
	{
		this.chart = chart;
	}
	
	private AbstractChart chart;
}
