package com.crichubs.rsrc.resources.entities;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.http.HttpMethod;

import com.crichubs.rsrc.controllers.AppVideoController;
import com.crichubs.rsrc.dtos.mapper.AppVideoMapper;
import com.crichubs.rsrc.dtos.response.entities.AppVideoResponseDto;
import com.crichubs.rsrc.entities.AppVideo;
import com.crichubs.rsrc.resources.BaseResource;
import com.crichubs.rsrc.resources.ExtendedLink;

import lombok.Getter;

@Getter
public class AppVideoResource extends BaseResource {

	private final AppVideoResponseDto appVideo;

	public AppVideoResource(final AppVideo appVideo, final AppVideoMapper appVideoMapper) {

		this.appVideo = appVideoMapper.getResponseDto(appVideo);
		final long id = appVideo.getId();

		add(new ExtendedLink(linkTo(methodOn(AppVideoController.class).getById(id)).withSelfRel())
				.withMethod(HttpMethod.GET));
		add(new ExtendedLink(linkTo(methodOn(AppVideoController.class).update(id, null)).withSelfRel())
				.withMethod(HttpMethod.PUT));

		this.loadCommonLink();

	}

	public AppVideoResource forDeletion() {
		super.removeLinks();
		this.loadCommonLink();
		return this;
	}

	private void loadCommonLink() {
		add(new ExtendedLink(linkTo(methodOn(AppVideoController.class).create(null)).withRel("create"))
				.withMethod(HttpMethod.POST));
		add(new ExtendedLink(linkTo(methodOn(AppVideoController.class).getRequests()).withRel("requests"))
				.withMethod(HttpMethod.GET));
		add(new ExtendedLink(linkTo(AppVideoController.class).withRel("app-videos"))
				.withMethod(HttpMethod.GET).withSearchableData());
	}
}