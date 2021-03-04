package com.desafio.mercadolivre.shared.security;

//@EnableWebSecurity
//@Configuration
//@Profile("dev")
//public class DevSecurityConfigurations extends WebSecurityConfigurerAdapter{
//
//	// Configurações de autorização: Urls restritas a determinados usuários
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//		.antMatchers("/**").permitAll()
//		.and().csrf().disable();
//	}
//}