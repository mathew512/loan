package app.utility.helper;

import app.framework.LoanMenu;
import app.framework.LoanTable;

import org.reflections.Reflections;

import java.util.HashSet;
import java.util.Set;

public class ClassScanner {

    // ================= TABLE SCANNER =================
    public static Set<Class<?>> scanForTables(String basePackage) {

        Reflections reflections = new Reflections(basePackage);

        Set<Class<?>> annotatedClasses =
                reflections.getTypesAnnotatedWith(LoanTable.class);

        return new HashSet<>(annotatedClasses);
    }

    // ================= MENU SCANNER =================
    public static Set<Class<?>> scanForMenu(String basePackage) {

        Reflections reflections = new Reflections(basePackage);

        Set<Class<?>> annotatedClasses =
                reflections.getTypesAnnotatedWith(LoanMenu.class);

        return new HashSet<>(annotatedClasses);
    }
}