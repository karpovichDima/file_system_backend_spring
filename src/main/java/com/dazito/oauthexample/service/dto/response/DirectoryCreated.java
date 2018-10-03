package com.dazito.oauthexample.service.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DirectoryCreated implements Serializable {

    private final static long serialVersionUID = 236152523;

    private Long parentId;
    private String name;
}
