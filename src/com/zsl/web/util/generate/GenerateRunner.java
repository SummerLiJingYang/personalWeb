package com.zsl.web.util.generate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class GenerateRunner {

	@Before
	public void init() {

	}

	@SuppressWarnings("unchecked")
	@Test
	public void run() {
		List<Map> paramList = new ArrayList<Map>();
		Map params = new HashMap<String, String>();
		params.put("modelName", "ComanyMember");
		params.put(Generate.PACKAGE_Path, "com.zsl.web.modules.member");
		params.put("creater", "linjiande");
		paramList.add(params);
		Generate.genDao(paramList, "dao.ftl", true);
		Generate.genDaoImpl(paramList, "daoImpl.ftl", true);
		Generate.genService(paramList, "service.ftl", true);
		Generate.genServiceImpl(paramList, "serviceImpl.ftl", true);
		Generate.genController(paramList, "controller.ftl", true);
	}
}
