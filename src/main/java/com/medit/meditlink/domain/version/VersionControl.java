package com.medit.meditlink.domain.version;

import com.medit.meditlink.domain.version.model.VersionEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/version")
public class VersionControl {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private VersionService versionService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getVersion() {

        VersionEntity version = versionService.getVersion();
        return new ResponseEntity<>(modelMapper.map(version, VersionEntity.class), HttpStatus.OK);
    }
}

