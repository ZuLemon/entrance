//package org.lemon.entrance.aop;
//import com.alibaba.fastjson.JSON;
//import org.lemon.entrance.model.ReturnModel;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.annotation.Resource;
//
//
//public class UserInterceptor implements HandlerInterceptor  {
//    @Resource(name = "adminService")
//    private ReturnModel returnModel;
//    //拦截前处理
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
////        Object sessionObj = request.getSession().getAttribute(ADMINSESSION);
////        if(sessionObj!=null) {
////            return true;
////        }
//        returnModel=new ReturnModel();
//        String userId=request.getParameter("userId");
//        String passwd=request.getParameter("passwd");
//        if(userId!=null&&passwd!=null) {
//
//        }else {
//            returnModel.setObject("用户名或密码为空");
//        }
//        response.setContentType("application/json");
//        response.getWriter().write(JSON.toJSONString(returnModel));
//        return false;
//    }
//    //拦截后处理
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView mav) throws Exception {
//    }
//    //全部完成后处理
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception e) throws Exception {
//    }
//}
//
