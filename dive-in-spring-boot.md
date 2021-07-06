# dive-in-spring-boot



##
## Springboot - @Import 详解 & @ImportSelector & @ImportSelector & @ImportResource


@Import 就是和xml配置的 <import />标签作用一样，允许通过它引入 @Configuration
注解的类 (java config)，


```java

package org.springframework.context.annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates one or more {@link Configuration @Configuration} classes to import.
 *  表示一个或者多个被 @Configuration注解的类被引入
 *  
 * <p>Provides functionality equivalent to the {@code <import/>} element in Spring XML.
 *  
 *  和spring XML中<import/>标签一样
 * 
 * Allows for importing {@code @Configuration} classes, {@link ImportSelector} and
 * {@link ImportBeanDefinitionRegistrar} implementations, as well as regular component
 * classes (as of 4.2; analogous to {@link AnnotationConfigApplicationContext#register}).
 * 
 * 允许通过它引入 @Configuration 注解的类 (java config)， 引入ImportSelector接口(这个比较重要，因为要通过它去判定要引入哪些@Configuration) 和 ImportBeanDefinitionRegistrar 接口的实现
 * 
 * <p>{@code @Bean} definitions declared in imported {@code @Configuration} classes should be
 * accessed by using {@link org.springframework.beans.factory.annotation.Autowired @Autowired}
 * injection.
  * 
  * 被@Configuration注解的类包含的beans应该通过 @Autowired注解注入，要么bean本身可以被自动注入，要么配置类能够被自动注入
  * 
  * Either the bean itself can be autowired, or the configuration class instance
 * declaring the bean can be autowired. The latter approach allows for explicit, IDE-friendly
 * navigation between {@code @Configuration} class methods.
 *
 * <p>May be declared at the class level or as a meta-annotation.
 *
 * <p>If XML or other non-{@code @Configuration} bean definition resources need to be
 * imported, use the {@link ImportResource @ImportResource} annotation instead.
 * 如果是xml，或者是其他非配置类的定义资源可以通过@ImportResource引入
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Import {

	/** 
	* 三种input
	* 
	 * {@link Configuration}, {@link ImportSelector}, {@link ImportBeanDefinitionRegistrar}
	 * or regular component classes to import.
	 */
	Class<?>[] value();

}

```

@Import的实现有很多时候需要借助
ImportSelector接口，所以我们再看下这个接口的描述,总结下来就是需要通过这个接口的实现去决定要引入哪些
@Configuration。 它如果实现了以下四个Aware 接口， 那么spring


```java


package org.springframework.context.annotation;

import org.springframework.core.type.AnnotationMetadata;

/**
 * Interface to be implemented by types that determine which @{@link Configuration}
 * class(es) should be imported based on a given selection criteria, usually one or more
 * annotation attributes.
 * 通过这个接口的实现去决定要引入哪些 @Configuration，通常是一个或者多个注解属性
 *
 * <p>An {@link ImportSelector} may implement any of the following
 * {@link org.springframework.beans.factory.Aware Aware} interfaces, and their respective
 * methods will be called prior to {@link #selectImports}:
 * 
 * 如果实现了以下四个Aware 接口， 那么spring保证会在调用它之前先调用Aware接口的方法，你可以通过这些Aware去感知系统里边所有的环境变量， 从而决定你具体的选择逻辑。
 * 
 * <ul>
 * <li>{@link org.springframework.context.EnvironmentAware EnvironmentAware}</li>
 * <li>{@link org.springframework.beans.factory.BeanFactoryAware BeanFactoryAware}</li>
 * <li>{@link org.springframework.beans.factory.BeanClassLoaderAware BeanClassLoaderAware}</li>
 * <li>{@link org.springframework.context.ResourceLoaderAware ResourceLoaderAware}</li>
 * </ul>
 *
 * <p>ImportSelectors are usually processed in the same way as regular {@code @Import}
 * annotations, however, it is also possible to defer selection of imports until all
 * {@code @Configuration} classes have been processed (see {@link DeferredImportSelector}
 * for details).
 * 
 * 
 */
public interface ImportSelector {

	/**
	 * Select and return the names of which class(es) should be imported based on
	 * the {@link AnnotationMetadata} of the importing @{@link Configuration} class.
	 */
	String[] selectImports(AnnotationMetadata importingClassMetadata);

}

```

Springboot 对@Import注解的处理过程

首先，Springboot的对注解的处理过程：Springboot对注解的处理都发生在AbstractApplicationContext->
refresh() -> invokeBeanFactoryPostProcessors(beanFactory) ->
ConfigurationClassPostProcessor
->postProcessBeanDefinitionRegistry()方法中


springboot初始化的普通context(非web)
是AnnotationConfigApplicationContext,在初始化的时候会初始化两个工具类,

AnnotatedBeanDefinitionReader
和ClassPathBeanDefinitionScanner，并分别用来从annotation
driven的配置和xml的配置中读取beanDefinition并向context注册。

那么在初始化AnnotatedBeanDefinitionReader的时候，会向BeanFactory注册一个ConfigurationClassPostProcessor用来处理所有的基于annotation的bean,
这个ConfigurationClassPostProcessor是 BeanFactoryPostProcessor
的一个实现，springboot会保证在invokeBeanFactoryPostProcessors(beanFactory)方法中调用注册到它上边的所有的BeanFactoryPostProcessor

```
// Parse each @Configuration class
        ConfigurationClassParser parser = new ConfigurationClassParser(
                this.metadataReaderFactory, this.problemReporter, this.environment,
                this.resourceLoader, this.componentScanBeanNameGenerator, registry);
```


ConfigurationClassParser -> processConfigurationClass() ->doProcessConfigurationClass() 方法中我们找到了

(这里边的流程还是很清楚的， 分别按次序处理了@PropertySource， @ComponentScan,@Import, @ImportResource,

- 首先， 判断如果被import的是 ImportSelector.class 接口的实现，那么初始化这个被Import的类，然后调用它的selectImports方法去获得所需要的引入的configuration， 然后递归处理

- 其次， 判断如果被import的是 ImportBeanDefinitionRegistrar 接口的实现，那么初始化后将对当前对象的处理委托给这个ImportBeanDefinitionRegistrar

- 最后， 将import引入的类作为一个正常的类来处理 (调用最外层的doProcessConfigurationClass())

所以， 从这里我们知道， 如果你引入的是一个正常的component，那么会作为@Compoent或者@Configuration来处理，这样在BeanFactory里边可以通过getBean拿到， 但如果你是ImportSelector或者
ImportBeanDefinitionRegistrar 接口的实现，那么spring并不会将他们注册到beanFactory中，而只是调用他们的方法。
