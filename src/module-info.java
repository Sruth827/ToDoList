module ToDoList {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.base;
	requires javafx.graphics;
	requires com.fasterxml.jackson.annotation;
	requires com.fasterxml.jackson.core;
	requires com.fasterxml.jackson.databind;
	opens application to javafx.graphics, javafx.fxml, com.fasterxml.jackson.databind;
}
