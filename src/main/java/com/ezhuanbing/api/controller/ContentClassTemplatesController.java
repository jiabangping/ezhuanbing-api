package com.ezhuanbing.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezhuanbing.api.authorize.APIAccessType;
import com.ezhuanbing.api.authorize.APIType;
import com.ezhuanbing.api.authorize.ApiControl;
import com.ezhuanbing.api.dao.mybatis.model.ContentClassTemplates;
import com.ezhuanbing.api.dao.mybatis.model.templateManager.ContentClassQuestions;
import com.ezhuanbing.api.service.ContentClassTemplatesService;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/mbClass")
public class ContentClassTemplatesController {
	@Autowired
	private ContentClassTemplatesService mbContentClassTemplatesService;
	
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value = "/getClassByTid")
	public PageInfo<ContentClassTemplates> getClassByTid(@RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
	          @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize,@RequestParam("tId") Integer tId){
		List<ContentClassTemplates> list = mbContentClassTemplatesService.getAllClassByTid(pageIndex,pageSize,tId);
		return new PageInfo<ContentClassTemplates>(list);
	}
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value = "/deleteById/{id}")
	public int deleteById(@PathVariable("id") Integer id){
		return mbContentClassTemplatesService.deleteById(id);
	}
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value = "/getContClassTree/{id}")
    public List<ContentClassQuestions> getContClassTree(@PathVariable("id") Integer id){
        List<ContentClassQuestions> contClassTree = mbContentClassTemplatesService.getContentClassTree(id);
        return contClassTree;
    }
}
