package ioc;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import services.email.EmailService;
import services.email.GmailService;
import services.data.DataService;
import services.data.MysqlDataService;
import services.hash.HashService;
import services.hash.MD5HashService;
import services.hash.Sha1HashService;

public class ConfigModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DataService.class).to(MysqlDataService.class);
        bind(EmailService.class).to(GmailService.class);
        bind(HashService.class)
                .annotatedWith(Names.named("MD5HashService"))
                .to(MD5HashService.class);
        bind(HashService.class)
                .annotatedWith(Names.named("Sha1HashService"))
                .to(Sha1HashService.class);
    }
}
