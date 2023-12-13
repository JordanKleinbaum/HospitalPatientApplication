module com.example {
    requires javafx.controls;

    exports com.example;

    requires transitive javafx.graphics;
    requires transitive com.oracle.database.jdbc;
    requires transitive java.sql;
    requires java.naming;

}
