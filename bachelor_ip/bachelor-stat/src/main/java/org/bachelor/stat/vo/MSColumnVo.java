package org.bachelor.stat.vo;


import java.io.Serializable;

public class MSColumnVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6137163358238786928L;

	
	private String caption;//标题
	private String yaxisname;//Y轴标题
	private String canvasbgcolor= "FEFEFE";//整体背景颜色
	private String canvasbasecolor = "FEFEFE";//整基本颜色
	private String tooltipbgcolor = "DEDEBE";//提示背景颜色
	private String tooltipborder = "889E6D";//提示边框大小
	private String divlinecolor = "999999";//柱线颜色
	private String showcolumnshadow = "0";//显示行阴影
	private String divlineisdashed = "1";//柱虚线
	private String divlinedashlen = "1";//柱折线
	private String divlinedashgap = "2";//柱空白
	private String numberprefix = "";//数值符号
	private String numbersuffix = "";//数值类型  例200M 1000毫秒
	
	
	public MSColumnVo(){ 
		
	}
	
	
	public String getNumberprefix() {
		return numberprefix;
	}



	public void setNumberprefix(String numberprefix) {
		this.numberprefix = numberprefix;
	}

	public String getNumbersuffix() {
		return numbersuffix;
	}



	public void setNumbersuffix(String numbersuffix) {
		this.numbersuffix = numbersuffix;
	}



	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getYaxisname() {
		return yaxisname;
	}
	public void setYaxisname(String yaxisname) {
		this.yaxisname = yaxisname;
	}
	public String getCanvasbgcolor() {
		return canvasbgcolor;
	}
	public void setCanvasbgcolor(String canvasbgcolor) {
		this.canvasbgcolor = canvasbgcolor;
	}
	public String getCanvasbasecolor() {
		return canvasbasecolor;
	}
	public void setCanvasbasecolor(String canvasbasecolor) {
		this.canvasbasecolor = canvasbasecolor;
	}
	public String getTooltipbgcolor() {
		return tooltipbgcolor;
	}
	public void setTooltipbgcolor(String tooltipbgcolor) {
		this.tooltipbgcolor = tooltipbgcolor;
	}
	public String getTooltipborder() {
		return tooltipborder;
	}
	public void setTooltipborder(String tooltipborder) {
		this.tooltipborder = tooltipborder;
	}
	public String getDivlinecolor() {
		return divlinecolor;
	}
	public void setDivlinecolor(String divlinecolor) {
		this.divlinecolor = divlinecolor;
	}
	public String getShowcolumnshadow() {
		return showcolumnshadow;
	}
	public void setShowcolumnshadow(String showcolumnshadow) {
		this.showcolumnshadow = showcolumnshadow;
	}
	public String getDivlineisdashed() {
		return divlineisdashed;
	}
	public void setDivlineisdashed(String divlineisdashed) {
		this.divlineisdashed = divlineisdashed;
	}
	public String getDivlinedashlen() {
		return divlinedashlen;
	}
	public void setDivlinedashlen(String divlinedashlen) {
		this.divlinedashlen = divlinedashlen;
	}
	public String getDivlinedashgap() {
		return divlinedashgap;
	}
	public void setDivlinedashgap(String divlinedashgap) {
		this.divlinedashgap = divlinedashgap;
	}
}
