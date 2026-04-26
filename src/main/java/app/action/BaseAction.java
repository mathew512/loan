package app.action;

import app.framework.*;
import app.utility.helper.EnumConverter;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class BaseAction<T> extends HttpServlet {

    private GenericDao<T, Integer> genericDao;

    // ================= INIT DAO SAFELY =================
    @Override
    public void init() throws ServletException {
        this.genericDao = new GenericDao<>(getType());
    }

    // ================= FORM SERIALIZATION =================
    public T serializeForm(Map<String, String[]> requestMap) {
        try {
            T instance = getType().getDeclaredConstructor().newInstance();

            ConvertUtilsBean convertUtils = new ConvertUtilsBean() {
                @Override
                public Object convert(String value, Class<?> targetType) {

                    try {
                        if (targetType.isEnum()) {
                            return new EnumConverter().convert(targetType, value);
                        }

                        if (targetType == Date.class) {
                            return new SimpleDateFormat("yyyy-MM-dd").parse(value);
                        }

                        if (targetType == BigDecimal.class) {
                            return new BigDecimal(value);
                        }

                        return super.convert(value, targetType);

                    } catch (Exception e) {
                        throw new RuntimeException("Conversion failed: " + value, e);
                    }
                }
            };

            BeanUtilsBean beanUtils = new BeanUtilsBean(convertUtils);
            beanUtils.populate(instance, requestMap);

            return instance;

        } catch (Exception e) {
            throw new RuntimeException("Form serialization error", e);
        }
    }

    // ================= POST (SAVE DATA) =================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            T entity = serializeForm(req.getParameterMap());

            genericDao.save(entity);

            LoanTable table = getType().getAnnotation(LoanTable.class);

            String redirectUrl;

            if (table != null) {
                redirectUrl = req.getContextPath() + "/" + clean(table.tableUrl());
            } else {
                redirectUrl = req.getContextPath() + "/home";
            }

            // 🔥 remove duplicate slashes like /Loan////list
            redirectUrl = redirectUrl.replaceAll("/{2,}", "/");

            resp.sendRedirect(redirectUrl);

        } catch (Exception e) {
            throw new ServletException("Error saving entity", e);
        }
    }

    // ================= GET (SHOW FORM) =================
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute(PageContent.CONTENT.name(),
                LoanFramework.htmlForm(getType()));

        RequestDispatcher rd = req.getRequestDispatcher("/app_page");
        rd.include(req, resp);
    }

    // ================= TYPE RESOLUTION =================
    @SuppressWarnings("unchecked")
    public Class<T> getType() {
        ParameterizedType type =
                (ParameterizedType) getClass().getGenericSuperclass();

        return (Class<T>) type.getActualTypeArguments()[0];
    }

    // ================= LIST DATA =================
    public List<T> returnData() {
        return genericDao.findAll();
    }

    // ================= CLEAN URL =================
    private String clean(String url) {
        if (url == null) return "";
        return url.replaceAll("^/+", "");
    }
}