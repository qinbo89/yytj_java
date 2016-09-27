package com.hongbao.restapi.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hongbao.dal.base.controller.ResponseObject;



@Controller
public class KeepALiveController {


	@Value("${keep.alive.host1}")
	private String keep_alive_host_1;
	@Value("${keep.alive.port1}")
	private String keep_alive_port_1;
	@Value("${keep.alive.id1}")
	private String keep_alive_id_1;

	@Value("${keep.alive.host2}")
	private String keep_alive_host_2;
	@Value("${keep.alive.port2}")
	private String keep_alive_port_2;
	@Value("${keep.alive.id2}")
	private String keep_alive_id_2;

	@Value("${keep.alive.host3}")
	private String keep_alive_host_3;
	@Value("${keep.alive.port3}")
	private String keep_alive_port_3;
	@Value("${keep.alive.id3}")
	private String keep_alive_id_3;

	@RequestMapping(value = "/sys/getKeepAliveInfo")
	@ResponseBody
	public ResponseObject<List<Map<String, String>>> getKeepAliveInfo() {
		ResponseObject<List<Map<String, String>>> 
		responseObject = new ResponseObject<List<Map<String, String>>>();
		List<Map<String, String>> list = getSysChannelInfo();
		responseObject.setData(list);
		return responseObject;
	}

	public List<Map<String, String>> getSysChannelInfo() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		Map<String, String> map = new HashMap<String, String>();
		map.put("host", keep_alive_host_1);
		map.put("port", keep_alive_port_1);
		map.put("id", keep_alive_id_1);
		map.put("weight", "1");
		list.add(map);

		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("host", keep_alive_host_2);
		map2.put("port", keep_alive_port_2);
		map2.put("id", keep_alive_id_2);
		map2.put("weight", "1");
		list.add(map2);

		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("host", keep_alive_host_3);
		map3.put("port", keep_alive_port_3);
		map3.put("id", keep_alive_id_3);
		map3.put("weight", "1");
		list.add(map3);

		return list;
	}

}
