package br.spi.edx.auth.extensions.form;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.keycloak.Config;
import org.keycloak.authentication.FormAction;
import org.keycloak.authentication.FormActionFactory;
import org.keycloak.authentication.FormContext;
import org.keycloak.authentication.ValidationContext;
import org.keycloak.authentication.forms.RegistrationPage;
import org.keycloak.events.Details;
import org.keycloak.events.Errors;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.models.ModelDuplicateException;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.UserModel.RequiredAction;
import org.keycloak.models.utils.FormMessage;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.services.messages.Messages;
import org.keycloak.services.validation.Validation;

import br.spi.edx.auth.custom.dto.Pessoa;
import br.spi.edx.auth.custom.roles.RolesProfileEnum;
import br.spi.edx.auth.util.ExternalProviderService;


public class PlaceholderRegistrationProfile implements FormAction, FormActionFactory {

    public static final String PROVIDER_ID = "spi-registration-profile"; // MAX 36 chars !!!!
    private final String CODE_AUTHENTIC = "codeAuthentic" ;
    private ExternalProviderService externalProviderService = ExternalProviderService.build();
   

    @Override
    public String getHelpText() {
        return "Validates email, first name, and last name attributes and stores them in user data.";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return null;
    }

    @Override
    public void validate(ValidationContext context) {
        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
        List<FormMessage> errors = new ArrayList<>();

        context.getEvent().detail(Details.REGISTER_METHOD, "form");
        String eventError = Errors.INVALID_REGISTRATION;

        if (Validation.isBlank(formData.getFirst((RegistrationPage.FIELD_FIRST_NAME)))) {
            errors.add(new FormMessage(RegistrationPage.FIELD_FIRST_NAME, Messages.MISSING_FIRST_NAME));
        }

        if (Validation.isBlank(formData.getFirst((RegistrationPage.FIELD_LAST_NAME)))) {
            errors.add(new FormMessage(RegistrationPage.FIELD_LAST_NAME, Messages.MISSING_LAST_NAME));
        }
        String codeAuthentic = formData.getFirst("user.attributes."+CODE_AUTHENTIC);
        if (Validation.isBlank(formData.getFirst("user.attributes."+CODE_AUTHENTIC))) {
            errors.add(new FormMessage(CODE_AUTHENTIC, "Codigo de verificação"));
        } else if (!externalProviderService.verifieldCodigo(codeAuthentic)) {
        	errors.add(new FormMessage(CODE_AUTHENTIC, "Codigo de verificação"));
        }
        
        String email = formData.getFirst(Validation.FIELD_EMAIL);
        boolean emailValid = true;
        if (Validation.isBlank(email)) {
            errors.add(new FormMessage(RegistrationPage.FIELD_EMAIL, Messages.MISSING_EMAIL));
            emailValid = false;
        } else if (!Validation.isEmailValid(email)) {
            context.getEvent().detail(Details.EMAIL, email);
            errors.add(new FormMessage(RegistrationPage.FIELD_EMAIL, Messages.INVALID_EMAIL));
            emailValid = false;
        }

        if (emailValid && !context.getRealm().isDuplicateEmailsAllowed()) {
            boolean duplicateEmail = false;
            try {
               if(context.getSession().users().getUserByEmail(email, context.getRealm()) != null) {
                   duplicateEmail = true;
               }
            } catch (ModelDuplicateException e) {
                duplicateEmail = true;
            }
            if (duplicateEmail) {
                eventError = Errors.EMAIL_IN_USE;
                formData.remove(Validation.FIELD_EMAIL);
                context.getEvent().detail(Details.EMAIL, email);
                errors.add(new FormMessage(RegistrationPage.FIELD_EMAIL, Messages.EMAIL_EXISTS));
            }
        }

        if (errors.size() > 0) {
            context.error(eventError);
            context.validationError(formData, errors);
            return;

        } else {
            context.success();
        }
    }

    @Override
    public void success(FormContext context) {
        UserModel user = context.getUser();
        /* Mapeado o grupo Administrador do Realm Revenda-Certa */
        GroupModel group = context.getSession().realms().getRealm("revenda-certa").getGroupById(RolesProfileEnum.ROLE_ADMINISTRADOR.getGroupId());
        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
        user.setFirstName(formData.getFirst(RegistrationPage.FIELD_FIRST_NAME).toLowerCase());
        user.setLastName(formData.getFirst(RegistrationPage.FIELD_LAST_NAME).toLowerCase());
        user.setEmail(formData.getFirst(RegistrationPage.FIELD_EMAIL));
        user.joinGroup(group);
        user.addRequiredAction(RequiredAction.VERIFY_EMAIL);
        user.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
    	//System.out.println("teste: "+externalProviderService.verifieldCodigo(formData.getFirst("user.attributes."+CODE_AUTHENTIC)));

        // Implementar a entidade Pessoa do sistema
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(user.getFirstName());
        pessoa.setSobrenome(user.getLastName());
        pessoa.setEmail(user.getEmail());
        pessoa.setUsuarioId(user.getId());
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
			pessoa.setDataNascimento(formato.parse(formData.getFirst("user.attributes.dataNascimento")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
        Pessoa pessoaNews = this.externalProviderService.createPessoaPrinciapl(pessoa, formData.getFirst("user.attributes."+CODE_AUTHENTIC));
        user.setSingleAttribute("session", pessoaNews.getSession());
        List<String> var = new ArrayList<>();
        pessoaNews.getModuloAplicacao().forEach(x -> {var.add(x.getNome());});
        user.setAttribute("modulo", var);
        
       
        
        //Pessoa p = pessoaDAO.createPessoa(pessoa);
        //user.setSingleAttribute("session", new SessionRevendaServiceImp(p.getTenantId(), p.getId()).gerarSession());
        //user.setAttribute("modulo", codigoCadastroService.buscarCodigoModulosNomes(formData.getFirst(CODE_AUTHENTIC)));
        //UserUpdateHelper.updateRegistrationProfile(context.getRealm(), user, formData);
        //UserUpdateProfileContext up = new UserUpdateProfileContext(context.getRealm(), user);
    }

    @Override
    public void buildPage(FormContext context, LoginFormsProvider form) {
        // complete
    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {

    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }


    @Override
    public void close() {

    }

    @Override
    public String getDisplayType() {
        return "Profile Validation Angular";
    }

    @Override
    public String getReferenceCategory() {
        return null;
    }

    @Override
    public boolean isConfigurable() {
        return false;
    }

    private static AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {
            AuthenticationExecutionModel.Requirement.REQUIRED,
            AuthenticationExecutionModel.Requirement.DISABLED
    };
    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return REQUIREMENT_CHOICES;
    }
    @Override
    public FormAction create(KeycloakSession session) {
        return this;
    }

    @Override
    public void init(Config.Scope config) {

    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {

    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }
}
