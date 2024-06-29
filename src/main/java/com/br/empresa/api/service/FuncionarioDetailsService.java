package com.br.empresa.api.service;

//@Service
//public class FuncionarioDetailsService implements UserDetailsService {
//
//    @Autowired
//    private FuncionarioRepository funcionarioRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Funcionario funcionario = funcionarioRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("Funcionário não encontrado com o email: " + email));
//
//        return User.builder()
//                .username(funcionario.getEmail())
//                .password(funcionario.getSenha())
//                .roles("USER")
//                .build();
//    }
//}
