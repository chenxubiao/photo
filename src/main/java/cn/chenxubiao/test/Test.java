package cn.chenxubiao.test;

import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.utils.Im4javaUtil;
import cn.chenxubiao.common.utils.StringUtil;
import cn.chenxubiao.common.web.GuestBaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxb on 17-5-13.
 */
@RestController
public class Test extends GuestBaseController {

    @RequestMapping(value = "/admin/font/list")
    public ResponseEntity test() {

        GraphicsEnvironment eq = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontsName = eq.getAvailableFontFamilyNames();
        List<String> stringList = new ArrayList<>();
        for (String s : fontsName) {
            stringList.add(s);
        }
        return ResponseEntity.success().set("data", stringList);
    }

    @RequestMapping(value = "/admin/setting/update")
    public ResponseEntity set(String fontString) {
        if (StringUtil.isNotBlank(fontString)) {
            Im4javaUtil.setFontString(fontString);
        }
        return ResponseEntity.success().set("data", fontString);
    }
}
