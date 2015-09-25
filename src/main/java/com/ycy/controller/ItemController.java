package com.ycy.controller;


import com.ycy.dto.ItemsCustom;
import com.ycy.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/9/17 0017.
 */
@Controller
@RequestMapping("/items")
public class ItemController {

    //注入service
    @Autowired
    private ItemsService itemsService;

    /**
     * 商品展示
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     * @throws Exception
     */
    @RequestMapping("/queryItems")
    public ModelAndView queryItems(javax.servlet.http.HttpServletRequest httpServletRequest,
                                   javax.servlet.http.HttpServletResponse httpServletResponse) throws Exception {
        //如果是转发：httpServletRequest的数据是可以共享的

        //商品列表
        List<ItemsCustom> itemsList = itemsService.findtemsList(null);
        //创建modelAndView准备填充数据、设置视图
        ModelAndView modelAndView = new ModelAndView();
        //填充数据
        modelAndView.addObject("itemsList", itemsList);
        //视图
        modelAndView.setViewName("order/itemsList");

        return modelAndView;
    }

    /**
     * 修改商品信息1.1
     * @return
     * @throws Exception
     */
    @RequestMapping("/editItems")
    public ModelAndView editItems(@RequestParam(value="id", required=true,defaultValue = "") Integer id ) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        ItemsCustom itemsCustom = itemsService.getItemsById(id);
        modelAndView.addObject("item",itemsCustom);
        modelAndView.setViewName("order/editItem");
        return modelAndView;
    }

    /**
     * 修改商品信息1.2
     * @return
     * @throws Exception
     */
    @RequestMapping("/editItems2")
    public String editItems(Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        ItemsCustom itemsCustom = itemsService.getItemsById(1);
        model.addAttribute("item", itemsCustom);
        return "order/editItem";
    }

    /**
     * 修改商品信息1.3
     * @return
     * @throws Exception
     */
    @RequestMapping("/editItems3")
    public void editItems(javax.servlet.http.HttpServletRequest httpServletRequest,
                          javax.servlet.http.HttpServletResponse httpServletResponse) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        ItemsCustom itemsCustom = itemsService.getItemsById(1);
        httpServletRequest.setAttribute("item", itemsCustom);
        httpServletRequest.getRequestDispatcher("/pages/jsp/order/editItem.jsp").forward(httpServletRequest,httpServletResponse);
        /**
         * 使用此方法，容易输出json、xml格式的数据：
         通过response指定响应结果，例如响应json数据如下：
         response.setCharacterEncoding("utf-8");
         response.setContentType("application/json;charset=utf-8");
         response.getWriter().write("json串");
         */
    }

    /**
     * 修改商品属性
     * @return
     */
    @RequestMapping("/editItemSubmit")
    public String editItemSubmit(Integer id, ItemsCustom itemsCustom) throws Exception {
        //重定向  request数据无法共享，url地址栏会发生变化的 页面地址：editItemSubmit
        itemsService.updateItem(id, itemsCustom);
        return  "redirect:queryItems";
        //转发   request数据可以共享，url地址栏不会变化 页面地址：queryItems
        //return  "forward:queryItems";
    }
    //自定义编辑器初级版本
    /**
     * 注册属性编辑器(字符串转换为日期)
     */
//    @InitBinder
//    public void initBinder(WebDataBinder binder) throws Exception {
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss"),true));
//        //System.out.println("init binder =======================");
//        binder.registerCustomEditor(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, false));
//        binder.registerCustomEditor(Integer.class, null,new CustomNumberEditor(Integer.class, null, true));
//        binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(Long.class, null, true));
//        binder.registerCustomEditor(Float.class, new CustomNumberEditor(Float.class, true));
//        binder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, true));
//        binder.registerCustomEditor(BigInteger.class, new CustomNumberEditor(BigInteger.class, true));
//    }
//











    @RequestMapping("/testRequestParam")
    public  void testRequestParam(@RequestParam(value="queryName", required=true,defaultValue = "") String name ){
    // 上面的对传入参数指定为queryName，如果前端不传queryName参数名，会报错
    // required=false表示不传的话，会给参数赋值为null，required=true就是必须要有
    }
    @RequestMapping("/testModel")
    public  String testModel(Model model )throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        ItemsCustom itemsCustom = itemsService.getItemsById(1);
        model.addAttribute("item", itemsCustom);
        return "order/editItem";
    }
    @RequestMapping("/testHttpSession")
    public  void testHttpSession(HttpSession  httpSession )throws Exception {
        httpSession.getAttribute("username");
    }
    @RequestMapping("/testHttprequest")
    public  void testHttprequest(javax.servlet.http.HttpServletRequest httpServletRequest,
                                 javax.servlet.http.HttpServletResponse httpServletResponse)throws Exception {
        httpServletRequest.setAttribute("obj", null);
        httpServletRequest.getRequestDispatcher("/pages/jsp/XXX/XXXX.jsp").forward(httpServletRequest,httpServletResponse);
    }
}
