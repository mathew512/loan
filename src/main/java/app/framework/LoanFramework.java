package app.framework;

import app.utility.helper.ClassScanner;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class LoanFramework {

    // ================= BASE CONTEXT =================
    private static final String CONTEXT = "/Loan";

    // ================= SAFE URL BUILDER =================
    private static String url(String path) {
        if (path == null) return CONTEXT;
        return CONTEXT + "/" + path.replaceAll("^/+", "").replaceAll("/{2,}", "/");
    }

    // ================= FORM GENERATOR =================
    public static String htmlForm(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(LoanForm.class))
            return "";

        LoanForm formAnnot = clazz.getAnnotation(LoanForm.class);

        StringBuilder formBuilder = new StringBuilder();

        formBuilder.append("<div class='container'><div class='card'>");
        formBuilder.append("<h2>").append(formAnnot.label()).append("</h2>");

        formBuilder.append("<form method='")
                .append(formAnnot.method())
                .append("' action='")
                .append(url(formAnnot.actionUrl()))
                .append("'>");

        for (Field field : clazz.getDeclaredFields()) {

            if (!field.isAnnotationPresent(LoanFormField.class))
                continue;

            LoanFormField meta = field.getAnnotation(LoanFormField.class);

            String name = meta.name().isEmpty() ? field.getName() : meta.name();
            String inputType = resolveInputType(field.getType());

            formBuilder.append("<div class='form-group'>");

            formBuilder.append("<label>")
                    .append(meta.label())
                    .append(":</label>");

            formBuilder.append("<input type='")
                    .append(inputType)
                    .append("' name='")
                    .append(name)
                    .append("' placeholder='Enter ")
                    .append(meta.placeholder())
                    .append("' required />");

            formBuilder.append("</div>");
        }

        formBuilder.append("<button type='submit' class='btn'>Submit</button>");
        formBuilder.append("</form>");

        if (clazz.isAnnotationPresent(LoanTable.class)) {
            LoanTable table = clazz.getAnnotation(LoanTable.class);

            formBuilder.append("<a href='")
                    .append(url(table.tableUrl()))
                    .append("' class='back-link'>&larr; List ")
                    .append(table.label())
                    .append("</a>");
        }

        formBuilder.append("</div></div>");

        return formBuilder.toString();
    }

    // ================= TABLE GENERATOR =================
    public static String htmlTable(Class<?> clazz, List<?> dataList) {

        if (!clazz.isAnnotationPresent(LoanTable.class))
            return "";

        LoanTable tableAnnot = clazz.getAnnotation(LoanTable.class);

        StringBuilder tableBuilder = new StringBuilder();

        List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(LoanTableCol.class))
                .peek(f -> f.setAccessible(true))
                .collect(Collectors.toList());

        tableBuilder.append("<div class='container'><div class='card'>");
        tableBuilder.append("<h2>")
                .append(tableAnnot.label())
                .append(" Registered</h2>");

        tableBuilder.append("<table>");

        // ================= HEADER =================
        tableBuilder.append("<thead><tr>");
        for (Field field : fields) {
            tableBuilder.append("<th>")
                    .append(field.getName())
                    .append("</th>");
        }
        tableBuilder.append("</tr></thead>");

        // ================= BODY =================
        tableBuilder.append("<tbody>");

        for (Object obj : dataList) {
            tableBuilder.append("<tr>");

            for (Field field : fields) {
                try {
                    Object value = field.get(obj);

                    tableBuilder.append("<td>")
                            .append(escapeHtml(String.valueOf(value)))
                            .append("</td>");

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            tableBuilder.append("</tr>");
        }

        tableBuilder.append("</tbody></table>");

        tableBuilder.append("<a href='")
                .append(url(tableAnnot.registerUrl()))
                .append("' class='back-link'>&larr; Register ")
                .append(tableAnnot.label())
                .append("</a>");

        tableBuilder.append("</div></div>");

        return tableBuilder.toString();
    }

    // ================= MENU GENERATOR =================
    public static String generateMenu() {
        Set<Class<?>> entities = ClassScanner.scanForMenu("app.model");

        return entities.stream()
                .filter(c -> c.isAnnotationPresent(LoanMenu.class))
                .map(c -> {
                    LoanMenu menu = c.getAnnotation(LoanMenu.class);
                    return "<a href='" + url(menu.url()) + "'>" + menu.label() + "</a>";
                })
                .collect(Collectors.joining("<br/>"));
    }

    // ================= INPUT TYPE RESOLVER =================
    private static String resolveInputType(Class<?> type) {

        if (type == int.class || type == Integer.class) return "number";
        if (type == double.class || type == Double.class) return "number";
        if (type == long.class || type == Long.class) return "number";
        if (type == boolean.class || type == Boolean.class) return "checkbox";

        if (type == java.util.Date.class) return "date";

        return "text";
    }

    // ================= HTML ESCAPE =================
    private static String escapeHtml(String input) {
        if (input == null) return "";
        return input
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}