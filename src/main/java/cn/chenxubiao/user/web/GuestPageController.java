package cn.chenxubiao.user.web;

import cn.chenxubiao.common.web.GuestBaseController;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Created by chenxb on 17-4-2.
 */
@Controller
public class GuestPageController extends GuestBaseController {

    @Autowired
    private DefaultKaptcha kaptchaProducer;

    @RequestMapping(value = {"index", "/"}, method = RequestMethod.GET)
    public String getIndexPage(Map<String, Object> map) {
        map.put("hello", "bbs");
        return "/index";
    }

    @RequestMapping(value = {"/kaptcha-image","/kaptcha"}, method = RequestMethod.GET)
    public ModelAndView getKaptchaImage(HttpServletRequest request, HttpSession session,
                                        HttpServletResponse response) throws Exception {

        String code = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        System.out.println("验证码: " + code);
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        String capText = kaptchaProducer.createText();
        System.out.println("test:" + capText);
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        BufferedImage bi = kaptchaProducer.createImage(capText);
        System.out.println("kaptcha made :" + session.getAttribute(Constants.KAPTCHA_SESSION_KEY) + "  id" + session.getId());
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
        return null;
    }
}
