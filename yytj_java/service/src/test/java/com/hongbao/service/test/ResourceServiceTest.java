package com.hongbao.service.test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hongbao.service.ResourceFacade;
import com.hongbao.service.test.base.BaseServiceTest;

public class ResourceServiceTest extends BaseServiceTest{
	@Autowired
    private ResourceFacade resourceFacade;
	@Test
	public void insert(){
	    File file = new File("C:\\Users\\lsh\\Desktop\\分类\\ic_beauty.png");
	    String url = null;
        try {
            url = resourceFacade.uploadFile(FileUtils.readFileToByteArray(file), file.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(url);
	    Assert.assertTrue(StringUtils.isNotEmpty(url));
	}
}
