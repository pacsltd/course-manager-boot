package net.training.unittest.config;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import net.training.unittest.converter.DateFormatter;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.Formatter;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class Config {

	@Autowired
	private DateFormatter dateFormatter;

	
	@Bean
	public MailSender mailSender() {
		
		//TODO use properties constructor
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("auth.smtp.1and1.co.uk");
		mailSender.setPort(587);
		mailSender.setUsername("mail@phillip.wales");
		mailSender.setPassword("dtlscap1");
		
		Properties javaMailProperties = new Properties();
		javaMailProperties.setProperty("mail.transport.protocol", "smtp");
		javaMailProperties.setProperty("mail.smtp.auth", "true");
		javaMailProperties.setProperty("mail.smtp.starttls.enable", "true");
		javaMailProperties.setProperty("mail.debug", "true");
		mailSender.setJavaMailProperties(javaMailProperties);
		return mailSender;

	}
	
	@Bean 
	public ConversionService conversionService() {
		
		Set<DateFormatter> formatters = new HashSet<>();
		formatters.add(dateFormatter);
		
		FormattingConversionServiceFactoryBean formattingConversionServiceFactoryBean = new FormattingConversionServiceFactoryBean();
		formattingConversionServiceFactoryBean.setFormatters(formatters);
		
		return formattingConversionServiceFactoryBean.getObject();
		
		
	}
	
}
