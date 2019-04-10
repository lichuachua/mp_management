package cn.lichuachua.mp_management.mp_managementserver.service.impl;


import cn.lichuachua.mp_management.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp_management.mp_managementserver.entity.ArticleType;
import cn.lichuachua.mp_management.mp_managementserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp_management.mp_managementserver.exception.ArticleTypeException;
import cn.lichuachua.mp_management.mp_managementserver.service.IArticleTypeService;
import cn.lichuachua.mp_management.mp_managementserver.vo.ArticleTypeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class ArticleTypeServiceImpl extends BaseServiceImpl<ArticleType, Integer> implements IArticleTypeService {
    /**
     * 添加文章类型
     * @param typeName
     */
    @Override
    public void add(String typeName){
        /**
         * 查看是否已经有了该类型
         */
        ArticleType articleType = new ArticleType();
        articleType.setTypeName(typeName);
        Optional<ArticleType> articleTypeOptional = selectOne(Example.of(articleType));
        if (articleTypeOptional.isPresent()){
            throw new ArticleTypeException(ErrorCodeEnum.TYPE_EXIT);
        }
        articleType.setCreatedAt(new Date());
        articleType.setUpdatedAt(new Date());
        save(articleType);
    }

    /**
     * 删除文章类型
     * @param typeId
     */
    @Override
    public void del(Integer typeId){
        /**
         * 查看该文章是否存在
         */
        ArticleType articleType = new ArticleType();
        articleType.setTypeId(typeId);
        Optional<ArticleType> articleTypeOptional = selectOne(Example.of(articleType));
        if (!articleTypeOptional.isPresent()){
            throw new ArticleTypeException(ErrorCodeEnum.TYPE_NO_EXIT);
        }
        delete(articleType);
    }

    /**
     * 查询所有文章类型列表
     * @return
     */
    @Override
    public List<ArticleTypeVO> queryArticleTypeList(){
        List<ArticleType> articleTypeList = selectAll();
        List<ArticleTypeVO> articleTypeVOList = new ArrayList<>();
        for (ArticleType articleType : articleTypeList){
            ArticleTypeVO articleTypeVO = new ArticleTypeVO();
            articleTypeVO.setTypeId(articleType.getTypeId());
            articleTypeVO.setTypeName(articleType.getTypeName());
            BeanUtils.copyProperties(articleType,articleTypeVO);
            articleTypeVOList.add(articleTypeVO);
        }
        return articleTypeVOList;
    }


}
