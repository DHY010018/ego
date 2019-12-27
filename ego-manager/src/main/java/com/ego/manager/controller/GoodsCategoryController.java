package com.ego.manager.controller;

import com.ego.common.result.BaseResult;
import com.ego.manager.pojo.GoodsCategory;
import com.ego.manager.pojo.GoodsCategoryExample;
import com.ego.manager.service.impl.GoodsCateServceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品分类
 * Created by Administrator on 2019/12/25.
 */
@Controller
@RequestMapping("category")
public class GoodsCategoryController {

    @Autowired
    private GoodsCateServceImpl goodsCateServce;
    /*
    * 跳转列表页面
    * */
    @RequestMapping("list")
    public String list(Model model) {
        model.addAttribute("gcvList",goodsCateServce.selectAllList());
        return "goods/category/category-list";
    }

        /*
        * 跳转新增页面
        * */
    @RequestMapping("add")
    public String add(Model model){
        //查询所有顶级分类
        model.addAttribute("gcList",goodsCateServce.selectTopList());
        return "goods/category/category-add";
    }
    //商品分类查询  根据父类id查询所有分类
    @RequestMapping("/{parentId}")
    @ResponseBody
    public List<GoodsCategory>selectByParentId(@PathVariable short parentId){
        return goodsCateServce.selectByParentId(parentId);
    }
    /*
    * 保存
    * */
    @RequestMapping("save")
    @ResponseBody
    public BaseResult save(GoodsCategory goodsCategory){
        return goodsCateServce.save(goodsCategory);
    }
}

