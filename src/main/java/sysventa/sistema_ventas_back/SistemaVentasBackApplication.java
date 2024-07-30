package sysventa.sistema_ventas_back;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import sysventa.sistema_ventas_back.entities.Proveedor;
import sysventa.sistema_ventas_back.repository.ProveedorRepository;

@SpringBootApplication
public class SistemaVentasBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaVentasBackApplication.class, args);
	}

	// @Bean
	// CommandLineRunner init(ProveedorRepository proveedorRepository) {
	// return args -> {
	// Proveedor proveedor1 = Proveedor.builder()
	// .nombre("Proveedor1")
	// .correo("proveedor1@gmail.com")
	// .direccion("Av Proveedor1 133")
	// .estado(true)
	// .imagen("Imagen1")
	// .ruc("123456")
	// .build();
	// Proveedor proveedor2 = Proveedor.builder()
	// .nombre("proveedor2")
	// .correo("proveedor2@gmail.com")
	// .direccion("Av proveedor2 133")
	// .estado(true)
	// .imagen("Imagen2")
	// .ruc("123456")
	// .build();
	// Proveedor proveedor3 = Proveedor.builder()
	// .nombre("proveedor3")
	// .correo("proveedor3@gmail.com")
	// .direccion("Av proveedor3 133")
	// .estado(true)
	// .imagen("Imagen1")
	// .ruc("123456")
	// .build();
	// Proveedor proveedor4 = Proveedor.builder()
	// .nombre("proveedor4")
	// .correo("proveedor4@gmail.com")
	// .direccion("Av proveedor4 133")
	// .estado(true)
	// .imagen("Imagen4")
	// .ruc("123456")
	// .build();
	// Proveedor proveedor5 = Proveedor.builder()
	// .nombre("proveedor5")
	// .correo("proveedor5@gmail.com")
	// .direccion("Av proveedor5 133")
	// .estado(true)
	// .imagen("Imagen1")
	// .ruc("123456")
	// .build();
	// Proveedor proveedor6 = Proveedor.builder()
	// .nombre("proveedor6")
	// .correo("proveedor6@gmail.com")
	// .direccion("Av proveedor6 133")
	// .estado(true)
	// .imagen("Imagen1")
	// .ruc("123456")
	// .build();
	// Proveedor proveedor7 = Proveedor.builder()
	// .nombre("Provproveedor7eedor1")
	// .correo("proveedor7@gmail.com")
	// .direccion("Av proveedor7 133")
	// .estado(true)
	// .imagen("Imagen1")
	// .ruc("123456")
	// .build();
	// Proveedor proveedor8 = Proveedor.builder()
	// .nombre("proveedor8")
	// .correo("proveedor8@gmail.com")
	// .direccion("Av proveedor8 133")
	// .estado(true)
	// .imagen("Imagen1")
	// .ruc("123456")
	// .build();
	// Proveedor proveedor9 = Proveedor.builder()
	// .nombre("proveedor9")
	// .correo("proveedor9@gmail.com")
	// .direccion("Av proveedor9 133")
	// .estado(true)
	// .imagen("Imagen1")
	// .ruc("123456")
	// .build();
	// Proveedor proveedor10 = Proveedor.builder()
	// .nombre("proveedor10")
	// .correo("proveedor10@gmail.com")
	// .direccion("Av proveedor10 133")
	// .estado(true)
	// .imagen("Imagen1")
	// .ruc("123456")
	// .build();
	// Proveedor proveedor11 = Proveedor.builder()
	// .nombre("proveedor11")
	// .correo("proveedor11@gmail.com")
	// .direccion("Av proveedor11 133")
	// .estado(true)
	// .imagen("proveedor11")
	// .ruc("123456")
	// .build();

	// proveedorRepository.saveAll(List.of(proveedor1 , proveedor2 , proveedor3 ,
	// proveedor4 , proveedor5 , proveedor6 , proveedor7 , proveedor8 ,proveedor9 ,
	// proveedor10 , proveedor11));
	// };
}
