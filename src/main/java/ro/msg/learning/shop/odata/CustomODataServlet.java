package ro.msg.learning.shop.odata;

import lombok.RequiredArgsConstructor;
import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.core.servlet.ODataServlet;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomODataServlet extends ODataServlet {

    private final CustomServiceFactory customServiceFactory;

    @Override
    protected ODataServiceFactory getServiceFactory(HttpServletRequest request) {
        return customServiceFactory;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws IOException {

        req.setAttribute(CustomServiceFactory.FACTORY_INSTANCE_LABEL, customServiceFactory);
        super.service(req, res);

    }



}
