package com.dazito.oauthexample.utils.converter;

import com.dazito.oauthexample.model.Content;
import com.dazito.oauthexample.service.dto.request.ContentUpdateDto;
import com.dazito.oauthexample.service.dto.response.ContentUpdatedDto;
import com.dazito.oauthexample.utils.converter.config.AutoConverter;

public class ContentUpdateToContentUpdated extends AutoConverter<Content, ContentUpdatedDto> {
}
