package cn.org.bachelor.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * BaseController 类是一个基础控制器类，用于处理日期格式化的绑定。
 * 该类提供了日期格式化的设置和获取方法，以及初始化数据绑定器的方法。
 */
public class BaseController {

	/**
	 * 日期格式字符串，默认值为 "yyyy-MM-dd HH:mm:ss"。
	 */
	private String dateFormat = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 初始化数据绑定器，用于注册自定义的日期属性编辑器。
	 * 当请求中包含 Date 类型的属性时，将使用该编辑器进行类型转换。
	 *
	 * @param binder 数据绑定器对象，用于绑定请求参数到 JavaBean。
	 * @throws Exception 由于代码中该异常不会抛出，建议移除 throws Exception 声明。
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		// 注册自定义的属性编辑器
		DateFormat df = new SimpleDateFormat(dateFormat);
		CustomDateEditor dateEditor = new CustomDateEditor(df, true);
		// 表示如果命令对象有 Date 类型的属性，将使用该属性编辑器进行类型转换
		binder.registerCustomEditor(Date.class, dateEditor);
	}

	/**
	 * 获取当前设置的日期格式。
	 * 该方法目前未被使用，可考虑移除。
	 *
	 * @return 日期格式字符串。
	 */
	public String getDateFormat() {
		return dateFormat;
	}

	/**
	 * 设置日期格式。
	 * 该方法目前未被使用，可考虑移除。
	 *
	 * @param dateFormat 要设置的日期格式字符串。
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
}