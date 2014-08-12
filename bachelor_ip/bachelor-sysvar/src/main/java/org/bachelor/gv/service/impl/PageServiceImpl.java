package org.bachelor.gv.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.bachelor.gv.domain.GlobalVariable;
import org.bachelor.gv.service.IGVService;
import org.bachelor.web.service.IPageService;
import org.springframework.beans.factory.annotation.Autowired;

public class PageServiceImpl implements IPageService {

	@Autowired
	private IGVService gvService;
	
	@Override
	public int getPageRowNum() {
		GlobalVariable gvPageNum = gvService.findByName("pageRowNum");
		if(gvPageNum != null && StringUtils.isEmpty(gvPageNum.getValue())){
			return Integer.parseInt(gvPageNum.getValue());
		}
		return 0;
	}

}
