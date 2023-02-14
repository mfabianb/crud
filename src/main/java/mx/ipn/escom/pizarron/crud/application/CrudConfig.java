package mx.ipn.escom.pizarron.crud.application;

import mx.ipn.escom.pizarron.crud.domain.impl.*;
import mx.ipn.escom.pizarron.crud.domain.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class CrudConfig {

    @Bean
    public ClassService classServicePort() {
        return new ClassServiceImpl();
    }

    @Bean
    public GroupService groupServicePort() { return new GroupServiceImpl(); }

    @Bean
    public GroupSubjectService groupSubjectService() { return new GroupSubjectServiceImpl(); }

    @Bean
    public ParticipantService participantService() { return new ParticipantServiceImpl(); }

    @Bean
    public PermissionService permissionService () { return new PermissionServiceImpl(); }

    @Bean
    public SchoolCycleService schoolCycleService() { return new SchoolCycleServiceImpl(); }

    @Bean
    public SubjectService subjectService () { return new SubjectServiceImpl() ; }

    @Bean
    public UserRolService userRolService () { return new UserRolServiceImpl() ; }

    @Bean
    public UserService userServicePort() { return new UserServiceImpl(); }

    @Bean
    public UserStatusService userStatusService() { return new UserStatusServiceImpl(); }

    @Bean
    public EncodeService encodeService(){ return new EncodeServiceImpl(); }

    @Bean
    public AuthenticationService authenticationService(){ return new AuthenticationServiceImpl(); }

    @Bean
    public AuthorizationService authorizationService() { return new AuthorizationServiceImpl(); }

}