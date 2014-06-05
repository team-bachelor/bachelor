package org.bachelor.demo.report.dao.impl;

import org.springframework.stereotype.Repository;

import org.bachelor.demo.report.dao.IReportDao;
import org.bachelor.demo.report.domain.Report;
import org.bachelor.dao.impl.GenericDaoImpl;

@Repository
public class ReportDaoImpl  extends GenericDaoImpl<Report,String> implements IReportDao{

}
