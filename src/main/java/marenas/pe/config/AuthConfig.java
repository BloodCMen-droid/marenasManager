 package marenas.pe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AuthConfig {

    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUserDetailsService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
        		.authorizeHttpRequests(auth -> auth
        			    .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
        			    .requestMatchers("/", "/login").permitAll()

        			    // ADMIN
        			    .requestMatchers("/listEmpleados","/newEmpleado","/saveEmpleado",
        			                     "/editEmpleado/**","/deleteEmpleado/**",
        			                     "/listRoles","/newRol","/saveRol",
        			                     "/editRol/**","/deleteRol/**",
        			                     "/listProductos","/newProducto","/saveProducto",
        			                     "/editProducto/**","/deleteProducto/**",
        			                     "/listCategorias","/newCategoria","/saveCategoria",
        			                     "/editCategoria/**","/deleteCategoria/**",
        			                     "/listMesas","/newMesa","/saveMesa",
        			                     "/editMesa/**","/deleteMesa/**","/recetaProducto/**", "/saveReceta", "/deleteReceta/**"
        			    ).hasAuthority("ROLE_ADMIN")

        			    // MESERO
        			    .requestMatchers("/pedidos","/newPedido","/savePedido",
        			                     "/saveDetalle","/detallePedido/**",
        			                     "/agregarDetallePedido","/eliminarDetallePedido/**"
        			    ).hasAuthority("ROLE_MESERO")

        			    /// INVENTARIO
        			    .requestMatchers("/inventario/**", "/listInsumos", "/newInsumo", 
        		                 "/saveInsumo", "/editInsumo/**", "/deleteInsumo/**",
        		                 "/addStock/**", "/saveStock")
        	         	.hasAnyAuthority("ROLE_INVENTARIO", "ROLE_ADMIN")
        			    
        		 	    //COCINERO
        			    .requestMatchers("/cocina", "/prepararDetalle/**")
        			    .hasAuthority("ROLE_COCINERO")
        			    
        			  //CAJERO
        			    .requestMatchers("/cajero", "/cajero/**")
        			    .hasAuthority("ROLE_CAJERO")
        			    
        			    

        			    .anyRequest().authenticated()
        			)
                // NUEVO: Spring Security maneja el login automáticamente
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/index", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider(userDetailsService());
        //authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}