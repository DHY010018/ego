package com.ego.manager.service;

import com.ego.common.result.BaseResult;
import com.ego.manager.pojo.GoodsCategory;
import com.ego.manager.vo.GoodsCategoryVo;

import java.util.List;

/**
 * 商品分类Service
 * Created by Administrator on 2019/12/26.
 */

public interface GoogsCategoryService {
    /*
    * 查询所有顶级分类
    * */
    List<GoodsCategory> selectTopList();
    /*
    * 通过父类id查询所有分类
    * */
    List<GoodsCategory>selectByParentId(short parentId);

    /*
    * 保存商品分类
    * */
    BaseResult save(GoodsCategory goodsCategory);
    /*
    * 查询所有分类list
    * */
    List<GoodsCategoryVo>selectAllList();
}
