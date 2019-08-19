package com.atguigu.springcloud.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.atguigu.springcloud.entities.Dept;

import feign.hystrix.FallbackFactory;

@Component // 不要忘记添加，不要忘记添加
public class DeptClientServiceFallbackFactory implements FallbackFactory<DeptClientService> {
	@Override
	public DeptClientService create(Throwable throwable) {
		return new DeptClientService() {
			@Override
			public Dept get(long id) {
				Dept f = new Dept();
				f.setDeptno(id);
				f.setDname("该ID：" + id + "没有没有对应的信息,Consumer客户端提供的降级信息,此刻服务Provider已经关闭");
				f.setDb_source("no this database in MySQL");

				return f;
			}

			@Override
			public List<Dept> list() {
				List<Dept> list = new ArrayList<>();
				for(int i=0;i<3;i++) {
					Dept f = new Dept();
					f.setDeptno(Long.parseLong(i+""));
					list.add(f);
				}
				
				return list;
			}

			@Override
			public boolean add(Dept dept) {
				return false;
			}
		};
	}
}
