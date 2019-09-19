package com.crichubs.rsrc.resources.assemblers.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crichubs.rsrc.controllers.AppVideoController;
import com.crichubs.rsrc.dtos.mapper.AppVideoMapper;
import com.crichubs.rsrc.entities.AppVideo;
import com.crichubs.rsrc.resources.assemblers.BaseResourceAssembler;
import com.crichubs.rsrc.resources.entities.AppVideoResource;

@Component
public class AppVideoResourceAssembler extends BaseResourceAssembler<AppVideo, AppVideoResource> {

	private final AppVideoMapper appVideoMapper;

	@Autowired
	public AppVideoResourceAssembler(AppVideoMapper appVideoMapper) {
		super(AppVideoController.class, AppVideoResource.class);
		this.appVideoMapper = appVideoMapper;
	}

	@Override
	public AppVideoResource toResource(AppVideo entity) {
		return new AppVideoResource(entity, appVideoMapper);
	}

}