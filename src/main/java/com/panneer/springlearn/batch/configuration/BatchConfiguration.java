package com.panneer.springlearn.batch.configuration;

import com.panneer.springlearn.batch.entity.Coffee;
import com.panneer.springlearn.batch.repository.CoffeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component("BatchConfiguration")
@EnableBatchProcessing(dataSourceRef = "batchDataSource", transactionManagerRef = "batchTransactionManager")
@AllArgsConstructor
public class BatchConfiguration extends DefaultBatchConfiguration {

    private JobRepository jobRepository;
    private PlatformTransactionManager transactionManager;
    private CoffeeRepository coffeeRepository;

    @Value("${inputFile}")
    private String fileInput;

    @Bean
    public FlatFileItemReader<Coffee> coffeeReader() {
        var flatFileItemReader = new FlatFileItemReader<Coffee>();
        flatFileItemReader.setName("coffeeItemReader");
        flatFileItemReader.setResource(new ClassPathResource(fileInput));
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(coffeeLineMapper());
        return flatFileItemReader;
    }

    private LineMapper<Coffee> coffeeLineMapper() {
        var lineMapper = new DefaultLineMapper<Coffee>();

        var lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("Brand", "Origin", "Characteristics");

        var fieldSetMapper = new BeanWrapperFieldSetMapper<Coffee>();
        fieldSetMapper.setTargetType(Coffee.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean
    public CoffeeCustomProcessor coffeeCustomProcessor() {
        return new CoffeeCustomProcessor();
    }

    @Bean
    public RepositoryItemWriter<Coffee> coffeeRepositoryItemWriter() {
        var repositoryItemWriter = new RepositoryItemWriter<Coffee>();
        repositoryItemWriter.setRepository(coffeeRepository);
        repositoryItemWriter.setMethodName("save");
        return repositoryItemWriter;
    }

    @Bean
    public Step stepOne() {
        var stepBuilder = new StepBuilder("stepOne", jobRepository);
        return stepBuilder.<Coffee, Coffee>chunk(100, transactionManager).reader(coffeeReader()).processor(coffeeCustomProcessor()).writer(coffeeRepositoryItemWriter()).build();
    }

    @Bean
    public Job job() {
        return new JobBuilder("coffeeBuilder", jobRepository)
                .start(stepOne())
                .build();
    }
}

