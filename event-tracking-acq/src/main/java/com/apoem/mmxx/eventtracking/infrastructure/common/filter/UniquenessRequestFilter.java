package com.apoem.mmxx.eventtracking.infrastructure.common.filter;

import com.apoem.mmxx.eventtracking.infrastructure.repository.CommonRepository;
import com.apoem.mmxx.eventtracking.serialnumber.MsSerialNumberHolder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Functions;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: UniquenessRequestFilter </p>
 * <p>Description: 生成唯一流水号 </p>
 * <p>Date: 2020/7/15 17:01 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Slf4j
@Component
public class UniquenessRequestFilter extends OncePerRequestFilter implements ApplicationContextAware {

    @Value("${uniqueness_request_filter.include_path_patterns:}")
    private Set<String> includePathPatterns;

    @Value("${uniqueness_request_filter.include_path_packages:}")
    private Set<String> includePathPackages;

    private final Set<String> includePathPackagesPatterns = new HashSet<>();

    private ApplicationContext applicationContext;

    private static final PathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    @SuppressWarnings("NullableProblems")
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        CommonRepository commonRepository = applicationContext.getBean(CommonRepository.class);
        String servletPath = httpServletRequest.getServletPath();
        String serialNumber = commonRepository.getSerialNumber();
        log.info("Receive request on [{}] and process with [{}]", servletPath, serialNumber);
        MsSerialNumberHolder.setMsSerialNumber(serialNumber);
        BodyWrapper requestWrapper = new BodyWrapper(httpServletRequest);
        String body = HttpHelper.getBodyString(requestWrapper);
        log.info("loggingFilter---请求路径 {}, 请求体内容 {}", httpServletRequest.getRequestURL(), body);
        filterChain.doFilter(requestWrapper, httpServletResponse);
//        filterChain.doFilter(httpServletRequest, httpServletResponse);
        MsSerialNumberHolder.resetMsSerialNumber();
        log.info("Return response on [{}] and process with [{}]", servletPath, serialNumber);
    }

    @Override
    @SuppressWarnings("NullableProblems")
    protected boolean shouldNotFilter(HttpServletRequest request) {
        for (String pattern : includePathPatterns) {
            if (PATH_MATCHER.match(pattern, request.getServletPath())) {
                return false;
            }
        }

        for (String pattern : includePathPackagesPatterns) {
            if (PATH_MATCHER.match(pattern, request.getServletPath())) {
                return false;
            }
        }
        return true;
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        PathPackagesHandler handler = new PathPackagesHandler(applicationContext);
        handler.setIncludePathPackages(includePathPackages);
        Set<String> pathPackagesPatterns = handler.explicate();
        includePathPackagesPatterns.addAll(pathPackagesPatterns);
    }

    /**
     * PathPackages处理器
     */
    static class PathPackagesHandler {
        private final ApplicationContext applicationContext;

        private Set<String> includePathPackages;

        RequestMappingHandlerMapping mapping;

        private final RequestMappingInfo biscuit = RequestMappingInfo.paths("**").build();

        private final Method createRequestMappingInfo;
        private final Method getPathPrefix;
        private final RequestMappingInfo.BuilderConfiguration config;

        @SneakyThrows
        public PathPackagesHandler(ApplicationContext applicationContext) {
            this.applicationContext = applicationContext;

            mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
            Class<?> klass = mapping.getClass();
            createRequestMappingInfo = klass.getDeclaredMethod("createRequestMappingInfo", AnnotatedElement.class);
            createRequestMappingInfo.setAccessible(true);
            getPathPrefix = klass.getDeclaredMethod("getPathPrefix", Class.class);
            getPathPrefix.setAccessible(true);
            Field builderConfiguration = klass.getDeclaredField("config");
            builderConfiguration.setAccessible(true);
            config = (RequestMappingInfo.BuilderConfiguration) builderConfiguration.get(mapping);
        }

        /**
         * 解析包路径下的控制器Mapping
         *
         * @return 控制器Mapping
         */
        private Set<String> explicate() {

            return applicationContext
                    .getBeansWithAnnotation(Controller.class)
                    .values()
                    .stream()
                    .map(Object::getClass)
                    .filter(this::checkedRequestMapping)
                    .map(Functions.asFunction(handlerType -> getRequestMappingInfo(createRequestMappingInfo, getPathPrefix, handlerType)))
                    .map(o -> o.getPatternsCondition().getPatterns())
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());
        }

        private RequestMappingInfo getRequestMappingInfo(Method createRequestMappingInfo, Method getPathPrefix, Class<?> handlerType) throws Exception {
            RequestMappingInfo info = biscuit;

            RequestMappingInfo typeInfo = (RequestMappingInfo) createRequestMappingInfo.invoke(mapping, handlerType);
            String prefix = (String) getPathPrefix.invoke(mapping, handlerType);

            if (typeInfo != null) {
                info = typeInfo.combine(info);
            }
            if (prefix != null) {
                info = RequestMappingInfo.paths(prefix).options(this.config).build().combine(info);
            }
            return info;
        }

        /**
         * 检出有 RequestMapping 注解类
         *
         * @param beanKlass 待检类
         * @return boolean 是否符合
         */
        private boolean checkedRequestMapping(Class<?> beanKlass) {
            Predicate<String> matchPattern = pattern -> PATH_MATCHER.match(pattern, beanKlass.getCanonicalName());
            Predicate<String> annotationPresent = o -> Objects.nonNull(beanKlass.getAnnotation(RequestMapping.class));
            return includePathPackages.stream().anyMatch(matchPattern.and(annotationPresent));
        }

        public void setIncludePathPackages(Set<String> includePathPackages) {
            this.includePathPackages = includePathPackages;
        }
    }
}
