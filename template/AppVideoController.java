package com.crichubs.rsrc.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.crichubs.rsrc.dtos.mapper.AppVideoMapper;
import com.crichubs.rsrc.dtos.request.entities.AppVideoRequestDto;
import com.crichubs.rsrc.dtos.response.requestdto.RequestBodyResponseDto;
import com.crichubs.rsrc.dtos.response.requestdto.RequestDataCollectionResponseDto;
import com.crichubs.rsrc.entities.AppVideo;
import com.crichubs.rsrc.resources.assemblers.entities.AppVideoResourceAssembler;
import com.crichubs.rsrc.resources.entities.AppVideoResource;
import com.crichubs.rsrc.services.AppVideoService;

@RestController
@RequestMapping("/app-videos")
public class AppVideoController extends BaseController {

	private final AppVideoService appVideoService;
	private final AppVideoMapper appVideoMappper;
	private final AppVideoResourceAssembler appVideoResourceAssembler;

	@Autowired
	public AppVideoController(AppVideoService appVideoService, AppVideoMapper appVideoMappper,
			AppVideoResourceAssembler appVideoResourceAssembler) {
		this.appVideoMappper = appVideoMappper;
		this.appVideoService = appVideoService;
		this.appVideoResourceAssembler = appVideoResourceAssembler;
	}

	@GetMapping("")
	public PagedResources<AppVideoResource> getByQuery(String search, Pageable pageable,
			PagedResourcesAssembler<AppVideo> pagedAssembler) {
		Page<AppVideo> appVideos = appVideoService.getByQuery(search, pageable);
		return pagedAssembler.toResource(appVideos, this.appVideoResourceAssembler);
	}

	@GetMapping("/{id}")
	public AppVideoResource getById(@PathVariable long id) {
		AppVideo appVideo = appVideoService.getById(id);
		return this.appVideoResourceAssembler.toResource(appVideo);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public AppVideoResource create(@Valid @RequestBody AppVideoRequestDto requestDto) {
		AppVideo appVideo = appVideoMappper.getEntity(requestDto);
		appVideoService.save(appVideo);
		return this.appVideoResourceAssembler.toResource(appVideo);
	}

	@DeleteMapping("/{id}")
	public AppVideoResource delete(@PathVariable long id) {
		AppVideo appVideo = appVideoService.getById(id);
		AppVideoResource response = this.appVideoResourceAssembler.toResource(appVideo).forDeletion();
		appVideoService.delete(appVideo);
		return response;
	}

	@PutMapping("/{id}")
	public AppVideoResource update(@PathVariable long id, @Valid @RequestBody AppVideoRequestDto requestDto) {
		AppVideo appVideo = appVideoService.getById(id);
		appVideoMappper.loadEntity(requestDto, appVideo);
		appVideoService.save(appVideo);
		return this.appVideoResourceAssembler.toResource(appVideo);
	}

	@GetMapping("/requests")
	public RequestDataCollectionResponseDto getRequests() {
		RequestDataCollectionResponseDto requestDataCollection = new RequestDataCollectionResponseDto();
		this.addRequestDataOfPost(requestDataCollection);
		return requestDataCollection;
	}

	private AppVideoController addRequestDataOfPost(RequestDataCollectionResponseDto requestDataCollection) {
		RequestBodyResponseDto<AppVideoRequestDto> requestBody = new RequestBodyResponseDto<AppVideoRequestDto>(
				AppVideoRequestDto.class);
		URI uri = linkTo(methodOn(AppVideoController.class).create(null)).toUri();
		requestDataCollection.addRequest(uri, org.springframework.http.HttpMethod.POST, requestBody);
		return this;
	}

}
