module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
	requires javafx.base;
	requires mssql.jdbc;
	

    opens org.example to javafx.fxml;
    exports org.example;
}