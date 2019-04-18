package cn.lichuachua.mp_management.mp_managementserver.service;


import cn.lichuachua.mp_management.core.support.service.IBaseService;
import cn.lichuachua.mp_management.mp_managementserver.entity.ArticleType;
import cn.lichuachua.mp_management.mp_managementserver.vo.ArticleTypeVO;

import java.util.List;


public interface IArticleTypeService extends IBaseService<ArticleType, Integer> {


    /**
     * 添加文章类型
     * @param typeName
     */
    void add(String typeName);

    /**
     * 删除文章类型
     * @param typeId
     */
    void del(Integer typeId);


    /**
     * 查询所有文章类型列表
     * @return
     */
    List<ArticleTypeVO> queryArticleTypeList();

    /**
     * 根据typeId和articelTypeId或者announcementTypeId查询出typeName;
     */
    String queryTypeName(Integer typeId);


}
