package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootApplication
@RequiredArgsConstructor
public class ComponentesFinalApplication implements CommandLineRunner {

	private final DataSource dataSource;

  public static void main(String[] args) {
		SpringApplication.run(ComponentesFinalApplication.class, args);
	}

  @Override
  public void run(String... args) throws Exception {
    System.out.println("Verficando la conexion a la base de datos");
    try (Connection connection = dataSource.getConnection()) {
      System.out.println("Conexión exitosa = " + connection.getMetaData().getDatabaseProductName());
    } catch (Exception e) {
      System.out.println("Error de conexión: " + e.getMessage());
      throw new RuntimeException("Fallo en la conexión a la base de datos", e);
    }
  }
}
