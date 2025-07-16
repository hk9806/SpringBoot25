# SpringBoot25
스프링부트 학습용


=================application.properties=================

spring.datasource.driver-class-name=org.mariadb.jdbc.Driver

spring.datasource.url=jdbc:mariadb://localhost:3306/?????????

spring.datasource.username=?????????

spring.datasource.password=???????



spring.jpa.hibernate.ddl-auto=update

spring.jpa.properties.hibernate.format_sql=true

spring.jpa.show-sql=true


=======================build.gradle=======================
  /*maven respository에서 코드를 가져와 코끼리를 누르면 가져옴*/

    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'  /*데이터베이스 관련*/
    
    implementation 'org.springframework.boot:spring-boot-starter-thymeleaf' /*프론트 관련*/
    
    implementation 'org.springframework.boot:spring-boot-starter-web'       /*String-web*/
    
    compileOnly 'org.projectlombok:lombok'                                  /*lombok*/
    
    annotationProcessor 'org.projectlombok:lombok'                          /*lombok*/
    
    testCompileOnly 'org.projectlombok:lombok'                              /*lombok*/
    
    testAnnotationProcessor 'org.projectlombok:lombok'                      /*lombok*/



    developmentOnly 'org.springframework.boot:spring-boot-devtools'         /*boot 개발용*/
    
    runtimeOnly 'org.mariadb.jdbc:mariadb-java-client'                      /*maira db 드라이버*/

    
    /* 1단계 2단계 설정 -> source/main/resources/application.propertice 에서 설정*/
    
    testImplementation 'org.springframework.boot:spring-boot-starter-test'  /*junit 메소드 단위 테스트*/
    
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'            /*junit 용 코드*/
