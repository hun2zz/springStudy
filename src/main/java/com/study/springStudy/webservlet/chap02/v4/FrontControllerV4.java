package com.study.springStudy.webservlet.chap02.v4;

import com.study.springStudy.webservlet.Model;
import com.study.springStudy.webservlet.ModelAndView;
import com.study.springStudy.webservlet.chap02.v3.controller.ControllerV3;
import com.study.springStudy.webservlet.chap02.v4.controller.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/chap02/v4/*")
public class FrontControllerV4 extends HttpServlet {
    private Map<String, ControllerV4> controllerMap = new HashMap<>();

    public FrontControllerV4() {
        controllerMap.put("/chap02/v4/join", new JoinController());
        controllerMap.put("/chap02/v4/save", new SaveController());
        controllerMap.put("/chap02/v4/show", new ShowController());
        controllerMap.put("/chap02/v4/delete", new DeleteController());
        controllerMap.put("/chap02/v4/detail", new DetailController());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        //요청에 맞는 적당한 컨트롤러 객체를 맵에서 꺼내기
        ControllerV4 controller = controllerMap.get(uri);
        // 요청 파라ㅏ미터를 전부 읽어서 맵에 담아 리턴하는 메서드 호출
        // 요청 파라미터 : 클라이언트가 서버로 전달한 데이터
        Map<String, String> parameterMap = createParamMap(req);
        Model model = new Model();
        String viewName = controller.process(parameterMap, model);

        ModelAndView mv = new ModelAndView(viewName);
        mv.setModel(model);
        // model 데이터 jsp로 보내기
        modelToView(req, mv);
        // view 렌더링
        mv.render(req, resp);
    }

    private void modelToView(HttpServletRequest req, ModelAndView mv) {
        Map<String, Object> modelMap = mv.getModel().getModelMap();
        for (String ket : modelMap.keySet()) {
            req.setAttribute(ket, modelMap.get(ket));
        }
    }

    private Map<String, String> createParamMap(HttpServletRequest req) {
        Enumeration<String> parameterNames = req.getParameterNames();

        Map<String, String> paramMap = new HashMap<>();
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String value = req.getParameter(key);
            paramMap.put(key, value);
        }
        return paramMap;

    }
}
