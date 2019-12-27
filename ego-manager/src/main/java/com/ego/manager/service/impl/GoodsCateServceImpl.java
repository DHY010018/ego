package com.ego.manager.service.impl;

import com.ego.common.result.BaseResult;
import com.ego.manager.mapper.GoodsCategoryMapper;
import com.ego.manager.pojo.GoodsCategory;
import com.ego.manager.pojo.GoodsCategoryExample;
import com.ego.manager.service.GoogsCategoryService;
import com.ego.manager.vo.GoodsCategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/12/26.
 */
@Service
public class GoodsCateServceImpl implements GoogsCategoryService {
    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;
    //顶级菜单查询
    @Override
    public List<GoodsCategory> selectTopList() {
        GoodsCategoryExample example=new GoodsCategoryExample();
        example.createCriteria().andParentIdEqualTo((short) 0);
        return goodsCategoryMapper.selectByExample(example);
    }

    /*
    *
    * 根据父类查询所有分类
    * */
    @Override
    public List<GoodsCategory> selectByParentId(short parentId) {
        GoodsCategoryExample example=new GoodsCategoryExample();
        example.createCriteria().andParentIdEqualTo(parentId);
        return goodsCategoryMapper.selectByExample(example);
    }

    @Override
    public BaseResult save(GoodsCategory goodsCategory) {
        //保存
        int result = goodsCategoryMapper.insertSelective(goodsCategory);
        return result>0 ? BaseResult.success():BaseResult.error();
    }

    @Override
    public List<GoodsCategoryVo> selectAllList() {
        //顶级分类list
        List<GoodsCategoryVo>gcvList01=new ArrayList<>();
        //创建查询对象
        GoodsCategoryExample example= new GoodsCategoryExample();
        //设置查询条件
        example.createCriteria().andParentIdEqualTo((short) 0).andLevelEqualTo((byte) 1);
        //查询所有顶级分类
        List<GoodsCategory> gcList01 = goodsCategoryMapper.selectByExample(example);
        for (GoodsCategory gc01:gcList01) {
            GoodsCategoryVo gcv01=new GoodsCategoryVo();
            //拷贝对象
            BeanUtils.copyProperties(gc01,gcv01);
            //清除查询条件
            example.clear();
            //设置2级分类查询条件
            example.createCriteria().andParentIdEqualTo(gc01.getId()).andLevelEqualTo((byte) 2);
            List<GoodsCategoryVo> gcvList02=new ArrayList<>();
            //查询所有二级分类
            List<GoodsCategory> gcList02 = goodsCategoryMapper.selectByExample(example);
            for (GoodsCategory gc02:gcList02) {
                GoodsCategoryVo gcv02 = new GoodsCategoryVo();
                //对象拷贝
                BeanUtils.copyProperties(gc02,gcv02);
                //清除查询条件
                example.clear();
                //设置三级查询条件
                example.createCriteria().andParentIdEqualTo(gc02.getId()).andLevelEqualTo((byte) 3);
                List<GoodsCategoryVo> gcvList03=new ArrayList<>();
                //查询所有三级分类
                List<GoodsCategory> gcList03 = goodsCategoryMapper.selectByExample(example);
                for (GoodsCategory gc03:gcList03) {
                    GoodsCategoryVo gcv03 = new GoodsCategoryVo();
                    //对象拷贝
                    BeanUtils.copyProperties(gc03,gcv03);
                    //将三级分类放入三级list集合中
                    gcvList03.add(gcv03);
                }
                //把所有三级分类 放入二级分类对象的list属性中
                gcv02.setChildrenList(gcvList03);
                gcvList02.add(gcv02);
            }
            //把所有二级分类放入一级分类对象的list属性中
            gcv01.setChildrenList(gcvList02);
            gcvList01.add(gcv01);
        }
        return gcvList01;
    }
}
